package com.itheima.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    //目标资源方法运行之前运行，返回true：放行
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        //获取请求url
        String url = req.getRequestURL().toString();
        log.info("url:{}", url);

        //判断请求url是否包含login,如果包含为登录请求
        if (url.contains("login")){
            log.info("登录请求");
            return true;
        }
        //获得请求头的令牌
        String jwt = req.getHeader("token");

        //判断是否存在
        if (!StringUtils.hasLength(jwt)){
            log.info("令牌token为空");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象---json  fastjson
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        //解析token 如果解析失败，返回错误
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("令牌解析失败");
            return false;
        }
        //放行
        log.info("令牌合法");
       return true;
    }

    //标资源方法后运行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    //试图渲染完毕后运行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
