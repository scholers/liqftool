package com.cache;

import java.util.ArrayList;
import java.util.List;

import com.liqf.other.Paire;

public class TestServiceImpl extends TestServiceBase  
{   
	private SeaBean seaBean = null;
	

	public void setSealBean(SeaBean paire){
		this.seaBean = paire;
	}
	
	public TestServiceImpl(SeaBean paire){
		super(paire);
	}
	
    public List getAllObject(SeaBean seaBean) {   
       System.out.println("---TestService��Cache�ڲ����ڸ�element�����Ҳ�����Cache��"); 
    	//ģ�����ݿ⣬�õ�����
        List list=new ArrayList();
        SeaBean seaBeanTem = new SeaBean();
        seaBeanTem.setName(seaBean.getName());
		list.add(seaBeanTem);
		return list;
    }   
  
    public void updateObject(Object Object) {   
        //System.out.println("---TestService�������˶������Class������cache������remove��");   
    }  
    
    public void updateObject2(Object Object) {   
       // System.out.println("---TestService�������˶������Class������cache������remove��");   
    }

	public SeaBean getPaire() {
		return seaBean;
	}   
}  

