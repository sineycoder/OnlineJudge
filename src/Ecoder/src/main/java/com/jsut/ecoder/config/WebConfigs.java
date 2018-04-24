package com.jsut.ecoder.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jsut.ecoder.interceptor.BackendInterceptor;
import com.jsut.ecoder.interceptor.CommonInterceptor;
import com.jsut.ecoder.interceptor.UserInterceptor;
import com.jsut.ecoder.interceptor.UserPrivateInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfigs implements WebMvcConfigurer {

    private final Logger logger =  LoggerFactory.getLogger(WebConfigs.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("PUT","GET","DELETE","POST")
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
        logger.info(" [*** loading Cors mapping ***] <- operate method[addCorsMappings] <- class["+this.getClass().getName()+"] ");

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
        logger.info(" [*** loading FastJson setting ***] <- operate method[configureMessageConverters] <- class["+this.getClass().getName()+"] ");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/api/common/**");
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/api/user/**");
        registry.addInterceptor(new BackendInterceptor()).addPathPatterns("/api/backend/**");
        registry.addInterceptor(new UserPrivateInterceptor()).addPathPatterns("/api/userPrivate/**");
        logger.info(" [*** loading Interceoptor ***] <- operate method[addInterceptors] <- class["+this.getClass().getName()+"] ");

    }

}
