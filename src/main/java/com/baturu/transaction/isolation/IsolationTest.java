package com.baturu.transaction.isolation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baturu.tkDemo.util.BaseServiceTest;

/**
 * Repeatable Read Isolation Level in InnoDB - How Consistent Read View Works
 * https://blogs.oracle.com/mysqlinnodb/entry/repeatable_read_isolation_level_in
 * 
 * MySQL 四种事务隔离级的说明
 * http://www.cnblogs.com/zhoujinyi/p/3437475.html
 * 
 * MySQL 加锁处理分析
 * http://hedengcheng.com/?p=771
 * 
 * 
 * Baron Schwartz's Blog
 * http://www.xaprb.com/
 * 
 * 
 * @author pingan
 *
 */


public class IsolationTest extends BaseServiceTest{

	@Autowired
	private IsolationService isolationService;
	

	/**
	 * 设置数据库隔离级别、读提交
	 * 【SET GLOBAL tx_isolation = 'read-committed';】
	 */
	public void test1(){
		isolationService.nonRepeatableRead();
	}
	
	/**
	 * 使用下面两种参数，分别执行该方法
	 * 1.【SET GLOBAL tx_isolation = 'REPEATABLE-READ';】
	 * 2.【SET GLOBAL tx_isolation = 'read-committed';】
	 */
	public void test2(){
		isolationService.readCommittedUpdate();
	}
	
	/**
	 * 使用下面两种参数，分别执行该方法
	 * 1.【SET GLOBAL tx_isolation = 'REPEATABLE-READ';】
	 * 2.【SET GLOBAL tx_isolation = 'read-committed';】
	 */
	public void test3(){
		isolationService.phantomRead1();
	}
	
	@Test
	public void test4(){
		isolationService.phantomRead2();
	}
	
	/**
	 * 设置数据库隔离级别、读提交
	 * 【SET GLOBAL tx_isolation = 'read-committed';】
	 */
	public void test5(){
		isolationService.nonRepeatableRead();
	}
}
