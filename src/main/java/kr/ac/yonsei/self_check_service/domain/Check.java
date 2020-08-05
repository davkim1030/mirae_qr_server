package kr.ac.yonsei.self_check_service.domain;

import java.time.*;

/**
 * @author hyun-wookkim
 * 자가 검진 데이터 클래스
 */
public class Check {
    private User user;          // 검진한 사람
    private LocalDate date;     // 검진한 날짜
    private boolean[] check;    // 검진 문항에 대한 결

    // Constructor
    public Check(User user, LocalDate date, boolean[] check)
    {
        this.user = user;
        this.date = date;
        this.check = check;
    }

    // getter과
    public User getUser() {
        return user;
    }
    public LocalDate getDate() {
        return date;
    }
    public boolean[] getCheck() {
        return check;
    }

    // setter
    public void setUser(User user) {
        this.user = user;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCheck(boolean[] check) {
        this.check = check;
    }
}
