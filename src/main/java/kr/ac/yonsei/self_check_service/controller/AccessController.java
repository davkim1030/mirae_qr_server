package kr.ac.yonsei.self_check_service.controller;

import kr.ac.yonsei.self_check_service.domain.Access;
import kr.ac.yonsei.self_check_service.domain.User;
import kr.ac.yonsei.self_check_service.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 출입 기록(Access) 관련 Controller
 */
@Controller
public class AccessController {
    private final AccessService accessService;

    // Constructor
    @Autowired
    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    // 출입 기록 추가를 위해 get으로 페이지 접근하는 매핑
    @GetMapping("/access/new")
    public String accessNewForm() {
        return "access/createAccessForm";
    }

    // 출입 기록 추가하는 페이지 post로 접근해서 데이터 추가
    @PostMapping("/access/new")
    public String createAccess(AccessForm form) {
        System.out.println(form.getDateTime());
        System.out.println(form.getBuildingType());
        System.out.println(form.getUser().getUserType());
        System.out.println(form.getUser().getKeyNumber());
//        Access access
//                = new Access(
//                form.getDateTime(),
//                form.getBuildingType(),
//                form.getUser()
//        );
//        accessService.addAccess(access);
        return "access/createAccessForm";
    }
}
