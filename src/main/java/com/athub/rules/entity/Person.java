package com.athub.rules.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wang wenjun
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;

    private Integer age;

    private Integer score;

    private String className;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

}
