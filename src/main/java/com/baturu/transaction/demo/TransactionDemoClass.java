package com.baturu.transaction.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderTkMapper;

@Service("transactionDemoClass")
public class TransactionDemoClass {
	
	@Autowired
	private PtInbOrderTkMapper ptInbOrderMapper;
	
	
    /**
     * 没有继承接口方法
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void transaction2(){
    	PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction2");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception");
    }
	
    /**
     * 没有继承接口方法 protected
     * 
     * protected不能打开事务的问题说明：
     * 
     * @link http://stackoverflow.com/questions/4396284/does-spring-transactional-attribute-work-on-a-private-method
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
    protected void transaction3(){
    	PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction3");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception3");
    }
    
    /**
     * 没有继承接口方法 friendly /default
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
    void transaction6(){
    	PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction6");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception");
    }
    
}
