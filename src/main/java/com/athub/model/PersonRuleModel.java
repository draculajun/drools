package com.athub.model;

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
public class PersonRuleModel {

    private String name;

    private Integer age;

    private Integer score;

    @Override
    public String toString() {
        return "PersonRuleModel{" +
                "name='" + name + '\'' +
                "age='" + age + '\'' +
                ", score=" + score +
                '}';
    }

}
