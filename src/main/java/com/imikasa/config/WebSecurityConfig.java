package com.imikasa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ssa")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN_SXT")
                .authorities("sys:add","sys:update","sys:select","sys:delete")
                .and()
                .withUser("test")
                .password(passwordEncoder().encode("test"))
                .roles("TEST")
                .authorities("sys:select")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//        http.formLogin()
//                .successForwardUrl("/welcome")
//                .failureForwardUrl("/fail")
//                .permitAll();
        http.formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler());


        http.authorizeRequests()
                .antMatchers("/query").hasAnyAuthority("sys:select")
                .antMatchers("/save").hasAnyAuthority("sys:add")
                .antMatchers("/del").hasAnyAuthority("sys:delete")
                .antMatchers("/update").hasAnyAuthority("sys:update")
                .antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            System.out.println(exception);
            // ??????????????????????????????
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", 401);
            // instanceof ?????????????????????????????? ????????????  ?????????exception?????????????????????????????????
            if (exception instanceof LockedException) {
                map.put("msg", "?????????????????????????????????");
            } else if (exception instanceof BadCredentialsException) {
                map.put("msg", "??????????????????????????????????????????");
            } else if (exception instanceof DisabledException) {
                map.put("msg", "?????????????????????????????????");
            } else if (exception instanceof AccountExpiredException) {
                map.put("msg", "?????????????????????????????????");
            } else if (exception instanceof CredentialsExpiredException) {
                map.put("msg", "?????????????????????????????????");
            } else {
                map.put("msg", "???????????????");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

}
