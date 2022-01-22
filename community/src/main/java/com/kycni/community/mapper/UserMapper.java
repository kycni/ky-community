package com.kycni.community.mapper;

import com.kycni.community.model.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Kycni
 * @date 2022/1/16 13:01
 */
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    /**
     * 插入
     *
     * @param user 用户
     * @return int
     */
    @Override
    int insert(User user);

    /**
     * 找到了令牌
     *
     * @param token 令牌
     * @return {@link User}
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
    
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
