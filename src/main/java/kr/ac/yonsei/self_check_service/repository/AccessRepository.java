package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Access;

import java.util.ArrayList;
import java.time.*;

public interface AccessRepository {
    Access save(Access access);
    ArrayList<Access> findByKey(String key);
    ArrayList<Access> findByBuilding(Access.BuildingType buildingType);
    ArrayList<Access> findByDate(LocalDateTime dateTime);
    ArrayList<Access> findAll();
}
