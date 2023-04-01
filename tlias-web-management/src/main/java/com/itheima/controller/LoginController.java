package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import com.itheima.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("员工登录 emp:{}", emp);
        Emp e= empService.login(emp);

        //登录成功，生成令牌，并且下发，如果失败返回错误信息
        if (e != null) {
            HashMap<String,Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("name", e.getName());
            claims.put("username",e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);//jwt包含当前登录的信息
            log.info("生成的token:{}", jwt);
            return Result.success(jwt);
        }else {
            return Result.error("用户名或者密码错误");
        }




//        return e !=null?Result.success():Result.error("用户名或者密码错误");
    }
}
