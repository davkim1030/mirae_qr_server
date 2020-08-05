package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Check;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Check 데이터 저장 구현체
 */
@Repository
public class MemoryCheckRepository implements CheckRepository{
    static ArrayList<Check> store = new ArrayList<>();  // 데이터 저장할 변수

    @Override
    public Check save(Check check) {
        store.add(check);
        return check;
    }

    @Override
    public ArrayList<Check> findByKey(String key) {
        ArrayList<Check> result = new ArrayList<>();

        for (Check item : store) {
            if (item.getUser().getKeyNumber().equals(key))
                result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Check> findByCheck(boolean[] check) {
        ArrayList<Check> result = new ArrayList<>();
        boolean flag;

        for (Check item : store) {
            flag = true;
            boolean[] tmp = item.getCheck();
            // 항목 하나씩 검사해서 모두 같아야 추가
            for (int i = 0; i < tmp.length; i++) {
                if (item.getCheck()[i] != tmp[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Check> findByDate(LocalDate date) {
        ArrayList<Check> result = new ArrayList<>();

        for (Check item : store) {
            if (item.getDate().equals(date))
                result.add(item);
        }
        return result;
    }

    @Override
    public Optional<Check> findByKeyDate(String key, LocalDate date) {
        return store.stream().findAny();
    }

    @Override
    public ArrayList<Check> findAll() {
        return store;
    }
}
