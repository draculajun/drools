package com.athub.framework;

import com.athub.framework.entity.Result;
import com.athub.framework.exception.BusinessException;
import com.athub.framework.service.AthubBaseService;
import com.athub.framework.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
public abstract class AthubBaseController<T, Service extends AthubBaseService<T>> {

    @Autowired
    protected Service baseService;

    @GetMapping("/{id}")
    @ApiOperation("根据主键编号查询业务实体")
    public Result selectOne(@PathVariable("id") Long id) {
        T t = (T) baseService.selectOne(id);
        return ResultUtil.success(t, "");
    }

    @PostMapping
    @ApiOperation("新增")
    public Result insert(@RequestBody T t) {
        T t1;
        try {
            t1 = baseService.insert(t);
        } catch (BusinessException e) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.success(t1, "新增成功");
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public Result update(@PathVariable("id") Long id, @RequestBody T t) {
        try {
            baseService.update(t);
        } catch (BusinessException e) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.success(t, "更新成功");
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除")
    public Result delete(@PathVariable("id") Long id) {
        try {
            baseService.delete(id);
        } catch (BusinessException e) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.success("删除成功");
    }

    @PostMapping("/list")
    public Result list(@RequestBody T t) {
        return ResultUtil.success(baseService.selectList(t), "LIST获取成功");
    }

    @PostMapping(value = "/page")
    @ApiOperation("分页查询")
    public Result page(@RequestBody T t) {
        return ResultUtil.success(baseService.page(t), "PAGE获取成功");
    }
}
