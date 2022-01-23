package com.kycni.community.service;

import com.kycni.community.mapper.UserMapper;
import com.kycni.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/20 8:37
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 对用户信息的创建和修改操作
     */
    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setBio(user.getBio());
            dbUser.setSource(user.getSource());
            userMapper.update(dbUser);
        }
    }
}
