package java.RunWeb.Spring.Web;

import org.junit.Test;

import java.RunWeb.Spring.Data.OJ;
import java.util.ArrayList;
import java.util.List;

public class OJControllerTest {
    @Test
    public void shouldShowRecentOJ() throws Exception {
        List<OJ> expectedOJs = creatOJList(20);
    }

    private List<OJ> creatOJList(int count) {
        List<OJ> OJs = new ArrayList<>();
        for (int i = 0; i < count; i++){
            OJs.add(new OJ())
        }
    }
}
