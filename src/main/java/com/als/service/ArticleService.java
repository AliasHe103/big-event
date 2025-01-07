package com.als.service;

import com.als.pojo.Article;
import com.als.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article detail(Integer id);

    void delete(Integer id);
}
