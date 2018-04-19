package com.jsut.ecoder.converter;

import com.jsut.ecoder.controller.EcoderCommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class StringToDateConverter implements Converter<String,Date>{
    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Nullable
    @Override
    public Date convert(String s) {
        Date date = null;
        if(s == null)return date;
        else{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sf.parse(s);
                logger.info("日期转换完成");
            } catch (ParseException e) {
                logger.info("Date Converter Error");
            }
        }
        return date;
    }
}
