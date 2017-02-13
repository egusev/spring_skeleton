package ru.erfolk.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Set;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@RestController
public class EndpointController {
    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public EndpointController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @RequestMapping(value = Endpoints.ENDPOINTS, method = RequestMethod.GET)
    public Set<RequestMappingInfo> show() {
        return handlerMapping.getHandlerMethods().keySet();
    }
}