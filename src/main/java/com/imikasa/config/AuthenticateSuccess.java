package com.imikasa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthenticateSuccess implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功");
        response.setContentType("application/json;charset=utf-8");
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","登录成功");
        map.put("data",authentication);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writer().writeValueAsString(map);
        PrintWriter writer = response.getWriter();
        writer.write(s);
        writer.flush();
        writer.close();
    }
}
