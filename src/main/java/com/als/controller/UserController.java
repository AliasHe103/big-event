package com.als.controller;

import com.als.pojo.Result;
import com.als.pojo.User;
import com.als.service.UserService;
import com.als.utils.JwtUtil;
import com.als.utils.Md5Util;
import com.als.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
//        if (username != null && username.length() >= 5 && username.length() <= 16 &&
//            password != null && password.length() >= 5 && password.length() <= 16
//        ) {
//
//        }
        //查询用户是否存在
        User u = userService.findByUsername(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            //用户已被占用
            return Result.error("用户名已被占用");
        }

        //注册
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }

        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数错误");
        }

        //校验旧密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User loginUser = userService.findByUsername(username.toString());
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        //校验新密码是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码不一致");
        }

        userService.updatePassword(newPwd);
        return Result.success();
    }
}
