import base64
import datetime
import hashlib
import os
import random

import pyqrcode
from Crypto import Random
from Crypto.Cipher import AES


from selfcheck.models import User


# 검사결과에서 비정상이 하나라도 있으면 False 모두0이면 True
from yonseiSelfCheck.settings import STATIC_ROOT


def accessable(check):
    return '1' not in check


# 파일명과 qr에 담을 메시지를 넣으면 'qrcode/'에 qr코드 저장
def create_qr(filename, msg):
    url = pyqrcode.create(msg)
    date_str = datetime.date.today().strftime("%Y%m%d")
    qr_path = STATIC_ROOT + '/selfcheck/qrcode/' + date_str
    if not(os.path.isdir(qr_path)):
        os.makedirs(os.path.join(qr_path))
    url.png(qr_path + "/" + filename + '.png',
            scale=6,
            module_color=[0, 0, 0],
            background=[0xff, 0xff, 0xff])


# 보안을 위해서 키값을 거꾸로 돌리고 sha256실행
def name_hash(msg):
    msg = list(msg)
    msg.reverse()
    msg = ''.join(msg)
    return hashlib.sha256(msg.encode("utf-8")).hexdigest()


# qr코드의 날짜 정보가 오늘 날짜와 같은지 확인
def check_qr(plain_txt):
    year = int(plain_txt[:4])
    month = int(plain_txt[4:6])
    day = int(plain_txt[6:8])
    key = plain_txt[8:]
    user = User.objects.filter(key_str=key)
    return datetime.date(year, month, day) == datetime.date.today() and len(user) == 1


class AESCipher:
    key = bytes()

    def __init__(self):
        self.key = hashlib.sha256('KimChoShin3588274758'[:16].encode("utf-8")).digest()
        self.BS = 16
        self.pad = lambda s: s + (self.BS - len(s.encode('utf-8')) % self.BS) * '-'
        self.unpad = lambda s: s[:ord(s[len(s) - 1:])]

    def encrypt(self, raw):
        raw = self.pad(raw)
        iv = Random.new().read(AES.block_size)
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return base64.b64encode(iv + cipher.encrypt(raw.encode('utf-8')))

    def decrypt(self, enc):
        enc = base64.b64decode(enc)
        iv = enc[:16]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return self.unpad(cipher.decrypt(enc[16:]))

    def encrypt_str(self, raw):
        return self.encrypt(raw).decode("utf-8")

    def decrypt_str(self, enc):
        return self.decrypt(enc).decode("utf-8").replace('-', '')
