package kr.ac.yonsei.self_check_service.domain;

import java.time.*;

/**
 * @author hyun-wookkim
 * 검진 데이터 클래스
 */
public class Check {
    private User user;
    private LocalDate date;
    private boolean[] check;

    // Constructor
    public Check(User user, LocalDate date, boolean[] check)
    {
        this.user = user;
        this.date = date;
        this.check = check;
    }

    // getter

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
