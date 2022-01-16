package com.kycni.community.mapper;

import com.kycni.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kycni
 * @date 2022/1/16 13:01
 */
@Mapper
public interface UserMapper {
    /**
     * @param user 将user元素插入user表中
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
