package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

@Slf4j
//@WebFilter("/login")
public class LoginCheckFliter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取请求url
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        String url = req.getRequestURL().toString();
        log.info("url:{}", url);

        //判断请求url是否包含login,如果包含为登录请求
        if (url.contains("login")){
            log.info("登录请求");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //获得请求头的令牌
        String jwt = req.getHeader("token");

        //判断是否存在
        if (StringUtils.hasLength(jwt)){
            log.info("令牌token为空");
            Result error = Result.error("NOT_;PGIN");
            //手动转换 对象---json  fastjson
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //解析token 如果解析失败，返回错误
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("令牌解析失败");
        }
        //放行
        log.info("令牌合法");
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
