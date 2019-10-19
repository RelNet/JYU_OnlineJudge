package test.OJWeb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.RunWeb.Spring.Config.RootConfig;
import java.RunWeb.Spring.Config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/java/RunWeb/Spring/Config")
@ContextHierarchy({@ContextConfiguration(name = "parent", classes = RootConfig.class),
        @ContextConfiguration(name = "child", classes = WebConfig.class)})
public class HomeControllerTest {
    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void setupMock() {
//        HomeController controller = new HomeController();
//        mockMvc = standaloneSetup(controller).build();
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
