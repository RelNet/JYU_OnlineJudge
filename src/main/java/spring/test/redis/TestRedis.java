package spring.test.redis;

import data.school.School;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testWrite() {
        School school = new School();
        ArrayList<String> list = new ArrayList<>();
        list.add("其他");
        list.add("嘉应学院");
        school.setSchoolList(list);
        redisTemplate.opsForValue().set("schools", school);
    }

    @Test
    public void testRead() {
        School school = (School) redisTemplate.opsForValue().get("schools");
        System.out.println(school);
    }
}
