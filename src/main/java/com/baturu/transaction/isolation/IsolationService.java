package com.baturu.transaction.isolation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderMapper;
import com.baturu.tkDemo.util.BaseServiceTest;

/**
 * Repeatable Read Isolation Level in InnoDB - How Consistent Read View Works
 * https://blogs.oracle.com/mysqlinnodb/entry/repeatable_read_isolation_level_in
 * 
 * 
 * Innodb中的事务隔离级别和锁的关系
 * http://tech.meituan.com/innodb-lock.html
 * 
 * 15.5 InnoDB Locking and Transaction Model
 * https://dev.mysql.com/doc/refman/5.7/en/innodb-locking-transaction-model.html
 * 
 * @author pingan
 * @since  2016.09.03
 */


@Service
public class IsolationService extends BaseServiceTest{

	@Autowired
	private PtInbOrderMapper inbOrderMapper;
	@Autowired
	private PtInbOrderService inbOrderService;
	@Autowired
	private PtInbOrderJdbcService inbOrderJdbcService;
	

	  
	/** 
	 * REPEATABLE READ is the default isolation level for InnoDB.
	 * http://dev.mysql.com/doc/refman/5.7/en/innodb-transaction-isolation-levels.html
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void notRepeatableRead1(){
		PtInbOrder entity = inbOrderMapper.get(1);
		System.out.println(JSON.toJSONString(entity, true));
		
		PtInbOrder updateEntity = new PtInbOrder();
		updateEntity.setId(1l);
		updateEntity.setStatus(6000);
		inbOrderService.updateTxNew(updateEntity);
		
		entity = inbOrderMapper.get(1);
		System.out.println(JSON.toJSONString(entity, true));
	}
	
	/**
	 * 跟mybatis设置有关系吗？ 
	 * 1.useCache="true" flushCache="false"
	 * 2.useCache="false" flushCache="false"
	 * 3.useCache="false" flushCache="true"
	 * 
	 * 用jdbcTemplate实现
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void notRepeatableRead2(){
		PtInbOrder entity = inbOrderJdbcService.get(1l);
		System.out.println(JSON.toJSONString(entity, true));
		
		PtInbOrder updateEntity = new PtInbOrder();
		updateEntity.setId(1l);
		updateEntity.setStatus(222);
		inbOrderJdbcService.updateTxNew(updateEntity);
		
		entity = inbOrderJdbcService.get(1l);
		System.out.println(JSON.toJSONString(entity, true));
	}
	
	/**
	 * why there is no phantomRead
	 * 
	 * 跟myabtis有关系？mybatis【 flushCache = true】是否开启新事务？
	 * @Options(useCache = false, flushCache = false, timeout = 10000)
	 * 
	 * Default: true for insert, update and delete statements.
	 * Setting this to true will cause the 2nd level and local caches to be flushed whenever this statement is called. 
	 * Default: false for select statements.
	 * http://www.mybatis.org/mybatis-3/sqlmap-xml.html
	 * 
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void phantomRead1(){
		String orderNo = "O1001";
		List<PtInbOrder> entities = inbOrderMapper.findByOrderNo(orderNo);
		System.out.println(JSON.toJSONString(entities, true));
		
		PtInbOrder entity = new PtInbOrder();
		entity.setOrderNo(orderNo);
		inbOrderService.saveTxNew(entity);
		//inbOrderMapper.save(entity);
		
		entities = inbOrderMapper.findByOrderNo(orderNo);
		//entities = inbOrderService.findTxNew(orderNo);
		System.out.println(JSON.toJSONString(entities, true));
	}
	
	/**
	 * innoDb【REPEATABLE-READ】隔离级别是否解决幻读问题？
	 * 
	 * 15.5.2.3 Consistent Nonlocking Reads
	 * If the transaction isolation level is REPEATABLE READ (the default level), 
	 * all consistent reads within the same transaction read the snapshot established by the first such read in that transaction. 
	 * You can get a fresher snapshot for your queries by committing the current transaction and after that issuing new queries.
	 * 
	 * With READ COMMITTED isolation level, each consistent read within a transaction sets and reads its own fresh snapshot.
	 * 
	 * https://dev.mysql.com/doc/refman/5.7/en/innodb-consistent-read.html
	 * 
	 * To prevent phantoms, InnoDB uses an algorithm called next-key locking
	 * https://dev.mysql.com/doc/refman/5.7/en/innodb-next-key-locking.html
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void phantomRead2(){
		String orderNo = "O1001";
		List<PtInbOrder> entities = inbOrderJdbcService.findByOrderNo(orderNo);
		System.out.println(JSON.toJSONString(entities, true));
		
		PtInbOrder entity = new PtInbOrder();
		entity.setOrderNo(orderNo);
		inbOrderJdbcService.saveTxNew(entity);
		
		entities = inbOrderJdbcService.findByOrderNo(orderNo);
		//entities = inbOrderJdbcService.queryTxNew(orderNo);
		System.out.println(JSON.toJSONString(entities, true));
	}
}
