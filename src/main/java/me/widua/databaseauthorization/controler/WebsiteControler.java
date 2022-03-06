package me.widua.databaseauthorization.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
// Jest to kontroler który zarządza stroną informacyjną o projekcie.
public class WebsiteControler {


    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/documentation")
    public String getDocumentation(){
        return "documentation";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/test")
    public String getTest(){
        return "test";
    }
}
