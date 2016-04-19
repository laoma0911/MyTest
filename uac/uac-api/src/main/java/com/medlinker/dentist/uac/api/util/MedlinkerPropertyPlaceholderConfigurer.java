package com.medlinker.dentist.uac.api.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
/**
 * 查找资源并替换占位符
 * Created by mazb on 2016/4/6.
 */
public class MedlinkerPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private Log log = LogFactory.getLog(MedlinkerPropertyPlaceholderConfigurer.class);

    public void setLocations(Resource[] locations) {
        if (locations != null) {
            String userDir = System.getProperty("user.dir");
            for (int i = 0; i < locations.length; i++) {
                try {
                    Resource location = locations[i];
                    String fileName = location.getFile().getPath();
                    String fileNameNoPath = location.getFile().getName();
                    if (fileName.startsWith("${user_dir}")) {
                        log.info("开始重新定位资源" + location);
                        try {
                            location = new UrlResource("file://" + userDir
                                    + fileName.substring("${user_dir}".length(), fileName.length()));
                            locations[i] = location;
                            if (!location.getFile().exists()) {
                                throw new IOException("没有找到文件" + fileNameNoPath);
                            }
                            log.info("重新定位资源" + location + "成功");
                        } catch (Exception e) {
                            log.info("重新定位资源" + location + "失败:" + e.getMessage());
                            log.info("开始重新定位资源" + location + "尝试从classpath中加载");
                            location = new ClassPathResource(fileNameNoPath);
                            locations[i] = location;
                            log.info("开始重新定位资源" + location + "尝试从classpath中加载成功");
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            super.setLocations(locations);
        }
    }
}
