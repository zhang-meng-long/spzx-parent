package com.example.manager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/5 15:38
 */
@Mapper
public interface RoleMenuMapper {

//    @Select("select menu_id from sys_role_menu where role_id = #{roleId} and is_deleted = 0 and is_half = 0")
    List<Long> RoleMenuByRoleId(Long id);

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    void deleteByRoleId(Long roleId);

    @Insert("insert into sys_role_menu (role_id, menu_id) values (#{roleId}, #{menuId})")
    void insert(Long roleId, Long menuId);





}
