package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Author 张乔
 * @Date 2023/10/31 16:25
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByUserName(String  userName);

@Update("update sys_user set update_time=now(), is_deleted=1 where id=#{id}")
    void deleteByIds(Integer id);
}
