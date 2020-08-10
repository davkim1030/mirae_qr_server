from django.db import models


class User(models.Model):
    key_str = models.CharField(max_length=20, primary_key=True)
    user_type = models.IntegerField(null=False)  # TODO: ENUM으로 변경 필요
    url_str = models.CharField(max_length=100, null=False)
    # TODO: 나중에 설계에 따라 언어 필드 추가 가능성 있음


class Access(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date_time = models.DateTimeField(null=False)
    building_type = models.IntegerField(null=False)     # TODO: ENUM으로 변경


class Check(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date = models.DateField(null=False)
    check = models.CharField(max_length=11, null=False)
