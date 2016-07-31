package com.baturu.transaction.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.baturu.tkDemo.util.BaseServiceTest;

/**
 * 
 * @author chenwenan
 * @since  2016.07.22
 * 
 * 分别执行 test1、test2、test3、test4、test5、test6
 * 查看main数据库、pt_inb_order表的新增记录
 * 
 * 
 * 发现的问题：
 * lostistics-service
 * 1.com.baturu.logistics.api.job.logistics-service LogisticsCheckReturnGoodsSync#buildSyncData
 * 
 * logistics-api
 * 2.com.batulu.mobile.app.service#createCode
 * 
 * http://stackoverflow.com/questions/4396284/does-spring-transactional-attribute-work-on-a-private-method
 *
 * This means that self-invocation 不能开启事务中文分析
 * http://www.iteye.com/topic/1122740
 * 
 * 
 * private方法 final、static、protected、friendly
 * http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations
 *
 * 1.需要使用事务的场景,多个表的状态需要保持一致，要么都修改，要么都不修改
 * wms-service项目
 * com.baturu.wms.biz.job.service.SysOrderPackageService
 *
 * negative
 * 2.事务太大,里面包含for循环update数据,如果有一条update执行失败，前面所有的update都回滚，另外会导致update一直不commit，只有最后一个for循环结束后，才会提交
 * logistics-api项目
 * com.batulu.mobile.thirdPartyMerge.service.OrderPackageBTLDeliveryService#batchDeliveryOrderPackage
 *
 *
 * 3.for循环分别开启事务的场景、当有一个订单数据错误，抛出exception，不希望影响到其他order数据的接收和save，一次update回滚，不影响前面save的回滚
 * wms-service项目
 * com.baturu.wms.biz.job.b2b.JobOfInfB2bSaleOrderS
 * com.baturu.wms.biz.job.service.InfB2bSaleOrderSService
 * 
 */

@Service("transactionDemoTest")
public class TransactionDemoTest extends BaseServiceTest{

	@Autowired
    private TransactionDemoExecute transactionDemoExecute;
	
	
    public void test1(){
    	transactionDemoExecute.execute1();
    }
    
    @Test
	public void test2(){
    	transactionDemoExecute.execute2();
	}
	
    public void test3(){
    	transactionDemoExecute.execute3();
    }
    
	public void test4(){
		transactionDemoExecute.execute4();
	}
	
	public void test5(){
		transactionDemoExecute.execute5();
	}
	
	public void test6(){
		transactionDemoExecute.execute6();
	}
	
}
