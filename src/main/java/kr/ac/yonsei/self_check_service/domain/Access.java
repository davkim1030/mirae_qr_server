package kr.ac.yonsei.self_check_service.domain;

import java.time.*;

/**
 * @author hyun-wookkim
 * 액세스 기록을 저장
 */
public class Access {
    public enum BuildingType {
        UNIV_CHURCH,                        // 00 대학 교회
        UNIV_HQ,                            // 01 대학 본부
        JEONG_UI,                           // 02 정의관
        CHEONG_SONG,                        // 03 청송관
        CHANG_JO,                           // 04 창조관
        BAEK_WOON,                          // 05 백운관
        MI_RAE,                             // 06 미래관
        LIBRARY,                            // 07 중앙 도서관
        STUDENT_UNION,                      // 08 학생회관
        EAGLE_PLAZA,                        // 09 이글 플라자
        YONSEI_PLAZA,                       // 10 연세 플라자
        SPORTS_CENTER,                      // 11 스포츠 센터
        SPORTS_STADIUM,                     // 12 대운동장
        TENNIS_COURT,                       // 13 테니스장
        NO_CHEON,                           // 14 노천극장
        MAEJI1_DORM,                        // 15 매지 1학사
        MAEJI2_DORM,                        // 16 매지 2학사
        MAEJI3_DORM,                        // 17 매지 3학사
        CHUNGYEON_DORM,                     // 18 청연학사
        SAEYEON1_2_DORM,                    // 19 세연 1, 2학사
        SAEYEON3_DORM,                      // 20 세연 3학사
        HYUN_WOON_JAE,                      // 21 현운재
        RADONFREE_HOUSE,                    // 22 라돈 프리 하우스
        INNDUSTRY_ACADEMIC_HALL,            // 23 산학관
        VENTURE_CENTER,                     // 24 산학관 벤처 센터
        ENVIRONMENTAL_FRIENDLY_TECH_CENTER, // 25 친환경화 기술 센터
        BUSINESS_INCUBATION_1,              // 26 제1 창업보육센터
        BUSINESS_INCUBATION_2,              // 27 제2 창업보육센터
        BUSINESS_INCUBATION_3,              // 28 제3 창업보육센터
        HAK_GOON_DAN                        // 29 학군단
    }           // 학교 건물 목록

    private LocalDateTime   dateTime;       // 출입 날짜, 시간
    private BuildingType    buildingType;   // 건물 종류
    private User            user;           // 유저 정보

    // Constructor
    public Access(LocalDateTime dateTime, BuildingType buildingType, User user) {
        this.dateTime = dateTime;
        this.buildingType = buildingType;
        this.user = user;
    }
    public Access(BuildingType buildingType, User user) {
        dateTime = LocalDateTime.now();
        this.buildingType = buildingType;
        this.user = user;
    }

    // getter
    public BuildingType getBuildingType() {
        return buildingType;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public User getUser() {
        return user;
    }

    // setter
    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
