package com.als.controller;

import com.als.pojo.Article;
import com.als.pojo.PageBean;
import com.als.pojo.Result;
import com.als.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article) {
        articleService.update(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article article = articleService.detail(id);
        return Result.success(article);
    }

    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }
}
