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
       System.out.println("---TestService：Cache内不存在该element，查找并放入Cache！"); 
    	//模拟数据库，得到数据
        List list=new ArrayList();
        SeaBean seaBeanTem = new SeaBean();
        seaBeanTem.setName(seaBean.getName());
		list.add(seaBeanTem);
		return list;
    }   
  
    public void updateObject(Object Object) {   
        //System.out.println("---TestService：更新了对象，这个Class产生的cache都将被remove！");   
    }  
    
    public void updateObject2(Object Object) {   
       // System.out.println("---TestService：更新了对象，这个Class产生的cache都将被remove！");   
    }

	public SeaBean getPaire() {
		return seaBean;
	}   
}  

