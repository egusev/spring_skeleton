package ru.erfolk.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erfolk.services.RightService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.RIGHT_LIST)
public class RightController {

    @Autowired
    private RightService rightService;

    @RequestMapping
    public String listRights(Model model) {
        model.addAttribute("list", rightService.findAll());
        return "right-list";
    }
}
