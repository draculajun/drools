package com.athub.rules.entity;

import lombok.Data;

/**
 * @Author Wang wenjun
 */
@Data
public class Person {

    private String name;

    private Integer age;

    private Integer score;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

}
