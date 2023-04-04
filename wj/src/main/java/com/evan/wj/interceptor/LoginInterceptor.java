package com.evan.wj.interceptor;

import com.evan.wj.pojo.User;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行 options 请求，否则无法让前端带上自定义的 header 信息，导致 sessionID 改变，shiro 验证失败
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        Subject subject = SecurityUtils.getSubject();
        //使用 shiro 验证
        if (!subject.isAuthenticated() && !subject.isRemembered()){
            return false;
        }
        return true;
    }

    /**
     * 检查给定的页面名称page是否以requiredAuthPages数组中的任何一个字符串为开头，
     * 如果是则返回true，否则返回false。
     * 其中，StringUtils.startsWith方法判断一个字符串是否以给定的前缀开头。
     * @param page 给定的页面名称
     * @param requiredAuthPages 需求的页面
     * @return true，false
     */
    private boolean begingWith(String page,String[] requiredAuthPages){
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages){
            if(StringUtils.startsWith(page,requiredAuthPage)){
                result = true;
                break;
            }
        }
        return result;
    }
}
