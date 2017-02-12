package ru.erfolk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.entities.Right;
import ru.erfolk.repositories.RightRepository;
import ru.erfolk.services.RightService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RightServiceImpl extends AbstractServiceImpl<Right, Integer>  implements RightService {

    @Autowired
    private RightRepository rightRepository;

    @Override
    protected CrudRepository<Right, Integer> getRepository() {
        return rightRepository;
    }
}
