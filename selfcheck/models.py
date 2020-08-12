from django.db import models
from django.utils.translation import gettext_lazy as _


class User(models.Model):
    class UserType(models.TextChoices):
        MEMBER = "M", _('Member')
        VISITOR = "V", _('Visitor')

    key_str = models.CharField(max_length=20, primary_key=True)
    user_type = models.CharField(
        null=False,
        max_length=2,
        choices=UserType.choices
    )
    url_str = models.CharField(max_length=100, null=False)
    # TODO: 나중에 설계에 따라 언어 필드 추가 가능성 있음


class Access(models.Model):
    class BuildingType(models.TextChoices):
        BAEK_WOON = "BAEK_WOON", _("Baek_woon")
        CHANG_JO = "GHANG_JO", _("Chang_jo")
        MI_RAE = "MI_RAE", _("Mi_rae")
        JEONG_UI = "JEONG_UI", _("Jeong_ui")
        CHEONG_SONG = "CHEONG_SONG", _("Cheong_song")
        LIBRARY = "LIBRARY", _("Library")
        STUDENT_UNION = "STUDENT_UNION", _("Student_union")
        YONSEI_PLAZA = "YONSEI_PLAZA", _("Yonsei_plaza")
        SAE_YEON_1_2 = "SAE_YEON_1_2", _("Sae_yeon_1_2")
        SAE_YEON_3 = "SAE_YEON_3", _("Sae_yeon_3")
        MAE_JI_1 = "MAE_JI_1", _("Mae_ji_1")
        MAE_JI_2 = "MAE_JI_2", _("Mae_ji_2")
        MAE_JI_3 = "MAE_JI_3", _("Mae_ji_3")
        CHEONG_YEON_1 = "CHEONG_YEON_1", _("Cheong_yeon_1")
        CHEONG_YEON_2 = "CHEONG_YEON_2", _("Cheong_yeon_2")
        SPORTS_CENTER = "SPORTS_CENTER", _("Sports_center")
        SAN_HAK_GWAN = "SAN_HAK_GWAN", _("San_hak_gwan")
        HYEON_WOON_JAE = "HYEON_WOON_JAE", _("Hyeon_woon_jae")

    class Way(models.TextChoices):
        IN = "IN", _("In")
        OUT = "OUT", _("Out")

    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date_time = models.DateTimeField(null=False)
    building_type = models.CharField(
        null=False,
        max_length=14,
        choices=BuildingType.choices
    )
    way = models.CharField(
        null=False,
        max_length=3,
        choices=Way.choices
    )
    success = models.BooleanField(null=False)


class Check(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date = models.DateField(null=False)
    check = models.CharField(max_length=11, null=False)
