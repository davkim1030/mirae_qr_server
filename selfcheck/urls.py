from django.urls import path
from . import views

urlpatterns = [
    path('', views.select_user_type, name='select_user_type'),
    path('user_check/', views.user_check, name='user_check'),
    path('selfcheck/<str:key_hash>/', views.selfcheck, name='selfcheck'),
    path('handle_check_result/<str:key_hash>/', views.handle_check_result, name='handle_check_result'),
    path('result/<str:key_hash>/', views.result, name='result'),
    path('qr_validate/', views.validate_qr, name='validate_qr'),
]
