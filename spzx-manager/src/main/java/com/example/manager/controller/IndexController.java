package com.example.manager.controller;

import com.example.common.util.AutoThreadLocal;
import com.example.manager.service.SysMenuService;
import com.example.manager.service.SysUserService;
import com.example.manager.service.validateCodeService;
import com.example.spzx.model.dto.system.LoginDto;
import com.example.spzx.model.dto.system.SysUserDto;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.system.LoginVo;
import com.example.spzx.model.vo.system.SysMenuVo;
import com.example.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/10/31 16:24
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

//用户登录
    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
       LoginVo loginVo= sysUserService.login(loginDto);
return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

//    生成验证码：
@Autowired
private validateCodeService validateCodeService;


    @Operation(summary = "生成验证码")
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo  validateCodeVo=  validateCodeService.createvalidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }
//用户退出
    @Operation(summary = "用户退出")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);



    }



//获取登录用户信息
    @Operation(summary = "获取登录用户信息")
    @GetMapping(value = "/getUserInfo")
public Result getUserInfo(@RequestHeader(name = "token") String token){
        return Result.build(AutoThreadLocal.getUser(), ResultCodeEnum.SUCCESS);
    }
    @Autowired
    private SysMenuService sysMenuService;
//动态菜单



    @GetMapping("/menus")
  public Result menus(){
     List<SysMenuVo> voList= sysMenuService.menus();

return Result.build(voList, ResultCodeEnum.SUCCESS);
    }



}
