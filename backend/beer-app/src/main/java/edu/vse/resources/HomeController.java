package edu.vse.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping({"/app", "/app/**"})
    public ModelAndView redirectToMainPage() {
        return new ModelAndView("redirect:/");
    }
}