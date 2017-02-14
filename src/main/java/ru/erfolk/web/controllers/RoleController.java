package ru.erfolk.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erfolk.services.RoleService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.ROLE_LIST)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping
    public String listGroups(Model model) {
        model.addAttribute("list", roleService.findAll());
        return "role-list";
    }
}
