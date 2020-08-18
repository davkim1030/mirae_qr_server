from django.urls import path
from . import views

urlpatterns = [
    path('', views.select_user_type, name='select_user_type'),              # 유저 타입 선택하는 메인 화면
    path('user_check/', views.user_check, name='user_check'),               # 받은 유저 데이터 처리하는 곳
    path('selfcheck/<str:key_hash>/', views.selfcheck, name='selfcheck'),   # 자가 검진하는 화면. url에 개인 key_hash값으로 구분
    # 자가 검진 화면에서 넘겨받은 form 값들은 처리하는 곳
    path('handle_check_result/<str:key_hash>/', views.handle_check_result, name='handle_check_result'),
    path('result/<str:key_hash>/', views.result, name='result'),            # 검진 결과 화면
    path('qr_validate/', views.validate_qr, name='validate_qr'),            # qr 검증 url
]
