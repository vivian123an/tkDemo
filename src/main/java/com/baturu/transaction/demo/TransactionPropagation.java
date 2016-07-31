package com.baturu.transaction.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderMapper;

@Service("transactionPropagation")
public class TransactionPropagation {
	@Autowired
	private PtInbOrderMapper ptInbOrderMapper;
	
	/**
	 * Propagation.REQUIRED
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void transactionRequired() {
		PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transactionRequired");
    	ptInbOrderMapper.insertSelective(entity);
	}
	/**
	 * Propagation.REQUIRED
	 * throw new RuntimeException("Exception");
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void transactionRequired2() {
		PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transactionRequired2");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception");
	}
	
	/**
	 * Propagation.REQUIRES_NEW
	 * throw new RuntimeException("Exception");
	 * 
	 * 如果外层没有事务,REQUIRED与REQUIRES_NEW效果是一致
	 * 如果外层有事务,REQUIRED与REQUIRES_NEW有区别
	 * 
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void transactionRequiredNew() {
		PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transactionRequiredNew");
    	ptInbOrderMapper.insertSelective(entity);
		throw new RuntimeException("Exception");
	}
}
