package cn.jsut.coder.config;

import cn.jsut.coder.fileupload.CustomMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author:
 * @date:2018/1/20 15:57
 * @version:
 * @copyright:
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartResolver multipartResolver(){
        CustomMultipartResolver customMultipartResolver = new CustomMultipartResolver();
        customMultipartResolver.setMaxUploadSize(1024*1024*100);//100MB
        return customMultipartResolver;
    }
}
