package ru.erfolk.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by eugene on 14.02.17.
 */
@Slf4j
public class UserOrgAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String DELIMITER = ":\t:";

    @Getter
    @Setter
    private String orgParameter = "org";

    @Getter
    @Setter
    private String organization;

    @Getter
    @Setter
    private String login;

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter(getUsernameParameter());
        String organization = request.getParameter(getOrgParameter());

        username = username == null ? "": username.trim();
        organization = organization == null ? "": organization.trim();

        setLogin(username);
        setOrganization(organization);

        String combinedUsername = username + DELIMITER + organization;
        log.debug("Combined username = " + combinedUsername);
        return combinedUsername;
    }
}