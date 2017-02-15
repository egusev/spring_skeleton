package ru.erfolk.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Component
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Log log) {
        logRepository.save(log);
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }
}
