package com.example.cananblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class VerificationCodeFilter extends GenericFilter {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if("POST".equals(req.getMethod()) && "/login".equals(req.getServletPath())){
            String code = req.getParameter("code");
            String verify_code = (String) redisTemplate.opsForValue().get("verify_code");
            if(verify_code == null){
                req.getSession().setAttribute("loginfail","验证码已过期");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login?error=true");
                requestDispatcher.forward(req,resp);
                return;
            }
            if(code == null || code.equals("") || !code.equalsIgnoreCase(verify_code)){
                req.setAttribute("loginfail","验证码错误");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login?error=true");
                requestDispatcher.forward(req,resp);
                return;
            }
            else{
                chain.doFilter(req,resp);
            }
        }
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
