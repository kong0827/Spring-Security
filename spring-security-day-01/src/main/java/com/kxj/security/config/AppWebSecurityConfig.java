package com.kxj.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * @author kongxiangjin
 */
// 声明当前类是一个配置类。相当与XML配置文件作用。
@Configuration
// 声明式配置，启用SpringSecurity安全机制
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth); 默认配置
        // 自定义用户信息(内存)
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123").roles("学徒")
                .and()
                .withUser("lisi").password("123").authorities("罗汉拳","武当长拳");

        // 基于数据库 根据用户名查询出用户的详细信息
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认权限规则，所有请求都收到限制
        // super.configure(http);

        // 授权首页和静态资源
        /*http.authorizeRequests()
                // 设置匹配的资源放行
                .antMatchers("/layui/**","/index.jsp").permitAll()
                // 剩余的资源必须认证
                .anyRequest().authenticated();*/

        http.authorizeRequests()
                .antMatchers("/layui/**", "/index.jsp").permitAll()
                .antMatchers("/level1/**").hasRole("学徒")
                .antMatchers("/level2/**").hasAnyRole("学徒","大师")
                .antMatchers("/level3/**").hasRole("宗师")
                .anyRequest().authenticated();
        // 默认自定义登录页
        // http.formLogin();
        // 自定义登录页面
        http.formLogin().loginPage("/index.jsp")
                // 默认表单字段为 'username'
                 .usernameParameter("username")
                // 默认为password
                 .passwordParameter("password")
                // 默认为login
                 .loginPage("/index.jsp")
                // 默认登录成功后URL
                .defaultSuccessUrl("/main.html");

        // 禁用csrf
        // http.csrf().disable();

        // 默认注销
        // http.logout();

        // 自定义注销
        http.logout()
                // 默认url为logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.jsp");

        // 自定义访问拒绝处理页面
        http.exceptionHandling().accessDeniedPage("/unAuth.html");

        // 记住我功能
        http.rememberMe();
    }
}
