package database;

import data.problem.Problem;
import database.problem.ProblemService;
import org.junit.Test;

public class TestProblem {
    @Test
    public void test(){
        Connect.init("47.100.33.84", "root", "HuangZiHangChenYuZhangWengKangLingZeXiao", "3300", "ojtest2");
        ProblemService service=ProblemService.copy();
        Problem problem = service.get(1);
        System.out.println(problem);
    }
}
