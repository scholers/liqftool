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
     * ��Ҫ���������ԣ�Ŀǰ��Ҫ��module,page�����ŷ��Ĭ��ȫ������
     */
    private String features;

    /**
     * ��Ƕ�����Ķ�������
     */
    private String extraConfigFile;

    /**
     * �������ԣ���ҪΪ�� module,page�����ŷ��Ĭ��ȫ������
     *
     * @param features features
     */
    public void setFeatures(String features) {
        this.features = features;
    }

    /**
     * ������Ƕ�����Ķ��������ļ�����classpath:xxxx.xml����xml�����ûḲ������ԭ�ȵ�����
     *
     * @param extraConfigFile �����Ķ��������ļ�
     */
    public void setExtraConfigFile(String extraConfigFile) {
        this.extraConfigFile = extraConfigFile;
    }

    /**
     * ������Ŀ��Spring����
     *
     * @param applicationContext project spring applicationContext
     * @throws BeansException bean exception
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.parentApplicationContext = applicationContext;
    }

    /**
     * ������صĳ�ʼ��
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
     * ��ȡ������bean
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
