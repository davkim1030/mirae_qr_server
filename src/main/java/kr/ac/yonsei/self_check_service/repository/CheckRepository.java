package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Check;

import java.time.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Check(자가 검진 기록) 데이터를 저장하는 Repo interface
 */
public interface CheckRepository {

    /**
     * repo에 Check 인스턴스 저장
     * @param check : 저장할 Check 인스턴스
     * @return : 저장한 Check 인스턴스 값
     */
    Check save(Check check);

    /**
     * repo에서 key로 검색
     * @param key : 검색할 key
     * @return : 하나 이상일 수 있으므로 ArrayList<Check>
     */
    ArrayList<Check> findByKey(String key);

    /**
     * repo에서 Check(검진 데이터)로 검색
     * @param check : 검색할 검진 데이터
     * @return : 하나 이상일 수 있으므로 ArrayList<Check>
     */
    ArrayList<Check> findByCheck(boolean[] check);

    /**
     * repo에서 date(날짜)로 검색
     * @param date : 검색할 date(날짜) 데이터
     * @return : 하나 이상일 수 있으므로 ArrayList<Check>
     */
    ArrayList<Check> findByDate(LocalDate date);

    /**
     * repo에서 key와 date로 검색 -> 후보키임
     * @param key : 검색할 key
     * @param date : 검색할 날짜
     * @return : 키이므로 하나이거나 없으므로 Optional 데이터 리턴
     */
    Optional<Check> findByKeyDate(String key, LocalDate date);

    /**
     * repo에 있는 모든 데이터 리턴
     * @return : 모든 데이터
     */
    ArrayList<Check> findAll();
}
