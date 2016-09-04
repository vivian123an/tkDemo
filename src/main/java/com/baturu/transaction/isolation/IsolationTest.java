package com.baturu.transaction.isolation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baturu.tkDemo.util.BaseServiceTest;

public class IsolationTest extends BaseServiceTest{

	@Autowired
	private IsolationService isolationService;
	
	
	public void test1(){
		isolationService.notRepeatableRead1();
	}
	
	
	public void test2(){
		isolationService.notRepeatableRead2();
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
}
