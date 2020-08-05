package kr.ac.yonsei.self_check_service.domain;

/**
 * 유저 정보 저장하는 클래스
 * @author hyun-wookkim
 */
public class User {
    public enum UserType {
        MEMBER,     // 교수, 교직원, 학생
        VISITOR     // 외부인
    }

    private String      keyNumber;      // 키값, MEMBER는 포탈 아이디, 외부인은 전화번호 11자리
    private UserType    userType;       // 유저 타입, MEMBER | VISITOR

    // constructor
    public User(String keyNumber, UserType userType) {
        this.keyNumber = keyNumber;
        this.userType = userType;
    }

    // getter
    public String getKeyNumber() {
        return keyNumber;
    }
    public UserType getUserType() {
        return userType;
    }

    // setter
    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
