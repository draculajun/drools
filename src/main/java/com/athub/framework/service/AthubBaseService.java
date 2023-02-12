package com.athub.framework.service;

import com.athub.framework.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author Wang wenjun
 */
public interface AthubBaseService<T> extends IService {

    T insert(T t);

    T update(T t);

    T selectOne(Long id);

    void delete(Long id);

    Integer selectPageCount(T t);

    List<T> selectPageList(T t);

    BaseEntity page(T t);

    void saveBatch(List<T> t);

    List<T> selectList(T t);
}
