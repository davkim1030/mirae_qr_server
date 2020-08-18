from django.db import models
from django.utils.translation import gettext_lazy as _


"""
RDB 테이블을 클래스로 매핑한 ORM 모듈
테이블 : 클래스
컬럼   : 멤버 변수
로우   : 인스턴스
"""


class User(models.Model):
    """
    유저 정보를 저장한 클래스
    :column key_str:    [VARCHAR(20), pk], 유저의 id값. 교내 구성원은 교번 또는 학번, 외부인은 전화번호로 자동 설정
    :column user_type:  [ENUM(UserType)], 유저의 종류, 교내 구성원은 M, 외부인은 V로 저장
    :column url_str:    [VARCHAR(100)], 유저의 키값(key_str)으로 만든 sha256 해쉬값
    :column name:       [VARCHAR(100)], 유저의 이름
    """
    class UserType(models.TextChoices):     # 유저 타입을 정의하는 enum 클래스
        MEMBER = "M", _('Member')           # 코드에서 쓰는 값 = "디비에 저장될 값", _('사용자들에게 보여질 값') 형식이다
        VISITOR = "V", _('Visitor')

    key_str = models.CharField(max_length=20, primary_key=True)
    user_type = models.CharField(
        null=False,
        max_length=2,
        choices=UserType.choices
    )
    url_str = models.CharField(max_length=100, null=False)
    name = models.CharField(max_length=100, null=False)


class Access(models.Model):
    """
    유저의 출입에 관한 테이블
    :column id:             [NUMBER, pk, AUTO_INCREMENT], 자동으로 생성, 증가하는 id 값, 쓸일은 없음
    :column user:           [VARCHAR(20), ForeignKey(User)], 출입한 유저. 디비에는 유저의 키값이 들어가지만, 여기서는 User 인스턴스로 사용
    :column date_time:      [DATETIME], 출입한 날짜, 시간
    :column building_type:  [ENUM(BuildingType)], 출입한 건물 이름
    :column way:            [ENUM(Way)], 출입 방향(IN, OUT)
    :column success:        [BOOLEAN], 출입 성공 여부
    """
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
    """
    검진 결과에 관한 테이블
    :column id:     [NUMBER, pk, AUTO_INCREMENT], 자동으로 생성, 증가하는 id 값, 쓸일은 없음
    :column user:   [ForeignKey(User)], 검진을 실시한 유저. 디비에는 유저의 키값이 들어가지만, 여기서는 User 인스턴스로 사용
    :column date:   [DATE], 검진 실시 날짜
    :column check:  [CHAR(11)], 검진 결과. 각 문항에 이상이 없으면 0, 이상이 있으면 1로 저장
    - 1, 3, 4, 5 문항은 yes/no 질문이라 한 자리씩 차지하지만 2번 문항은 다중 선택이 가능하므로 7자리를 차지
    - 예시
        1. 1    2-1 2-2 2-3 2-4 2-5 2-6 2-7 3   4   5
           0    0   0   0   0   0   0   0   0   0   0
           => 모두 이상 없음. 등교 가능, QR 생성 가능
        2. 1    2-1 2-2 2-3 2-4 2-5 2-6 2-7 3   4   5
           0    1   0   0   0   0   0   0   0   0   0
           => 2-1번 문항 이상 있음. 등교 불가능, QR 생성 불가
    """
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    date = models.DateField(null=False)
    check = models.CharField(max_length=11, null=False)
