package com.gk.cbl.config;

import com.gk.cbl.realm.MyShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
       // System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        /*
        *    /login=anon
                /op/admin=anon
                /op/putong=anon
                /user/goManMes=authc
                /org_index_ol/goUserMes=authc
                /shiro/success=authcBasic
                /word/comment=authc
        * */
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/op/admin", "anon");
        filterChainDefinitionMap.put("/op/putong", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/user/goManMes", "authc");
        filterChainDefinitionMap.put("/org_index_ol/goUserMes", "authc");
        filterChainDefinitionMap.put("/shiro/success", "authcBasic");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/word/comment", "authc");
        //filterChainDefinitionMap.put("/**", "anon");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/org_index_ol/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/jsp/success.jsp");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/jsp/unauthorized.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
}
