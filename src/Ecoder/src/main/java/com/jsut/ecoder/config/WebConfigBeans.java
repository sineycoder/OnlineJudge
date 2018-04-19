package com.jsut.ecoder.config;

import com.jsut.ecoder.controller.EcoderCommonController;
import com.jsut.ecoder.converter.StringToDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfigBeans {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @PostConstruct
    public void initEditableValidation(){
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService() != null){
            GenericConversionService service = (GenericConversionService)initializer.getConversionService();
            service.addConverter(new StringToDateConverter());
        }
        logger.info(" [*** loading date binder ***] <- operate method[initEditableValidation] <- class["+this.getClass().getName()+"] ");
    }
}
