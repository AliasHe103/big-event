package com.als.service.impl;

import com.als.mapper.CategoryMapper;
import com.als.pojo.Category;
import com.als.service.CategoryService;
import com.als.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Integer userId = ThreadLocalUtil.getId();

        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Integer userId = ThreadLocalUtil.getId();
        return categoryMapper.list(userId);
    }

    @Override
    public Category findByCategoryId(Integer id) {
        return categoryMapper.findByCategoryId(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
