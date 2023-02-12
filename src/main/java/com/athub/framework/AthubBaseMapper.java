package com.athub.framework;

import com.athub.framework.entity.BaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AthubBaseMapper<T> extends BaseMapper<T> {

    Integer selectPageCount(T t);

    List<T> selectPageList(T t);

    <T extends BaseEntity> List<T> selectList(T t);

}
