package com.example.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.SysRoleMapper;
import com.example.manager.mapper.UserRoleMapper;
import com.example.manager.service.SysRoleService;
import com.example.spzx.model.dto.system.SysRoleDto;
import com.example.spzx.model.entity.system.SysRole;
import com.example.spzx.model.entity.system.SysRoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/1 22:02
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Override
    public PageInfoTest<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {


//查询全部
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(sysRoleDto.getRoleName() != null , SysRole::getRoleName , sysRoleDto.getRoleName())
                .orderByDesc(SysRole::getId);

        IPage<SysRole> page=new Page<>(pageNum,pageSize);

        IPage<SysRole> sysRoleIPage = sysRoleMapper.selectPage(page, lambdaQueryWrapper);
        List<SysRole> sysRoles = sysRoleIPage.getRecords();
        long total = sysRoleIPage.getTotal();

        PageInfoTest<SysRole> pageInfo = new PageInfoTest<>(sysRoles,total) ;

        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        // 指定日期时间格式
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        System.err.println(formattedDate);

        Date date=null;
        try {
             date = formatter.parse(formattedDate);
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sysRole.setUpdateTime(date);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void deleteByid(Integer roleId) {
        sysRoleMapper.deleteById(roleId);





    }

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        LambdaQueryWrapper<SysRoleUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysRoleUser::getRoleId);
        lambdaQueryWrapper.eq(userId!=null,SysRoleUser::getUserId,userId);
        List<SysRoleUser> sysRoleUsers = userRoleMapper.selectList(lambdaQueryWrapper);

        List<Long> longList = sysRoleUsers.stream().map(SysRoleUser::getRoleId).toList();



        Map<String,Object> map=new HashMap<>();
        map.put("roleId",longList);
        map.put("roleList",roleList);
        return map;
    }
}
