package com.athub.rules.entity;

import com.athub.framework.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "rule")
public class RuleEntity extends BaseEntity implements Serializable {

    private String code;

    private String content;

    private String params;


    @Override
    public String toString() {
        return "RuleEntity{" +
                ", code='" + code + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
