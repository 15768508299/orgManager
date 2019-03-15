package com.gk.cbl.controller;

import com.gk.cbl.service.Org_userService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    Org_userService userService;

    @RequestMapping("/index")
    public String index(){
        return "admin/login";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public Map checkLogin(String username, String password, HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","fail");
        resultMap.put("message","用户名或密码错误");

        if (!username.equals("admin")){
            resultMap.put("status","fail");
            resultMap.put("message","您不是管理员，无法登录!");
            return resultMap;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
            resultMap.put("status","success");
            resultMap.put("message","");
        }catch(Exception e){

        }
        return resultMap;
    }

    @RequestMapping("/main")
    public String main(){
        return "admin/main";
    }
}
