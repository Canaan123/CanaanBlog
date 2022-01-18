package com.example.cananblog.config;

import com.example.cananblog.bean.EmailModel;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.MailSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationErrorHandler implements AuthenticationFailureHandler {

    @Autowired
    EmailModel emailModel;
    @Autowired
    MailSendUtils mailSendUtils;

    /**
     * 登陆失败后:wq
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String ip = IpUtil.getIP();
//        emailModel.setEmailTheme("登录博客后台系统");
//        emailModel.setEmailContent("登录失败\t" + "IP地址: " + ip);
//        emailModel.setRecieverEmailAddress("1106126069@qq.com");
//        mailSendUtils.sendEmailAsText(emailModel);
        httpServletResponse.sendRedirect("/login?error=true");
//        httpServletResponse.sendRedirect("/login?error=true");
    }
}
