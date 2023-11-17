package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.system.SysRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 张乔
 * @Date 2023/11/4 15:06
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<SysRoleUser> {
    @Delete("DELETE FROM sys_user_role WHERE user_id = #{userId}")
    void deletes(Long userId);

    @Insert("insert into sys_user_role (user_id, role_id) values (#{userId}, #{roleId})")
    void inserts(Long userId, Long roleId);
}







