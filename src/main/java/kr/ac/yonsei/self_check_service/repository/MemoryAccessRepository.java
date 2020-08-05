package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Access;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Access 데이터 저장 구현체
 */
@Repository
public class MemoryAccessRepository implements AccessRepository{

    private static ArrayList<Access> store = new ArrayList<>(); // 데이터 저장할 변수

    @Override
    public Access save(Access access) {
        store.add(access);
        return access;
    }

    @Override
    public ArrayList<Access> findByKey(String key) {
        ArrayList<Access> result = new ArrayList<>();
        for (Access item: store) {
            if (item.getUser().getKeyNumber().equals(key))
                result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Access> findByBuilding(Access.BuildingType buildingType) {
        ArrayList<Access> result = new ArrayList<>();
        for (Access item: store) {
            if (item.getBuildingType().equals(buildingType))
                result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Access> findByDate(LocalDateTime dateTime) {
        ArrayList<Access> result = new ArrayList<>();
        for (Access item: store) {
            if (item.getDateTime().equals(dateTime))
                result.add(item);
        }
        return result;
    }

    @Override
    public ArrayList<Access> findAll() {
        return store;
    }
}
