import datetime
import json
from json import JSONDecodeError

from django.http import HttpResponseBadRequest, JsonResponse, HttpResponseNotFound
from django.shortcuts import render, redirect
from .models import User
from .models import Check
from .apis import name_hash, check_qr
from .apis import create_qr
from .apis import AESCipher
from .apis import accessable


def access_list(request):
    return render(request, 'selfcheck/access_list.html')


def select_language(request):
    return render(request, 'selfcheck/select_language.html')


def select_user_type(request):
    return render(request, 'selfcheck/select_user_type.html')


def selfcheck(request, key_hash):
    user = User.objects.filter(url_str=key_hash)
    today = datetime.date.today()
    if len(user) == 0:
        return HttpResponseNotFound()  # 커스텀 페이지 제작 필
    if len(Check.objects.filter(user=user[0], date=today)) != 0:
        return redirect('/result/' + key_hash + "/")
    return render(request, 'selfcheck/selfcheck.html', {'url_str': key_hash})


def handle_check_result(request, key_hash):
    if request.method == "POST":
        user = User.objects.filter(url_str=key_hash)
        if len(user) == 0:
            return HttpResponseNotFound()
        user = user[0]
        today = datetime.date.today()
        if len(Check.objects.filter(user=user, date=today)) == 0:
            data = request.POST
            check_str = ""
            check_str = check_str + '0' if data['q1'] == 'y' else '1'
            if 'q21' in data.keys() and data['q21'] == 'on':
                check_str += "0" * 7
            else:
                for i in range(2, 9):
                    check_str += ('1' if 'q2' + str(i) in data.keys() and data['q2' + str(i)] == 'on' else '0')
            check_str += '0' if data['q3'] == 'y' else '1'
            check_str += '0' if data['q4'] == 'y' else '1'
            check_str += '0' if data['q5'] == 'y' else '1'
            Check.objects.create(user=user, date=today, check=check_str)
            if accessable(check_str):
                msg = today.strftime("%Y%m%d") + user.key_str
                create_qr(key_hash, AESCipher().encrypt_str(msg))
        return redirect('/result/' + key_hash + "/")
    return HttpResponseBadRequest()


def result(request, key_hash):
    r = False
    user = User.objects.filter(url_str=key_hash)
    if len(user) == 0:
        return HttpResponseNotFound()
    user = user[0]
    today = datetime.date.today()
    check = Check.objects.filter(user=user, date=today)
    if len(check) == 0:
        return redirect('/selfcheck/' + key_hash + "/")
    if accessable(check[0].check):
        r = True
    url = "selfcheck/qrcode/" + today.strftime("%Y%m%d") + "/" + key_hash + ".png"
    return render(request, 'selfcheck/result.html', {'result': r, 'url': url})


# qr코드를 읽은 결과를 판별해주는 곳, 예외처리 필요
def validate_qr(request):
    if request.method == "GET":
        try:
            data = json.loads(request.body)
        except Exception:
            return HttpResponseBadRequest
        if data['msg'] is None:
            return JsonResponse({'result': False})
        plain = str()
        result = bool()
        try:
            plain = AESCipher().decrypt_str(data['msg'])
            result = check_qr(plain)
        except Exception:
            return JsonResponse({'result': False})
        return JsonResponse({'result': result})
    return HttpResponseBadRequest
