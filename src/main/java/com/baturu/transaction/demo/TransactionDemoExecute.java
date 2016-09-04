package com.baturu.transaction.demo;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderTkMapper;

/**
 * 
 * 分别执行execute1、execute2、execute3、execute4、execute5
 * 
 */


@Service("transactionDemoExecute")
public class TransactionDemoExecute {

	@Autowired
	private TransactionDemoInterface transactionDemo;
	@Autowired
	private TransactionDemoClass transactionDemoClass;
	@Autowired
	private TransactionPropagation transactionPropagation;
	@Autowired
	private PtInbOrderTkMapper ptInbOrderMapper;
	
	/**
	 * execute1方法没有开启事务注解
	 * 
	 * Spring AOP使用JDK动态代理或者CGLIB来为目标对象创建代理
	 * spring 官方说明：
	 * http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/aop.html#aop-proxying
	 * 
	 * transaction1 使用JDK实现
	 * transaction2、transaction3、transaction6使用CgLib实现
	 * 
	 * 注意方法的可见性
	 * http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations
	 * Method visibility and @Transactional When using proxies, you should apply the @Transactional annotation only to methods with public visibility. 
	 * If you do annotate protected, private or package-visible methods with the @Transactional annotation, no error is raised, 
	 * but the annotated method does not exhibit the configured transactional settings. 
	 * Consider the use of AspectJ (see below) if you need to annotate non-public methods.
	 */
	public void execute1(){
		
		try {
			transactionDemo.transaction1();
		} catch (Exception e) {
			//do nothing
		}
		/*try {
			transactionDemoClass.transaction2();
		} catch (Exception e) {
			//do nothing
		}
		try {
			transactionDemoClass.transaction3();
		} catch (Exception e) {
			//do nothing
		}
		try {
			transactionDemoClass.transaction6();
		} catch (Exception e) {
			//do nothing
		}*/
	}
    
    
    /**
     * execute2方法没有开启事务注解
     * 调用自身的方法transaction4、transaction5不能打开事务
     * 
     * debug日志：
     * org.mybatis.spring.SqlSessionUtils.getSqlSession(140) 
     * SqlSession was not registered for synchronization because synchronization is not active
     * 
     * 
     * 目标对象内部的自我调用将无法实施切面中的增强
     * 参考资料：
     * @link http://www.iteye.com/topic/1122740
     * 
     * spring 官方说明：
     * @link http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations
     * 
     * In proxy mode (which is the default), only external method calls coming in through the proxy are intercepted. 
     * This means that self-invocation, in effect, a method within the target object calling another method of the target object, 
     * will not lead to an actual transaction at runtime even if the invoked method is marked with @Transactional.     * 
     * 
     */
    public void execute2(){
		try {
			transaction4();
		} catch (Exception e) {
			//do nothing
		}
		try {
			transaction5();
		} catch (Exception e) {
			//do nothing
		}
		throw new RuntimeException();
    }
    
    /**
     * execute3方法开启了事务注解
     * transaction4、transaction5 跟execute3在同一个事务中
     * 
     * 查看debug日志
     * org.mybatis.spring.SqlSessionUtils.getSqlSession(120) Registering transaction synchronization for SqlSession
     * org.springframework.transaction.support.AbstractPlatformTransactionManager.processRollback(851) Initiating transaction rollback
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void execute3(){
    	try {
    		transaction4();
    	} catch (Exception e) {
    		//do nothing
    	}
    	try {
    		transaction5();
    	} catch (Exception e) {
    		//do nothing
    	}
    	//throw new RuntimeException();
    }
    
	/**
	 * 调用  private 方法
	 * 
	 */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class })
    private void transaction4(){
    	PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction4");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception4");
    }
    /**
     * 调用  public 方法
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class })
    public void transaction5(){
    	PtInbOrder entity = new PtInbOrder();
    	entity.setOrderNo("transaction5");
    	ptInbOrderMapper.insertSelective(entity);
    	throw new RuntimeException("Exception5");
    }
    
    /**
     * execute4没有开启事务注解
     * transaction4、transaction5开启了事务
     * 
     */
    public void execute4(){
    	try {
    		((TransactionDemoExecute) AopContext.currentProxy()).transaction4();
    	} catch (Exception e) {
    		// TODO: handle exception
    	}
    	try {
    		((TransactionDemoExecute) AopContext.currentProxy()).transaction5();
    	} catch (Exception e) {
    		// TODO: handle exception
    	}
    }
    
    
    /**
     * execute5方法开启了事务注解
     * transactionRequired2、transactionRequired、execute5三个方法在同一个事务中
     * 
     * 查看debug日志:
     * 
     * 一次开启事务，一次回滚
     * org.mybatis.spring.SqlSessionUtils.getSqlSession(120) Registering transaction synchronization for SqlSession
     * org.springframework.transaction.support.AbstractPlatformTransactionManager.handleExistingTransaction(476) Participating in existing transaction
     * org.springframework.transaction.support.AbstractPlatformTransactionManager.processRollback(858) Participating transaction failed - marking existing transaction as rollback-only
     * org.springframework.transaction.support.AbstractPlatformTransactionManager.processRollback(851) Initiating transaction rollback
     * 
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void execute5(){
		transactionPropagation.transactionRequired();
		
		try {
			transactionPropagation.transactionRequired2();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    
    /**
     * 
     * execute6方法开启了事务注解
     * execute6、transactionRequired在同一个事务中、transactionRequiredNew开启新的事务
     * 
     * 查看debug日志:
     * 
     * 两次开启事务
     * Creating a new SqlSession 
     * Registering transaction synchronization for SqlSession
     * Creating a new SqlSession 
     * Registering transaction synchronization for SqlSession
     * 
     * 一次回滚、一次提交
     * Initiating transaction rollback 
     * Initiating transaction commit
     */
    @Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void execute6(){
		transactionPropagation.transactionRequired();
		try {
			transactionPropagation.transactionRequiredNew();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    

}
