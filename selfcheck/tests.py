from django.test import TestCase
from .apis import check_qr

check_qr("20200807")

# apis.selfcheck
# # 로그인 정보 체크
# # 연세 내부인일 경우
# # key = request.COOKIES.get('memberId')  # 나중에 실제 쿠키 받으면 수정 필요
# key = '2013253048'  # 나중에 실제 쿠키 받으면 수정 필요
# user = User.objects.filter(key_str=key)
# # user = User.objects.filter(url_str=key_hash)
# if len(user) == 0:  # DB에 없으면 추가
#     User.objects.create(key_str=key, user_type=0, url_str=name_hash(key))  # user_type 변경 필요
#     return render(request, 'selfcheck/selfcheck.html')
# check = Check.objects.filter(user=user[0], date=datetime.date.today())
# if len(check) == 0:
#     return render(request, 'selfcheck/selfcheck.html')  # url get으로 추가 필요
# return redirect('result')