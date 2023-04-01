package com.itheima.controller;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@RestController
public class SessionController {
 //设置cookie
    @GetMapping("/c1")
    public Result cookie1(HttpServletResponse response) {
        response.addCookie(new Cookie("login_username","xiaou"));
        return Result.success();
    }
    //获得cookie
    @GetMapping("/c2")
    public Result cookie2(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login_username")) {
                System.out.println("login_userbane: "+cookie.getValue());
            }
        }
        return Result.success();
    }
    //往httpSession中存储值
    @GetMapping("/s1")
    public Result session1(HttpSession session){
        log.info("s1{}",session.hashCode());
        session.setAttribute("login_username", "xiaou");
        return Result.success();
    }
    //从httpsession中获得值
    @GetMapping("/s2")
    public Result session2(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("s2{}", session.hashCode());
        Object obj = session.getAttribute("login_username");
        log.info("s2: {}", obj);
        return Result.success();
    }
}
