package com.example.cananblog.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean {
    @Autowired
    private RedisTemplate redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 只在登录时对验证码进行拦截,验证
     */
    private static String url = "/login";
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String t = request.getRequestURI();
        if (antPathMatcher.match(url,request.getRequestURI())&& "POST".equals(request.getMethod())){
            checkImageCode(request,response,filterChain);
            return;
        }
        filterChain.doFilter(request,response);
    }

    /**
     *
     * Description:从cookie中取出redis的kye，拿着key取出对应的value,验证图片验证码是否正确
     * @param httpServletRequest
     * @param filterChain
     */
    private void checkImageCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        /*从cookie取值*/
        Cookie[] cookies = httpServletRequest.getCookies();
        String uuid = "";
        for (Cookie cookie : cookies)
        {
            String cookieName = cookie.getName();
            if ("captcha".equals(cookieName))
            {
                uuid = cookie.getValue();
            }
        }
        String redisImageCode = (String) redisTemplate.opsForValue().get(uuid);
        /*获取图片验证码与redis验证*/
        String imageCode = httpServletRequest.getParameter("code");
        /*redis的验证码不能为空*/
        if (StringUtils.isEmpty(redisImageCode) || StringUtils.isEmpty(imageCode))
        {
            httpServletRequest.setAttribute("loginfail","验证码不能为空");
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/login?error=true");
            requestDispatcher.forward(httpServletRequest,httpServletResponse);
            return;
        }
        /*校验验证码*/
        if (!imageCode.equalsIgnoreCase(redisImageCode))
        {
            httpServletRequest.setAttribute("loginfail","验证码错误");
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/login?error=true");
            requestDispatcher.forward(httpServletRequest,httpServletResponse);
            return;
        }
        redisTemplate.delete(redisImageCode);
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
