package com.anranruozhu.utils;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author anranruozhu
 * @ClassName JwtFilter
 * @Description JWT过滤器，拦截 /secure的请求
 * @create 2024/4/7 15:23
 **/
@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/secure/*")
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {
            if (token == null) {
                response.getWriter().write("没有token！");
                return;
            }
            // token 超时判断
            Date expireTime = JWT.decode(token).getExpiresAt();
            Date currentTime = new Date(System.currentTimeMillis());
//            if ( currentTime.before(expireTime)){
//                response.setStatus(401);
//                response.getWriter().write("token 过期时间："+ expireTime);
//                return;
//            }
            if ( currentTime.after(expireTime)){
                response.setStatus(401);
                response.getWriter().write("token 已过期, 过期时间为："+ expireTime);
                return;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("token不合法！");
                return;
            }
            Integer id = userData.get("id").asInt();
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            chain.doFilter(req, res);
        }
    }
    @Override
    public void destroy() {
    }
}