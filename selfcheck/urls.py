from django.urls import path
from . import views

urlpatterns = [
    path('', views.access_list, name='access_list'),
    path('lang/', views.select_language, name='select_language'),
    path('type/', views.select_user_type, name='select_user_type'),
    path('selfcheck/<str:key_hash>/', views.selfcheck, name='selfcheck'),
    path('handle_check_result/<str:key_hash>/', views.handle_check_result, name='handle_check_result'),
    path('result/<str:key_hash>/', views.result, name='result'),
    path('qr_validate/', views.validate_qr, name='validate_qr'),
]
