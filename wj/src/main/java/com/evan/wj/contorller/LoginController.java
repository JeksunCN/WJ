package com.evan.wj.contorller;


import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.evan.wj.pojo.User;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin    //处理跨域请求的注解
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser){
        // 对 html 标签进行转义，防治 XXS 攻击
        String username =requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        //System.out.println(username);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try{
            subject.login(usernamePasswordToken);
            System.out.println(subject.isRemembered());
            System.out.println(subject.isAuthenticated());
            return ResultFactory.buildSuccessResult(username);
        }catch (AuthenticationException e){
            String message = "账号密码错误！";
            return ResultFactory.buildFailResult(message);
        }
    }


    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        //System.out.println("进入注册接口");
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        boolean exist = userService.isExist(username);
        if(exist){
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }

        //生成盐，默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //设置 hash 算法迭代次数
        int times = 2;
        //得到 hash 后的密码
        String md5 = new SimpleHash("md5", password, salt, times).toString();
        //存储用户信息 包括 salt 和 hash 后的密码
        user.setSalt(salt);
        user.setPassword(md5);
        userService.add(user);

        return ResultFactory.buildSuccessResult(user);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "api/authentication")
    public String authentication(){
        return "身份验证成功";
    }

}
