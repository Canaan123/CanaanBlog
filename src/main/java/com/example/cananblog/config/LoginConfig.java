package com.example.cananblog.config;

import com.example.cananblog.bean.EmailModel;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.MailSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Authenticator;

@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    VerificationCodeFilter verificationCodeFilter;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    MyAuthenticationErrorHandler myAuthenticationErrorHandler;
    @Autowired
    ImageCodeFilter imageCodeFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class);

        // 首页所有人都可以访问，后台只有对应权限的人才能访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/downloadEssay").permitAll(); // 允许所有人下载
//                .antMatchers("/admin/**").hasRole("vip");//开发中暂时关闭
        // 跳转登录界面
        http.formLogin().loginPage("/login")// 登录界面url
                .loginProcessingUrl("/login")  // 登录验证url
                .defaultSuccessUrl("/admin/") // 成功登录界面
                .permitAll() // 设置所有人都可以访问登录页面
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationErrorHandler);


        //http.formLogin();
        http.logout().permitAll();
        http.csrf().disable();// 禁用跨站攻击
        http.headers().frameOptions().disable();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("canaan").password("123456").roles("vip");
    }

}
