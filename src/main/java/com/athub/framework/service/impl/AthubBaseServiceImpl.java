package com.athub.framework.service.impl;

import com.athub.framework.AthubBaseMapper;
import com.athub.framework.entity.BaseEntity;
import com.athub.framework.service.AthubBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wang wenjun
 */
public class AthubBaseServiceImpl<T extends BaseEntity, M extends AthubBaseMapper<T>> extends ServiceImpl implements AthubBaseService<T> {

    @Autowired
    protected M mapper;

    @Override
    public T insert(T t) {
        t.setLastUpdateTime(LocalDateTime.now());
        mapper.insert(t);
        return t;
    }

    @Override
    public T update(T t) {
        t.setLastUpdateTime(LocalDateTime.now());
        mapper.updateById(t);
        return t;
    }

    @Override
    public T selectOne(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public Integer selectPageCount(T t) {
        return mapper.selectPageCount(t);
    }

    @Override
    public List<T> selectPageList(T t) {
        return mapper.selectPageList(t);
    }

    @Override
    public BaseEntity page(T t) {
        t.setOffset();
        int num = this.selectPageCount(t);
        List<T> list = new ArrayList<T>();
        if (num > 0) {
            list = this.selectPageList(t);
        }
        BaseEntity page = new BaseEntity();
        page.setTotal(num);
        page.setPageData(list);
        return page;
    }

    @Override
    public void saveBatch(List<T> t) {
        for (int i = 0; i < t.size(); i++) {
            t.get(i).setLastUpdateTime(LocalDateTime.now());
        }
        super.saveBatch(t);
    }

    @Override
    public List<T> selectList(T t) {
        return mapper.selectList(t);
    }

}
