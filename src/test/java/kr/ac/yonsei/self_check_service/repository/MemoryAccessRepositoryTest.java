package kr.ac.yonsei.self_check_service.repository;

import kr.ac.yonsei.self_check_service.domain.Access;
import kr.ac.yonsei.self_check_service.domain.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemoryAccessRepositoryTest {
    MemoryAccessRepository repository = new MemoryAccessRepository();

    @Test
    public void 저장()
    {
        // given
        String key = "2013253048";
        LocalDateTime dateTime = LocalDateTime.now();
        Access.BuildingType buildingType = Access.BuildingType.CHANG_JO;
        User user = new User(key, User.UserType.MEMBER);
        Access access = new Access(dateTime, buildingType, user);
        // when
        repository.save(access);

        // then
        ArrayList<Access> result = repository.findByKey(key);
        assertThat(result.get(0)).isEqualTo(access);
    }

}
