package com.athub.service.impl;

import com.athub.rules.entity.Person;
import com.athub.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * @Author Wang wenjun
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Override
    public void sayHi(Person person) {
        System.out.println("Hello：" + person.toString());
    }

}
