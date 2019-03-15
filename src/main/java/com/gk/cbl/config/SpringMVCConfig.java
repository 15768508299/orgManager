package com.gk.cbl.config;

import com.gk.cbl.util.FileNullException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.HashSet;
import java.util.Set;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set set = new HashSet();
        set.add( new FileNullException());
        conversionServiceFactoryBean.setConverters(set);
        return conversionServiceFactoryBean;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(-1);
        return resolver;
    }

/*    @Bean(name = "beanNameUrlHandlerMapping")
    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping(){
        return new BeanNameUrlHandlerMapping();
    }

    @Bean(name = "simpleControllerHandlerAdapter")
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter(){
        return new SimpleControllerHandlerAdapter();
    }*/
}
