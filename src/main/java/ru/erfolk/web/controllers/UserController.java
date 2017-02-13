package ru.erfolk.web.controllers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erfolk.entities.User;
import ru.erfolk.services.UserService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.USER_LIST)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String listUsers(Model model) {
        model.addAttribute("list", userService.findAll());
        return "user-list";
    }

    @RequestMapping("{id}")
    public String form(@NonNull Integer id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/" + Endpoints.USER_LIST;
        }
        model.addAttribute("user", user);
        return "user-form";
    }
}
