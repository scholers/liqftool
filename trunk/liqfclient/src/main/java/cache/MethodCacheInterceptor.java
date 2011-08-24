package cache;

import java.io.Serializable;   

import net.sf.ehcache.Cache;   
import net.sf.ehcache.Element;   
  
import org.aopalliance.intercept.MethodInterceptor;   
import org.aopalliance.intercept.MethodInvocation;   
import org.apache.commons.logging.Log;   
import org.apache.commons.logging.LogFactory;   
import org.springframework.beans.factory.InitializingBean;   
import org.springframework.util.Assert;   
  
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean   
{   
    private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);   
  
    private Cache cache;   
  
    public void setCache(Cache cache) {   
        this.cache = cache;   
    }   
  
    public MethodCacheInterceptor() {   
        super();   
    }   
  
    /**  
     * ����Service/DAO�ķ����������Ҹý���Ƿ���ڣ�������ھͷ���cache�е�ֵ��  
     * ���򣬷������ݿ��ѯ�����������ѯ�������cache  
     */  
    public Object invoke(MethodInvocation invocation) throws Throwable {   
        String targetName = invocation.getThis().getClass().getName();   
        String methodName = invocation.getMethod().getName();   
        Object[] arguments = invocation.getArguments();   
        Object result;   
       
        logger.debug("Find object from cache is " + cache.getName());   
           
        String cacheKey = getCacheKey(targetName, methodName, arguments);   
        Element element = cache.get(cacheKey);   
  
        if (element == null) {   
            logger.debug("Hold up method , Get method result and create cache........!");   
            result = invocation.proceed();   
            element = new Element(cacheKey, (Serializable) result);   
            cache.put(element);   
        }   
        return element.getValue();   
    }   
  
    /**  
     * ���cache key�ķ�����cache key��Cache��һ��Element��Ψһ��ʶ  
     * cache key���� ����+����+����������com.co.cache.service.UserServiceImpl.getAllUser  
     */  
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {   
        StringBuffer sb = new StringBuffer();   
        sb.append(targetName).append(".").append(methodName);   
        if ((arguments != null) && (arguments.length != 0)) {   
            for (int i = 0; i < arguments.length; i++) {   
                sb.append(".").append(arguments[i]);   
            }   
        }   
        System.out.println(sb.toString());
        return sb.toString();   
    }   
       
    /**  
     * implement InitializingBean�����cache�Ƿ�Ϊ��  
     */  
    public void afterPropertiesSet() throws Exception {   
        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");   
    }   
  
}  
