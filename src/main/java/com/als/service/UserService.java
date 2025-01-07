package com.als.service;

import com.als.pojo.User;
import jakarta.validation.constraints.Pattern;

public interface UserService {
    User findByUsername(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePassword(@Pattern(regexp = "^\\S{5,16}$") String password);
}
