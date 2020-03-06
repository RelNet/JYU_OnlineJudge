package database;

import data.user.User;
import database.user.UserService;
import org.junit.Test;

public class TestUser {
    @Test
    public void testInsert() {
        UserService service = UserService.copy();
        User user = new User();
        user.setPassword("nihao");
        user.setUsername("nihuai");
    }
}
