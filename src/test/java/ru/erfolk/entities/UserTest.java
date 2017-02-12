package ru.erfolk.entities;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.erfolk.BaseDBUnitTest;
import ru.erfolk.audit.Audit;
import ru.erfolk.audit.AuditRepository;
import ru.erfolk.config.DataConfiguration;
import ru.erfolk.config.RootConfiguration;
import ru.erfolk.services.UserService;

import java.util.List;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfiguration.class, DataConfiguration.class})
@WebAppConfiguration
@Configuration
@Slf4j
public class UserTest extends BaseDBUnitTest {


    @Autowired
    private UserService userService;

    @Autowired
    private AuditRepository auditRepository;

    @Test
    @DatabaseSetup({
            "/database/users.xml",
    })
//    @Transactional
    public void testPopulation() throws Exception {
        User user = userService.updateUser(2);
        user = userService.findById(2);

        userService.delete(2);

        user = new User();
        user.setOrg("new org");
        user.setUsername("new username");
        user.setPassword("********");
        user.setRole(null);
        Integer id = userService.create(user);

        userService.updateUser(id);

        List<Audit> auditList = auditRepository.findAll();
        for (Audit audit : auditList) {
            log.warn("{}", audit);
        }
    }


}