package com.kycni.community.mapper;

import com.kycni.community.model.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.ExampleMapper;

/**
 * @author Kycni
 * @date 2022/1/16 13:01
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,bio,source) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio},#{source})")
    void insert(User user);

    /**
     * 找到了令牌
     *
     * @param token 令牌
     * @return {@link User}
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
    
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl}, bio = #{bio}, source = #{source} where id = #{id}")
    void update(User user);
}
