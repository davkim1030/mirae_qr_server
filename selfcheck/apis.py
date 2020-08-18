import base64
import datetime
import hashlib
import os

import pyqrcode
from Crypto import Random
from Crypto.Cipher import AES
from selfcheck.models import User
from yonseiSelfCheck.settings import STATIC_ROOT


"""
기능들을 정의해둔 모듈
"""


def accessable(check):
    """
    검사결과에서 비정상이 하나라도 있으면 False 모두 0이면 True
    :param check:   str, 검진 결과를 저장해둔 11자리 문자열 (models.Check.check)
    :return:        bool, 등교 가능 여부를 리턴
    """
    return '1' not in check


def create_qr(filename, msg):
    """
    QR코드를 생성하는 메소드
    파일명과 qr에 담을 메시지를 넣으면 STATIC_ROOT의 'selfcheck/qrcode/<today>/'에 qr코드 저장
    :param filename:    str, 저장할 QR코드 이미지 파일 이름
    :param msg:         str, QR코드로 변환할 메시지
    """
    url = pyqrcode.create(msg)                                  # msg 내용으로 QR코드 생성
    date_str = datetime.date.today().strftime("%Y%m%d")         # 디렉토리 생성을 위해 현재 날짜 구해서 문자열로 변경
    qr_path = STATIC_ROOT + '/selfcheck/qrcode/' + date_str     # 전체 경로를 문자열 변수 생성
    if not(os.path.isdir(qr_path)):                             # 디렉토리가 존재하지 않으면
        os.makedirs(os.path.join(qr_path))                      # 디렉토리 생성
    url.png(qr_path + "/" + filename + '.png',                  # QR코드 내용을 이미지 파일로 저장
            scale=6,                                            # 크기
            module_color=[0, 0, 0],                             # 색상(hex)
            background=[0xff, 0xff, 0xff])                      # 배경 색상(hex)


def name_hash(msg):
    """
    이름(평문)이 들어오면 해쉬값으로 바꾸는 함수
    평문 -> reverse -> sha256
    보안을 위해서 키값을 거꾸로 돌리고 sha256실행
    :param msg: str, 암호화할 평문
    :return:    str, 암호문(해쉬)
    """
    msg = list(msg)                                             # 문자열을 리스트로 변경
    msg.reverse()                                               # 거꾸로 뒤집기
    msg = ''.join(msg)                                          # 다시 문자열로 변경
    return hashlib.sha256(msg.encode("utf-8")).hexdigest()      # 결과를 sha256해쉬해서 hex인코딩(0~f)해서 리턴


def check_qr(plain_txt):
    """
    qr코드의 날짜 정보가 오늘 날짜와 같은지 확인
    QR코드에 들어가는 내용은 날짜 8자리(YYYYmmdd) + 유저 키값이다.
    날짜 데이터는 plain_txt[:8], 유저 키값은 plain_txt[8:]이다.
    :param plain_txt:   str, QR코드를 읽은 값의 평문
    :return:            bool, QR코드 값 유효성 여부
    """
    year = int(plain_txt[:4])                   # 문자열에서 년 데이터 추출
    month = int(plain_txt[4:6])                 # 문자열에서 월 데이터 추출
    day = int(plain_txt[6:8])                   # 문자열에서 일 데이터 추출
    key = plain_txt[8:]                         # 문자열에서 키값 추출
    user = User.objects.filter(key_str=key)     # 키값을 가지는 유저 레코드 DB에서 셀렉트
    # 날짜 데이터와 오늘 날짜가 같고, DB에 존재하는 유저이면 True 아니면 False
    return datetime.date(year, month, day) == datetime.date.today() and len(user) == 1


class AESCipher:
    """
    AES(CBC) 암호화를 구현한 클래스
    """
    key = bytes()       # 키값을 저장할 클래스 변수

    def __init__(self):
        """
        생성자
        인스턴스 변수들의 값을 초기화해준다
        """
        self.key = hashlib.sha256('KimChoShin3588274758'[:16].encode("utf-8")).digest()     # 암복호화 키값 16자리를 맞춰야함
        self.BS = 16                                                                        # 블록 사이즈
        self.pad = lambda s: s + (self.BS - len(s.encode('utf-8')) % self.BS) * '-'         # 패딩
        self.unpad = lambda s: s[:ord(s[len(s) - 1:])]

    def __encrypt__(self, raw):
        """
        암호화 메소드수 내부 함
        :param raw:     bytes, 암호화할 평문 바이트 시퀀스
        :return:        bytes, 암호화된 암호문 바이트 시퀀스
        """
        raw = self.pad(raw)                             # 평문에 패딩 추가
        iv = Random.new().read(AES.block_size)
        cipher = AES.new(self.key, AES.MODE_CBC, iv)    # CBC모드로 암호화
        return base64.b64encode(iv + cipher.encrypt(raw.encode('utf-8')))   # 암호문을 utf-8로 인코딩하고 base64로 인코딩함

    def __decrypt__(self, enc):
        """
        복호화 메소드 내부 함
        :param enc:     bytes, 복호화할 암호문 바이트 시퀀스
        :return:        bytes, 복호화한 평문 바이트 시퀀스
        """
        enc = base64.b64decode(enc)                     # base64 디코드
        iv = enc[:16]                                   # 초기 벡터 추출
        cipher = AES.new(self.key, AES.MODE_CBC, iv)    # 복호화
        return self.unpad(cipher.decrypt(enc[16:]))     # 복호화 결과 패딩 제거하고 리턴

    def encrypt_str(self, raw):
        """
        암호화 메소드
        :param raw:     str, 암호화할 평문 문자열
        :return:        str, 암호화된 암호문 문자열
        """
        return self.__encrypt__(raw).decode("utf-8")

    def decrypt_str(self, enc):
        """
        복호화 메소드
        :param enc:     str, 복호화할 암호문 문자열
        :return:        str, 보호화된 평문 문자열
        """
        return self.__decrypt__(enc).decode("utf-8").replace('-', '')
