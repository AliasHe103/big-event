package com.als.service.impl;

import com.als.mapper.ArticleMapper;
import com.als.pojo.Article;
import com.als.pojo.PageBean;
import com.als.service.ArticleService;
import com.als.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Integer userId = ThreadLocalUtil.getId();
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public Article detail(Integer id) {
        return articleMapper.detail(id);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pageBean = new PageBean<>();

        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        Integer userId = ThreadLocalUtil.getId();
        List<Article> articleList =  articleMapper.list(userId, categoryId, state);
        Page<Article> page = (Page<Article>) articleList;

        pageBean.setTotal(page.getTotal());
        pageBean.setItems(articleList);
        return pageBean;
    }
}
