package com.example.cananblog.config;

import com.example.cananblog.bean.EmailModel;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.MailSendUtils;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    EmailModel emailModel;
    @Autowired
    MailSendUtils mailSendUtils;

    /**
     * 登录成功后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String ip = IpUtil.getIP();
//        emailModel.setEmailTheme("登录博客后台系统");
        emailModel.setEmailContent("登录成功\t" + "IP地址: " + ip);
        emailModel.setRecieverEmailAddress("1106126069@qq.com");
        mailSendUtils.sendEmailAsText(emailModel);

        httpServletResponse.sendRedirect("/admin/");
    }
}
