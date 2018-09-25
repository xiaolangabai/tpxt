package com.tpxt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties properties = null;

    public static Properties getProperties(){
        if(properties == null){
            properties = loadProperties("wx.properties");
        }
        return properties;
    }

    public static Properties loadProperties(String path){
        InputStream is = null;
        properties = new Properties();
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            properties.load(is);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在", e);
        } catch (IOException e) {
            //do nothing;
        }
        return properties;
    }
}
