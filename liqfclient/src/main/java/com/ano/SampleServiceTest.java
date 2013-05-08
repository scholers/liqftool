package com.ano;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cache.TestServiceImpl;

/**
 * 中文
 * @author weique.lqf
 *
 */
public class SampleServiceTest{
	@Resource
	private SampleServiceImpl sampleService = new SampleServiceImpl(new String());
	@Test
	public void testCacheTest(){     
		
		
		List<String> list=this.sampleService.cacheTest();
		for(String str:list){
			System.out.println(str);
		}
 
		List<String> cachelist=this.sampleService.cacheTest();
		for(String str:cachelist){
			System.out.println(str);
		}
 
		List<String> cachenewlist=this.sampleService.cacheFlushTest();
		for(String str:cachenewlist){
			System.out.println(str);
		}
 
		List<String> cachelist1=this.sampleService.cacheTest();
		for(String str:cachelist1){
			System.out.println(str);
		}		
	}
}
 
