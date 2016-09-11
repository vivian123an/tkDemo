package com.baturu.transaction.isolation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baturu.tkDemo.base.BaseServiceTest;

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
	@Test
	public void test1(){
		isolationService.mybatisFlushCache();
	}

	@Test
	public void test2(){
		isolationService.consistentRead();
	}

	@Test
	public void test3(){
		isolationService.readCommittedUpdate();
	}


	@Test
	public void test4(){
		isolationService.phantomRead();
	}
	

}
