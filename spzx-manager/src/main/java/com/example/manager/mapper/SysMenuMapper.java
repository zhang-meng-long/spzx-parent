package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/4 16:37
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    List<SysMenu> selectLists();
    void inserts(SysMenu sysMenu);


    void updateByIds(SysMenu sysMenu);

    int deletes(Long id);

    void deleteByIds(Long id);

    List<SysMenu> selectListByUserId(Long userId);
}
