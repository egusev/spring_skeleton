package ru.erfolk.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
public class GeneralController {

    // Match everything without a suffix (so not a static resource)
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
            // Forward to home page so that route is preserved.
            return "forward:/";
    }

    @RequestMapping()
    public String homepage(Model model, Principal principal) {
        return principal != null ? "welcome" : "login";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

}
