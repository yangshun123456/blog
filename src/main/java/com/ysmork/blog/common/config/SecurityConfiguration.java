package com.ysmork.blog.common.config;

import com.ysmork.blog.framework.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: Spring Security配置类
 * @date 2020/9/22 21:57
 */
@Configuration
@EnableWebSecurity //激活Spring Security
/**
 * 开启方法级别安全控制(在方法上添加@PreAuthorize ("hasAnyRole('admin')"))
 * prePostEnabled = true 会拦截@PreAuthorize的配置
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

//    /**
//     * 认证失败处理类
//     */
//    @Resource
//    private AuthenticationEntryPointImpl unauthorizedHandler;

//    /**
//     * 退出处理类
//     */
//    @Resource
//    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JWTAuthorizationFilter authenticationTokenFilter;
    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 基于内存的方式放入一个用户名密码
         */
        /*auth.inMemoryAuthentication ()
                .passwordEncoder (new BCryptPasswordEncoder ())
                .withUser ("admin")
                .password (new BCryptPasswordEncoder ().encode ("123456")).roles ();*/
        //或者  下方注入PasswordEncoder 的 Bean
        /*auth.inMemoryAuthentication ()
                .withUser ("admin")
                .password (passwordEncoder().encode ("123456"))
                .roles();*/

        auth.userDetailsService (userDetailsService).passwordEncoder (passwordEncoder ());
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 跨域配置开始
                .cors().disable()
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .and()
                // 跨域配置结束
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/user/login", "/user/createCaptcha","/minaPersonCenter/login").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/static/**",
                        "/"
                ).permitAll()
                .antMatchers(
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**"
                ).anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
//        http.logout().logoutUrl("/user/logout").logoutSuccessHandler(logoutSuccessHandler);//登出
        // 添加JWT filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }


    /**
     * 密码生成策略.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }
}
