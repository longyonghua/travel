package com.longge.dao;

import com.longge.domain.Category;

import java.util.List;

/**
 * @author longge
 * @create 2019-12-10 下午4:36
 */
public interface CategoryDao {
    //查询所有
    List<Category> findAll();
}
