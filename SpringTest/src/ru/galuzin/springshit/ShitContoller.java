package ru.galuzin.springshit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Galuzin on 09.01.2016.
 */
@Controller
public class ShitContoller {
    @RequestMapping("/publicHey")
    public String/*ModelAndView*/ publicHello(){
        System.out.println("GOOD WORK !");
        return "publicHello";
//        return new ModelAndView("publicHello");
    }
}
