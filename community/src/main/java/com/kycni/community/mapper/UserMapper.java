package com.kycni.community.mapper;

import com.kycni.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author Kycni
 * @date 2022/1/16 13:01
 */
@Mapper
public interface UserMapper {
    
    /**
     * @param user 将user元素插入user表中
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,bio,source) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio},#{source})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
    
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
    
}
