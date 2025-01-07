package com.als.service.impl;

import com.als.mapper.UserMapper;
import com.als.pojo.User;
import com.als.service.UserService;
import com.als.utils.Md5Util;
import com.als.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //密码需要先进行加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Integer id = ThreadLocalUtil.getId();
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePassword(String password) {
        Integer id = ThreadLocalUtil.getId();
        userMapper.updatePassword(Md5Util.getMD5String(password), id);
    }
}
