package com.baturu.transaction.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderTkMapper;

/**
 * 
 * @author Administrator
 *
 * spring事务的官方说明文档：
 * 
 * http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations
 *
 */

@Service("transactionDemo")
public class TransactionDemoImpl implements TransactionDemoInterface{

	@Autowired
	private PtInbOrderTkMapper ptInbOrderMapper;
	
	/**
	 * 调用TransactionDemo接口方法
	 * 
	 * 查看debug日志:
	 * 
	 * org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource.getTransactionAttribute(108) 
     * Adding transactional method 'TransactionImpl.springTransaction1' with attribute: PROPAGATION_REQUIRED,ISOLATION_DEFAULT;
     * org.springframework.aop.framework.JdkDynamicAopProxy.getProxy(117) Creating JDK dynamic proxy: 
     * target source is SingletonTargetSource for target object [com.baturu.wms.biz.util.test.TransactionDemoImpl@168cd36b]
     * Participating transaction failed - marking existing transaction as rollback-only
	 * 
	 */
	@Override
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void transaction1(){
		PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction1");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception1");
    }


	
}
