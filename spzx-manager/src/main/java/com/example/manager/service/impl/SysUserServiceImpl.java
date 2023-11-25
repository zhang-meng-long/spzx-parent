package com.example.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zq.common.service.exception.CustomException;
import com.example.manager.config.Constant;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.SysUserMapper;
import com.example.manager.mapper.UserRoleMapper;
import com.example.manager.service.SysUserService;
import com.example.spzx.model.dto.system.AssginRoleDto;
import com.example.spzx.model.dto.system.LoginDto;
import com.example.spzx.model.dto.system.SysUserDto;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/10/31 16:25
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//    @Override
//    public LoginVo login(LoginDto loginDto) {
//
//        // 根据用户名查询用户
//        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
//        if(sysUser == null) {
//            throw new RuntimeException("用户名或者密码错误") ;
//        }
//
//        // 验证密码是否正确
//        String inputPassword = loginDto.getPassword();
//        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
//        if(!md5InputPassword.equals(sysUser.getPassword())) {
//            throw new RuntimeException("用户名或者密码错误") ;
//        }
//
//        // 生成令牌，保存数据到Redis中
//        String token = UUID.randomUUID().toString().replace("-", "");
//        redisTemplate.opsForValue().set(Constant.LOGIN + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);
//
//        // 构建响应结果对象
//        LoginVo loginVo = new LoginVo() ;
//        loginVo.setToken(token);
//        loginVo.setRefresh_token("");
//
//        // 返回
//        return loginVo;
//    }
//用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

//先判断验证码是否正确
        String codeKey = loginDto.getCodeKey();
        String captcha = loginDto.getCaptcha();
        String captchaCode = redisTemplate.opsForValue().get(Constant.CODE + codeKey);
//        判断redis中验证码不为空，并在比较时忽略大小写。
        if(StrUtil.isEmpty(captchaCode) || !captcha.equalsIgnoreCase(captchaCode)){
            throw new CustomException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete(Constant.CODE + codeKey);

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUserName,loginDto.getUserName());

        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        if (sysUser==null){
            throw new CustomException(ResultCodeEnum.LOGIN_USERNAME_ERROR);
        }

        String loginDtoPassword = loginDto.getPassword();
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(loginDtoPassword.getBytes());
        if (!md5DigestAsHex.equals(sysUser.getPassword())){
            throw new CustomException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        String token= UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(Constant.LOGIN+token,JSON.toJSONString(sysUser),
                5, TimeUnit.DAYS);
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }


//获取登录用户信息

    @Override
    public SysUser getUserInfo(String token) {
        String sysUserToken = redisTemplate.opsForValue().get(Constant.LOGIN + token);
        System.out.println("用户登录信息");
        SysUser sysUser = JSON.parseObject(sysUserToken, SysUser.class);
        return sysUser;
    }

//    用户退出
    @Override
    public void logout(String token) {
        redisTemplate.delete(Constant.LOGIN + token);
    }

    @Override
    public PageInfoTest<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        IPage<SysUser> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(sysUserDto.getKeyword()!=null&&!StrUtil.isEmpty(sysUserDto.getKeyword())
                        ,SysUser::getUserName,
                        sysUserDto.getKeyword())
                .ge(sysUserDto.getCreateTimeBegin()!=null&&!StrUtil.isEmpty(sysUserDto.getCreateTimeBegin())
                        ,SysUser::getCreateTime,
                        sysUserDto.getCreateTimeBegin())
                .le(sysUserDto.getCreateTimeEnd()!=null&&!StrUtil.isEmpty(sysUserDto.getCreateTimeEnd())
                        ,SysUser::getCreateTime,
                        sysUserDto.getCreateTimeEnd())
                .orderByDesc(SysUser::getId);


        IPage<SysUser> sysUserIPage = sysUserMapper.selectPage(page, queryWrapper);
        List<SysUser> records = sysUserIPage.getRecords();
        long total = sysUserIPage.getTotal();
        PageInfoTest<SysUser> pageInfoTest=new PageInfoTest<>(records,total);


        return pageInfoTest;
    }

//    新增用户
    @Override
    public void saveSysUser(SysUser sysUser) {
//判断用户名是否重复
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUserName,sysUser.getUserName());
        SysUser selectOne = sysUserMapper.selectOne(lambdaQueryWrapper);
        if (selectOne!=null){
            throw new CustomException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
//        对密码加密
        String password = sysUser.getPassword();
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(md5DigestAsHex);

        sysUserMapper.insert(sysUser);


    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        //判断用户名是否重复
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUserName,sysUser.getUserName());
        SysUser selectOne = sysUserMapper.selectOne(lambdaQueryWrapper);

        if (selectOne!=null&&!sysUser.getId().equals(selectOne.getId())){
            throw new CustomException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

//            对传入的密码进行加密

//        String password = sysUser.getPassword();
//        String mdsPassword = DigestUtils.md5DigestAsHex(password.getBytes());
//sysUser.setPassword(mdsPassword);

        sysUser.setUpdateTime(new Date());

//1可用，0不可用
sysUser.setStatus(1);


sysUserMapper.updateById(sysUser);

    }

    @Override
    public void deleteById(Integer id) {

        sysUserMapper.deleteByIds(id);
    }

@Autowired
private UserRoleMapper userRoleMapper;

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
//    先删除已经绑定的角色
        userRoleMapper.deletes(assginRoleDto.getUserId());

//        再绑定传入的角色
        List<Long> roleIdList = assginRoleDto.getRoleIdList();

        for (Long roleId : roleIdList){
            userRoleMapper.inserts(assginRoleDto.getUserId(),roleId);
        }
    }
}
