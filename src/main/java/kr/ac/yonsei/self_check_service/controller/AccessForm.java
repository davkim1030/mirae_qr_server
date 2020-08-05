package kr.ac.yonsei.self_check_service.controller;

import kr.ac.yonsei.self_check_service.domain.Access;
import kr.ac.yonsei.self_check_service.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Access Controller에 데이터를 담기 위한 Form
 */
public class AccessForm {
    private User user;
    private LocalDateTime dateTime;
    private Access.BuildingType buildingType;

    // Constructor
    public AccessForm(String key, int userType, String dateTime, int buildingType)
    {
        user = new User(key, userTypeMapping(userType));
        this.buildingType = buildingTypeMapping(buildingType);
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    /**
     * 넘겨 받은 데이터(0 | 1)로 유저 타입 enum에 매핑하는 것
     * @param val : enum 몇 번째 데이터인지
     * @return : enum에 매핑하여 리턴
     */
    private User.UserType userTypeMapping(int val)
    {
        return User.UserType.values()[val];
    }

    /**
     * 넘겨 받은 데이터(0 ~ 29)로 건물 타입 enum에 매핑하는 것
     * @param val : enum 몇 번째 데이터인지
     * @return : enum에 매핑하여 리턴
     */
    private Access.BuildingType buildingTypeMapping(int val)
    {
        return Access.BuildingType.values()[val];
    }

    // getter
    public User getUser() {
        return user;
    }
    public Access.BuildingType getBuildingType() {
        return buildingType;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // setter
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setBuildingType(Access.BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
