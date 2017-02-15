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
import ru.erfolk.entities.Role;
import ru.erfolk.services.PermissionService;
import ru.erfolk.services.RoleService;

import javax.validation.Valid;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Controller
@Slf4j
@RequestMapping(Endpoints.ROLE_LIST)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping
    public String listGroups(Model model) {
        model.addAttribute("list", roleService.findAll());
        return "role-list";
    }

    @RequestMapping("{id}")
    public String form(@PathVariable("id") @NonNull Integer id, Model model) {
        Role role = id > 0 ? roleService.findById(id) : new Role();
        if (role == null) {
            return "redirect:" + Endpoints.ROLE_LIST;
        }
        model.addAttribute("role", role);
        model.addAttribute("permissions", permissionService.findAll());
        return "role-form";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @Transactional
    public String save(Model model,
                       @PathVariable("id") @NonNull Integer id,
                       @Valid Role role,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return "redirect:" + Endpoints.ROLE_LIST + "/" + id;
        }

        Role entity = id > 0 ? roleService.findById(id) : new Role();
        if (entity != null) {
            entity.setCode(role.getCode());
            entity.setName(role.getName());
            entity.setRights(role.getRights());

            if (entity.getId() == null) {
                roleService.create(entity);
            } else {
                roleService.update(entity);
            }
        }

        return "redirect:" + Endpoints.ROLE_LIST;
    }

    @RequestMapping(value = "{id}/delete")
    @Transactional
    public String save(@PathVariable("id") @NonNull Integer id) {
        roleService.delete(id);
        return "redirect:" + Endpoints.ROLE_LIST;
    }
}
