package com.athub.model;

import lombok.Data;

@Data
public class Result {

    private int code;//状态码

    private String message;//信息

    private Object data;//数据

}

