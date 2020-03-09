package spring.test.time;

import org.junit.Test;

import java.sql.Timestamp;

public class TestTimestamp {
    @Test
    public void testSystem() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.toString());
    }

    @Test
    public void testSet() {
        Timestamp timestamp = new Timestamp(2020 - 1900, 3, 8, 12, 5, 0, 0);
        System.out.println(timestamp.toString());
    }
}
