package ru.erfolk.web.controllers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.erfolk.entities.User;
import ru.erfolk.services.UserService;

import javax.validation.Valid;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.USER_LIST)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping
    public String listUsers(Model model) {
        model.addAttribute("list", userService.findAll());
        return "user-list";
    }

    @RequestMapping("{id}")
    public String form(@PathVariable("id") @NonNull Integer id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/" + Endpoints.USER_LIST;
        }
        model.addAttribute("user", user);
        return "user-form";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @Transactional
    public String save(Model model,
                       @PathVariable("id") @NonNull Integer id,
                       @Valid User user,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "redirect:" + Endpoints.USER_LIST + "/" + id;
        }
        log.warn("user {}", user);
        User entity = userService.findById(id);
        if (entity != null) {
            entity.setUsername(user.getUsername());
            entity.setOrg(user.getOrg());

            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userService.update(entity);
        }

        return "redirect:" + Endpoints.USER_LIST;
    }
}
