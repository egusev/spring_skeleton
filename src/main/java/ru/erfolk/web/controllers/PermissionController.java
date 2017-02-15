package ru.erfolk.web.controllers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.erfolk.entities.Permission;
import ru.erfolk.services.PermissionService;

import javax.validation.Valid;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.RIGHT_LIST)
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping
    public String listRights(Model model) {
        model.addAttribute("list", permissionService.findAll());
        return "permission-list";
    }

    @RequestMapping("{id}")
    public String form(@PathVariable("id") @NonNull Integer id, Model model) {
        Permission permission = id > 0 ? permissionService.findById(id) : new Permission();
        if (permission == null) {
            return "redirect:" + Endpoints.RIGHT_LIST;
        }
        model.addAttribute("permission", permission);
        return "permission-form";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @Transactional
    public String save(Model model,
                       @PathVariable("id") @NonNull Integer id,
                       @Valid Permission permission,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("permission", permission);
            return "redirect:" + Endpoints.RIGHT_LIST + "/" + id;
        }

        Permission entity = id > 0 ? permissionService.findById(id) : new Permission();
        if (entity != null) {
            entity.setPath(permission.getPath());
            entity.setMethod(permission.getMethod());
            entity.setPermission(permission.getPermission());

            if (entity.getId() == null) {
                permissionService.create(entity);
            } else {
                permissionService.update(entity);
            }
        }

        return "redirect:" + Endpoints.RIGHT_LIST;
    }
}
