package ru.erfolk.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erfolk.audit.AuditService;
import ru.erfolk.audit.LogService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
public class LogsController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private LogService logService;

    @RequestMapping(Endpoints.AUDIT_LIST)
    public String listAuditLogs(Model model) {
        model.addAttribute("list", auditService.findAll());
        return "audit-list";
    }

    @RequestMapping(Endpoints.LOGS_LIST)
    public String listHttpLogs(Model model) {
        model.addAttribute("list", logService.findAll());
        return "log-list";
    }
}
