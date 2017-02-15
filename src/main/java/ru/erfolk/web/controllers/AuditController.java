package ru.erfolk.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erfolk.audit.AuditService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.AUDIT_LIST)
public class AuditController {

    @Autowired
    private AuditService auditService;

    @RequestMapping
    public String listGroups(Model model) {
        model.addAttribute("list", auditService.findAll());
        return "audit-list";
    }
}
