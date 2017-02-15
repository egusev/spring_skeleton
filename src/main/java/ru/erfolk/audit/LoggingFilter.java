package ru.erfolk.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.erfolk.entities.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Autowired
    private AuditorAware<User> auditorAware;

    @Autowired
    private LogService logService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long id = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long last = (System.currentTimeMillis() - id);
            User user = auditorAware.getCurrentAuditor();
            log.debug("{}, {}, request {}, response {}, took {} ms",
                    id,
                    user,
                    request.getServletPath(),
                    response.getStatus(),
                    last
            );

            logService.save(new Log(new Date(id), last, user == null ? null : user.getId(), request.getServletPath(), response.getStatus()));
        }
    }
}