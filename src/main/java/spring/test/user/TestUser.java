package spring.test.user;

import data.user.User;
import data.user.UserInfo;
import database.Connect;
import database.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {
    @Test
    public void testInsert() {
        Connect.init("47.100.33.84", "root", "HuangZiHangChenYuZhangWengKangLingZeXiao", "3300", "ojtest2");
        UserService service = UserService.copy();
        User user = new User();
        user.setPassword("nihao");
        user.setUsername("nihuai");
        UserInfo userInfo = new UserInfo();
        userInfo.setClassId("233班");
        userInfo.setSchool("JYU");
        userInfo.setStudentId(1111);
        int id = service.insert(user, userInfo);
        System.out.println(id);
    }
}
