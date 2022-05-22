package com.imikasa.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecurityController {

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @GetMapping("/user-info")
    public Principal getUserInfo(Principal principal){
        return principal;
    }
    @GetMapping("/user-info1")
    public Object getUserInfo1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }



    /**
     * 登录成功的主页返回值
     *
     * @return
     */
    @PostMapping("welcome")
    public String welcome() {
        return "欢迎来到主页";
    }

    /**
     * 登录失败的返回值
     *
     * @return
     */
    @PostMapping("fail")
    public String fail() {
        return "登录失败了";
    }

    /**
     * 开启方法权限的注解
     *
     * @return
     */
    @GetMapping("save")
    public String add() {
        return "欢迎来到主ADD";
    }

    @GetMapping("update")
    public String update() {
        return "欢迎来到UPDATE";
    }

    @GetMapping("del")
    public String delete() {
        return "欢迎来到DELETE";
    }

    @GetMapping("query")
    public String select() {
        return "欢迎来到SELECT";
    }

    @GetMapping("role")
    public String role() {
        return "欢迎来到ROLE";
    }

    @GetMapping("admin/hello")
    public String admin() {
        return "我是只有 admin 角色才可以访问的";
    }

}
