package java.RunWeb.Spring.Web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.RunWeb.Spring.Data.OJ;
import java.RunWeb.Spring.Data.OJRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OJControllerTest {
    @Test
    public void shouldShowRecentOJ() throws Exception {
        List<OJ> expectedOJs = creatOJList(20);
        OJRepository mockRepository = mock(OJRepository.class);
        when(mockRepository.findOJs(Long.MAX_VALUE, 20)).thenReturn(expectedOJs);
        OJController controller = new OJController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/web/sites/test.jsp")).build();

        mockMvc.perform(get("/test"))
                .andExpect(view().name("test"))
                .andExpect(model().attributeExists("testList"))
                .andExpect(model().attribute("testList", hasItems(expectedOJs.toArray())));
    }

    private List<OJ> creatOJList(int count) {
        List<OJ> OJs = new ArrayList<>();
        for (Long i = 0L; i < count; i++) {
            String[] tempStrings = {"test", i.toString()};
            OJs.add(new OJ(i, tempStrings, new Date()));
        }
        return OJs;
    }
}
