package ano;

import java.util.ArrayList;
import java.util.List;

//import org.springmodules.cache.annotations.Cacheable;
//import org.springmodules.cache.annotations.CacheFlush;

public class SampleServiceImpl {
	private SampleServiceImpl (){
		
	}
	
	public SampleServiceImpl (Object obj){
		
	}
	//@Cacheable(modelId="testCache")
	public List cacheTest(){
		List list=new ArrayList();
		list.add("a");
		return list;
	}
 
	//@CacheFlush(modelId ="testFlush")
	public List cacheFlushTest(){
		List list=new ArrayList();
		list.add("b");
		return list;
	}	
}
 


