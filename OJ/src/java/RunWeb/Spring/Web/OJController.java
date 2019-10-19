package java.RunWeb.Spring.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import java.RunWeb.Spring.Data.OJRepository;

@Controller
@RequestMapping("/test")
public class OJController {
    private OJRepository spittleRepository;

    @Autowired
    OJController(OJRepository spittleRepository){
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String OJs(Model module){
        module.addAttribute("testList", spittleRepository.findOJs(Long.MAX_VALUE, 20));
        return "tests";
    }
}
