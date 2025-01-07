package com.als.service;

import com.als.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);

    List<Category> list();

    Category findByCategoryId(Integer id);

    void update(Category category);

    void delete(Integer id);
}
