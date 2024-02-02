package com.zq.service.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.dto.h5.UserLoginDto;
import com.example.spzx.model.dto.h5.UserRegisterDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.user.UserInfo;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.h5.UserInfoVo;
import com.zq.client.product.ProductFeignClient;
import com.zq.service.user.mapper.SysUserMapper;
import com.zq.service.user.mapper.UserInfoMapper;
import com.zq.service.user.service.RegistryService;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/11/17 20:43
 */
@Service
public class RegistryServiceImpl implements RegistryService {

@Autowired
private SysUserMapper sysUserMapper;

@Autowired
private UserInfoMapper userInfoMapper;


//注册用户信息
    @Override
    public void register(UserRegisterDto userRegisterDto) {
//    检验验证码是否正确
        String code = userRegisterDto.getCode();
        if (!"6666".equals(code)){
            throw new RuntimeException("验证码错误");
        }
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
//        插入用户信息前，判断用户名是否重复

        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(!username.isEmpty(),
                UserInfo::getUsername, username));
        if (userInfo!=null){
            throw new RuntimeException("用户名已存在");
        }
//        对输入的密码进行加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
      UserInfo userInfo1 = new UserInfo();
        userInfo1.setUsername(username);
        userInfo1.setPassword(md5DigestAsHex);
userInfo1.setNickName(nickName);
        userInfo1.setPhone(username);
        userInfo1.setStatus(1);
        userInfo1.setSex(0);

//        头像
        userInfo1.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

userInfoMapper.insert(userInfo1);
    }


    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//    用户登录
    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
//        根据用户名查询数据库中信息
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(StringUtils.hasText(username),
                UserInfo::getUsername, username));
        if (userInfo==null){
            throw new RuntimeException("用户名不存在，请先注册");
        }
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5DigestAsHex.equals(userInfo.getPassword())){
            throw new RuntimeException("密码错误");
        }
//校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new RuntimeException("用户状态被禁用");
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(token,  JSON.toJSONString(userInfo),5, TimeUnit.DAYS);

        return token;
    }

//    获取用户信息
    @Override
    public UserInfoVo getUserInfo(String token) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户不存在，请先登录");
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }



@Autowired
private ProductFeignClient productFeignClient;


    @Override
    public List<Product> getPage() {




        return productFeignClient.get();
    }
}



