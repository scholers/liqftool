package cache;

import java.util.List;

public class TestServiceImpl2  implements TestService{
	    public List getAllObject() {   
	        System.out.println("---TestService��Cache�ڲ����ڸ�element�����Ҳ�����Cache��");   
	        return null;   
	    }   
	  
	    public void updateObject(Object Object) {   
	        System.out.println("---TestService�������˶������Class������cache������remove��");   
	    }  
	    
	    public void updateObject2(Object Object) {   
	        System.out.println("---TestService�������˶������Class������cache������remove��");   
	    }   
	}  