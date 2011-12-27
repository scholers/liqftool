package com.taobao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EmbedSpringContainer implements ApplicationContextAware, InitializingBean {
    /**
     * embed application context
     */
    public static ApplicationContext applicationContext;
    /**
     * parent applicationContext
     */
    private ApplicationContext parentApplicationContext;



    /**
     * 所要包含的特性，目前主要是module,page，逗号风格，默认全部加载
     */
    private String features;

    /**
     * 内嵌容器的额外配置
     */
    private String extraConfigFile;

    /**
     * 设置特性，主要为： module,page，逗号风格，默认全部加载
     *
     * @param features features
     */
    public void setFeatures(String features) {
        this.features = features;
    }

    /**
     * 设置内嵌容器的额外配置文件，如classpath:xxxx.xml，该xml中配置会覆盖容器原先的配置
     *
     * @param extraConfigFile 容器的额外配置文件
     */
    public void setExtraConfigFile(String extraConfigFile) {
        this.extraConfigFile = extraConfigFile;
    }

    /**
     * 设置项目的Spring容器
     *
     * @param applicationContext project spring applicationContext
     * @throws BeansException bean exception
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.parentApplicationContext = applicationContext;
    }

    /**
     * 进行相关的初始化
     *
     * @throws Exception exception
     */
    public void afterPropertiesSet() throws Exception {
    	//LoggerInit.initLogFromBizLog();
        List<String> configurations = new ArrayList<String>();
       // log.info("afterPropertiesSet init!!!:::");
        configurations.add("classpath:client-cache.xml");
        configurations.add("classpath:applicationContext-sg-client.xml");
        configurations.add("classpath:applicationContext-sg-client-jmx.xml");
        configurations.add("classpath:applicationContext-sg-client-tair.xml");
        configurations.add("classpath:applicationContext-sg-client-hsf.xml");
        configurations.add("classpath:biz-forest.xml");
       // configurations.add("classpath:logback.xml");
        if (features == null) {
            features = "module,page";
        }
        if (features.contains("module")) {
            configurations.add("classpath:applicationContext-sg-client-modules.xml");
        }
        if (features.contains("page")) {
            configurations.add("classpath:applicationContext-sg-client-page.xml");
        }
       
        
        try {
        EmbedSpringContainer.applicationContext = new ClassPathXmlApplicationContext(
                configurations.toArray(new String[configurations.size()]), parentApplicationContext);
        } catch (Exception ex) {
        	ex.fillInStackTrace();
        	
        }
       
    }

    /**
     * 获取子容器bean
     *
     * @param beanName bean name
     * @return spring bean
     */
    public static Object getBean(String beanName) {
    	if(applicationContext == null) {
    		System.out.println("applicationContext is null");
    	}
        return applicationContext == null ? null : applicationContext.getBean(beanName);
    }
}
