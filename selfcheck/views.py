import datetime
import json
import logging

from django.http import HttpResponseBadRequest, JsonResponse, HttpResponseNotFound
from django.shortcuts import render, redirect
from django.views.decorators.csrf import csrf_protect

from .models import User, Access
from .models import Check
from .apis import name_hash, check_qr
from .apis import create_qr
from .apis import AESCipher
from .apis import accessable


def select_user_type(request):
    """
    메인페이지(유저 종류 선택하는 페이지) 연결 메소드
    """
    return render(request, 'selfcheck/main.html')


@csrf_protect
def user_check(request):
    """
    포탈 로그인, 문자 로그인 후 쿠키 값을 가져와서 처리하는 메소드
    """
    # TODO: 본인인증 API 로그인 진입점 설정 필요
    data = request.COOKIES  # 쿠키 값 추출
    if 'memberId' in data.keys() and 'memberName' in data.keys():  # id와 name이 모두 쿠키에 있으면
        key_str = data.get('memberId')                              # 해당 값들을 변수로 저장
        name = data.get('memberName')
        user = User.objects.filter(key_str=key_str)                 # DB 유저 테이블에 id를 pk로 갖는 레코드가 있는지 검색
        if len(user) == 0:                                          # DB에 레코드가 없으면 추가
            User.objects.create(key_str=key_str, user_type=User.UserType.MEMBER, url_str=name_hash(key_str), name=name)
        return redirect(f'/selfcheck/{name_hash(key_str)}/')        # 개인 URL 검진 페이지로 리다이렉트
    return HttpResponseBadRequest()                                 # 쿠키값에 이상이 있으면 400에러


def selfcheck(request, key_hash):
    """
    자가 검진 페이지로 연결시켜주는 메소드기
    :param request: http request
    :param key_hash: 개인별 해쉬값, url로 사용됨
    """
    user = User.objects.filter(url_str=key_hash)                    # 유저테이블에서 key_hash데이터를 갖는 유저 셀렉트
    today = datetime.date.today()                                   # 오늘 날짜를 가져옴
    if len(user) == 0:                                              # key_hash를 갖는 유저가 없다면 404 에러
        return HttpResponseNotFound()  # TODO: 커스텀 페이지 제작 필요
    if len(Check.objects.filter(user=user[0], date=today)) != 0:    # 유저의 오늘 검진(Check) 기록이 있다면
        return redirect('/result/' + key_hash + "/")                # 결과페이지(/result/<key_hash>/)로 리다이렉트
    return render(request, 'selfcheck/selfcheck.html', {'url_str': key_hash})  # 기록이 없으면 검진 페이지 열기


def handle_check_result(request, key_hash):
    """
    검진페이지('/selfcheck/<key_hash>/')에서 검진 내용을 제출하면 결과를 처리하는 메소드
    검진 결과에 상관 없이 레코드를 검진 결과(Check) 테이블에 추가한다.
    그 후 결과 페이지('/result/<key_hash>/')로 리다이렉트
    :param request: http request
    :param key_hash: 개인별 해쉬값, url로 사용됨
    """
    if request.method == "POST":                                                # 리퀘스트 메소드가 POST이면 동작
        user = User.objects.filter(url_str=key_hash)                            # key_hash로 유저 검색해서
        if len(user) == 0:                                                      # 없으면 404 에러
            return HttpResponseNotFound()
        user = user[0]                                                          # 결과가 QuerySet이니까 그중 첫 번째 데이터 선택
        today = datetime.date.today()                                           # 오늘 날짜 가져오기
        if len(Check.objects.filter(user=user, date=today)) == 0:               # 유저가 오늘 검사한 기록이 없으면 검진 데이터 처
            data = request.POST                                                 # 검진 페이지에서 넘긴 데이터 추출
            check_str = ""                                                      # 검사 결과 저장할 문자열 초기화
            # 각 라디오버튼 값 체크해서 이상 있으면 1, 없으면 0으로 저장
            check_str = check_str + '0' if data['q1'] == 'y' else '1'
            # 체크박스는 '아니오'가 checked일 경우 모두 이상 없음(0)으로 저장, 아니면 해당 문항 이상있음('1')으로 저장
            if 'q2_n' in data.keys() and data['q2_n'] == 'y':
                check_str += "0" * 7
            else:
                for i in range(2, 9):  # 2 ~ 8번 문항 체크
                    check_str += ('1' if 'q2' + str(i) in data['q2'] else '0')
            check_str += '0' if data['q3'] == 'y' else '1'
            check_str += '0' if data['q4'] == 'y' else '1'
            check_str += '0' if data['q5'] == 'y' else '1'
            # 입력값 처리 끝
            Check.objects.create(user=user, date=today, check=check_str)        # 검진 DB에 레코드 저장
            if accessable(check_str):                                           # 검진 결과에 이상(1) 없으면
                msg = today.strftime("%Y%m%d") + user.key_str                   # 오늘 날짜와 유저 키값을 합쳐서
                create_qr(key_hash, AESCipher().encrypt_str(msg))               # QR코드 생성하여 로컬에 저장
        return redirect('/result/' + key_hash + "/")                            # 검진 데이터가 있으면 결과 페이지로 리다이렉트
    return HttpResponseBadRequest()                                             # 리퀘스트가 POST가 아니면 400 에러


def result(request, key_hash):
    """
    검진 데이터에 따라 결과물 보여주는 페이지
    검진 결과 이상 없음 - 개인 QR코드 출력
    검진 결과 이상 있음 - 등교 중지 안내문 출력
    :param request: http request
    :param key_hash: 유저 key_hash
    """
    r = False                                                   # 결과 기본값 False
    user = User.objects.filter(url_str=key_hash)                # key_hash에 해당되는 유저 디비에서 검색
    if len(user) == 0:                                          # 없으면 404 에러
        return HttpResponseNotFound()
    user = user[0]                                              # 어차피 결과가 1개이므로 첫 번째 데이터 선택
    today = datetime.date.today()                               # 오늘 날짜 가져오기
    check = Check.objects.filter(user=user, date=today)         # 디비에서 유저의 오늘 날짜의 검진 레코드 검색
    if len(check) == 0:                                         # 검진 레코드가 없으면
        return redirect('/selfcheck/' + key_hash + "/")         # 자가 검진 페이지로 리다이렉트
    if accessable(check[0].check):                              # 검진 데이터에 이상(1)이 없으면
        r = True                                                # 출입 가능 결과 True로 변경
    url = "selfcheck/qrcode/" + today.strftime("%Y%m%d") + "/" + key_hash + ".png"  # qr코드의 path 가져오기
    return render(request, 'selfcheck/result.html', {'result': r, 'url': url})      # 결과 페이지 결과와 url 보내서 열기


def validate_qr(request):
    """
    qr리더가 읽은 json타입으로 오는 데이터를 처리
    json 데이터 형식
     - msg: qr코드 읽은 결과 문자열
     - building_type: 건물 문자열 (Access 테이블에 있는 BuildingType enum값 중 하나여야 함)
       -> 건물 관리자 계정을 사용할 경우 설계 바꿔야 함
     - way: 들어가는지 나가는지 판별하는 문자열 (IN / OUT 중 하나)
    TODO: 예외처리 필요
    :param request: http request
    """
    if request.method == "POST":                                        # 메소드가 POST이면 QR코드 판별 실행
        try:                                                            # 데이터의 내용에서 예외 발생 가능하므로 처리
            data = json.loads(request.body)                             # json 데이터 로드
            # json 데이터에 msg, building_type, way가 없으면 예외 발생
            if 'msg' not in data.keys() or \
                    'building_type' not in data.keys() or \
                    'way' not in data.keys():
                raise Exception("Necessary data not received")
            plain = AESCipher().decrypt_str(data['msg'])                # msg 데이터(암호문)을 복호화해서 평문(plain)에 저장
            result = check_qr(plain)                                    # qr코드 값이 유효한지(날짜와 유저) 판별항여 결과 저장
            building_type = Access.BuildingType[data['building_type']]  # building_type 데이터 저장 (enum 타입)
            way = Access.Way[data['way']]                               # way 데이터 저장 (enum 타입)
            user = User.objects.filter(key_str=plain[8:])               # DB에서 유저 레코드 선택
            if len(user) == 0:                                          # 유저가 없으면 예외 발생
                raise Exception("No such user on db")
            user = user[0]                                              # 유저 선택
            Access.objects.create(user=user,                            # 출입(Access) 레코드 생성
                                  date_time=datetime.datetime.now(),
                                  building_type=building_type,
                                  way=way,
                                  success=result)
            return JsonResponse({'result': result})                     # 결과를 json 타입으로 리턴
        except Exception:                                               # 도중 예외 발생시 False 결과 리턴
            return JsonResponse({'result': False})
    return HttpResponseBadRequest()                                     # POST 메소드가 아니면 400 에러
