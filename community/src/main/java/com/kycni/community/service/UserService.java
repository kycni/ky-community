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
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("accoundId",user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            /*数据库中的数组中的第一个元素*/
            User dbUser = users.get(0);
            /*创建一个更新的用户*/
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setBio(user.getBio());
            updateUser.setSource(user.getSource());
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("id",dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
}
