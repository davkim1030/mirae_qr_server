package kr.ac.yonsei.self_check_service.service;

import kr.ac.yonsei.self_check_service.domain.Check;
import kr.ac.yonsei.self_check_service.domain.User;
import kr.ac.yonsei.self_check_service.repository.CheckRepository;
import kr.ac.yonsei.self_check_service.repository.MemoryCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * 자가 검진 데이터(Check) Service
 */
@Service
public class CheckService {
    private final CheckRepository checkRepository; // Check 저장소 구현체

    // Constructor
    @Autowired
    public CheckService(CheckRepository checkRepository) {
        this.checkRepository =  checkRepository;
    }

    /**
     * 검사 추가하는 메소드
     * @param check : 추가할 검사 내용
     */
    public void addCheck(Check check) {
        checkRepository.save(check);
    }

    /**
     * 오늘 검사 했는지 확인하는 메소드
     * @param key : 검사 했는지 확인할 유저의 키
     * @param date : 검사 확인할 날짜
     * @return : 검사 했으면 true, 안 했으면 false
     */
    public boolean isTodayDone(String key, LocalDate date) {
        Optional<Check> result =
                checkRepository.findByKeyDate(key, date);
        return result.isPresent();
    }

    /**
     * Check 데이터가 오늘 데이터인지 확인
     * @param date : 검사할 날짜
     * @return : 오늘이면 true, 아니면 false
     */
    public boolean validateCheckData(LocalDate date) {
        return date.isEqual(LocalDate.now());
    }

}
