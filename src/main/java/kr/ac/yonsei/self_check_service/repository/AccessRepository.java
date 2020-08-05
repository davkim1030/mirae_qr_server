package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Access;

import java.util.ArrayList;
import java.time.*;

/**
 * Access(출입 기록)를 관리하는 Repo interface
 */
public interface AccessRepository {
    /**
     * Access 인스턴스를 Repo에 저장
     * @param access : 저장할 Access 인스턴스
     * @return : 저장한 값을 리턴
     */
    Access save(Access access);

    /**
     * Repo에서 데이터를 key(학번이나 전화번호)로 검색
     * @param key : 검색할 key
     * @return : 결과가 하나 이상일 수 있으므로 ArrayList<Access>
     */
    ArrayList<Access> findByKey(String key);

    /**
     * Repo에서 데이터를 buildingType(건물 종류)으로 검색
     * @param buildingType : 검색할 건물 종류
     * @return : 결과가 하나 이상일 수 있으므로 ArrayList<Access>
     */
    ArrayList<Access> findByBuilding(Access.BuildingType buildingType);

    /**
     * Repo에서 데이터를 dateTime(날짜 & 시간)으로 검색
     * 범위가 아니라 정확히 일치하는 데이터만 검색
     * @param dateTime : 검색할 dateTime(날짜 & 시간)
     * @return : 결과가 하나 이상일 수 있으므로 ArrayList<Access>
     */
    ArrayList<Access> findByDate(LocalDateTime dateTime);

    /**
     * Repo 안에 있는 모든 데이터 가져오기
     * @return : 결과가 하나 이상이므로 ArrayList<Access>
     */
    ArrayList<Access> findAll();
}
