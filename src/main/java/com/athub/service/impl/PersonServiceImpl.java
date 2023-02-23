package com.athub.service.impl;

import com.athub.rules.entity.Person;
import com.athub.service.PersonService;
import com.athub.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Wang wenjun
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private RuleService ruleService;

    @Override
    public boolean check(Integer num) {
        return ruleService.list().size() > num;
    }

}
