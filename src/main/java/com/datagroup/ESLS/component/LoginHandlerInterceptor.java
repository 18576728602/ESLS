package com.datagroup.ESLS.component;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;
    //执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String token = request.getHeader("X-ESLS-TOKEN");
        if( token==null ){
            //未携带头部字段
            return true;
            //return returnWithError(response,"请在header携带"+"[ X-ESLS-TOKEN ]"+"字段");
        }
        try {
            String userId = JWT.decode(token).getToken();
            System.out.println(userId);
            flag = redisUtil.isExist(token);
        }
        catch (Exception e){}
        if(!flag){
            // token过期
            return returnWithError(response,"token无效或过期！请重新登录");
        }
        else
            //已登录 放行请求
            return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    public boolean returnWithError(HttpServletResponse response, String msg) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(ResultBean.error(msg)));
        return false;
    }
}
