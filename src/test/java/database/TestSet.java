package database;

import data.problem.Problem;
import database.set.SetService;
import org.junit.Test;

import java.util.ArrayList;

public class TestSet {
    @Test
    public void get() {
        Connect.init("47.100.33.84", "root", "HuangZiHangChenYuZhangWengKangLingZeXiao", "3300", "ojtest2");
        SetService service = SetService.copy();
        System.out.println(service.maxPage());
    }
}
