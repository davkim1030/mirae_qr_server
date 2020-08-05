package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Check;

import java.time.*;
import java.util.ArrayList;
import java.util.Optional;

public interface CheckRepository {
    Check save(Check check);
    ArrayList<Check> findByKey(String key);
    ArrayList<Check> findByCheck(boolean[] check);
    ArrayList<Check> findByDate(LocalDate date);
    Optional<Check> findByKeyDate(String key, LocalDate date);
    ArrayList<Check> findAll();
}
