package kr.ac.yonsei.self_check_service.service;

import kr.ac.yonsei.self_check_service.domain.Access;
import kr.ac.yonsei.self_check_service.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 출입 기록(Access) 서비스
 */
@Service
public class AccessService {
    private final AccessRepository accessRepository; // 저장소 구현체

    // Constructor
    @Autowired
    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    /**
     * 출입기록 추가 메서드
     * @param access : 추가할 출입 기록
     */
    public void addAccess(Access access) {
        accessRepository.save(access);
    }
}
