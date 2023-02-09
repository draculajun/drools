package com.athub.controller;

import com.athub.model.Result;
import com.athub.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Wang wenjun
 */
@RestController
@RequestMapping()
public class DroolsController {

    @PostMapping("/test1")
    public Result test1() {

        return ResultUtil.success(null);
    }

}
