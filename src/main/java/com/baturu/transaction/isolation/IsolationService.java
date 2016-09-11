package com.baturu.transaction.isolation;

import com.alibaba.fastjson.JSON;
import com.baturu.tkDemo.base.BaseServiceTest;
import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderMapper;
import com.baturu.tkDemo.mapper.PtProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Repeatable Read Isolation Level in InnoDB - How Consistent Read View Works
 * https://blogs.oracle.com/mysqlinnodb/entry/repeatable_read_isolation_level_in
 * 
 * REPEATABLE READ is the default isolation level for InnoDB.
 * http://dev.mysql.com/doc/refman/5.7/en/innodb-transaction-isolation-levels.html
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
	private PtInbOrderMapper inbOrderDao;
	@Autowired
	private PtProvinceMapper provinceDao;
	@Autowired
	private PtInbOrderService inbOrderService;
	@Autowired
	private PtInbOrderJdbcService inbOrderJdbcDao;
	
	/**
     *
     * 问题:在一个事务中,使用mybatis查询数据,然后其他事务提交了数据更新（update）,第一个事务查询不到更新
     * 跟【read-committed】【repeatable read】没有关系
     *
	 * 跟mybatis设置有关系吗？ 
	 * 1.useCache="true" flushCache="false"
	 * 2.useCache="false" flushCache="false"
	 * 3.useCache="false" flushCache="true"
	 *
	 * Default: true for insert, update and delete statements.
	 * Setting this to true will cause the 2nd level and local caches to be flushed whenever this statement is called.
	 * Default: false for select statements.
	 * http://www.mybatis.org/mybatis-3/sqlmap-xml.html
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void mybatisFlushCache(){
		System.out.println(JSON.toJSONString(inbOrderDao.get(1), true));
		
		PtInbOrder updateEntity = new PtInbOrder();
		updateEntity.setId(1l);
		updateEntity.setStatus(404);
		inbOrderService.updateTxNew(updateEntity);
		
		System.out.println(JSON.toJSONString(inbOrderDao.get(1), true));
	}

	/**
	 * 关注最开始是否执行了查询操作 provinceMapper.get(1l)
	 *
	 * https://blogs.oracle.com/mysqlinnodb/entry/repeatable_read_isolation_level_in
	 *
	 * If the traditional locking approach is taken then the output would be the new value.
	 * But in InnoDB the output of query “select f1 from t2” in line 6 would be the old value.
	 *
	 * InnoDB creates a consistent read view or a consistent snapshot either when the statement
	 * is executed or when the first select query is executed in the transaction.
	 *
	 *
	 * https://dev.mysql.com/doc/refman/5.7/en/innodb-consistent-read.html
	 *
	 *【SET GLOBAL tx_isolation = 'REPEATABLE-READ';】
	 * 15.5.2.3 Consistent Nonlocking Reads
	 * If the transaction isolation level is REPEATABLE READ (the default level), all consistent reads within the same transaction
	 * read the snapshot established by the first such read in that transaction.
	 * You can get a fresher snapshot for your queries by committing the current transaction and after that issuing new queries.
	 *
	 * 【SET GLOBAL tx_isolation = 'read-committed';】
	 * With READ COMMITTED isolation level, each consistent read within a transaction sets and reads its own fresh snapshot.
	 *
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void consistentRead(){
		//System.out.println(JSON.toJSONString(provinceDao.get(1l),true));
		System.out.println(JSON.toJSONString(inbOrderJdbcDao.get(1l), true));

		PtInbOrder updateEntity = new PtInbOrder();
		updateEntity.setId(1l);
		updateEntity.setStatus(404);
		inbOrderService.updateTxNew(updateEntity);

		System.out.println(JSON.toJSONString(inbOrderJdbcDao.get(1l), true));
	}


	/**
	 * consistentRead      在最外层事务只做select读操作
	 * readCommittedUpdate 在最外层事务做update后再select
	 *
	 * The snapshot of the database state applies to SELECT statements within a transaction, not necessarily to DML statements.
	 * https://dev.mysql.com/doc/refman/5.7/en/innodb-consistent-read.html
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void readCommittedUpdate(){
		System.out.println(JSON.toJSONString(inbOrderJdbcDao.get(1l), true));

		inbOrderJdbcDao.increaseStatusTxNew(1l);
		inbOrderJdbcDao.increaseStatus(1l);
		
		System.out.println(JSON.toJSONString(inbOrderJdbcDao.get(1l), true));
	}
	
	/**
     *  mybatis使用默认值【flushCache = false】
     *  问题:在一个事务中,使用mybatis根据条件查询数据,然后其他事务提交insert,第一个事务查询不到新数据
     *  innoDb【read-committed】隔离级别解决了幻读问题?
     *  巴图鲁main数据库事务隔离级别的设置是【read-committed】
     *
     *  在网络上搜索,隔离级别的说明,大部分如下:
     *  ISOLATION_READ_UNCOMMITTED： 这是事务最低的隔离级别，它充许令外一个事务可以看到这个事务未提交的数据,这种隔离级别会产生脏读，不可重复读和幻像读。
     *  ISOLATION_READ_COMMITTED： 保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据
     *  ISOLATION_REPEATABLE_READ： 这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免不可重复读
     *  ISOLATION_SERIALIZABLE 这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。除了防止脏读，不可重复读外，还避免了幻像读。
     *
     *
     * innoDb【read-uncommited】隔离级别存在【dirty read】【non-repeatable read】【phantom read】问题
	 * innoDb【read-committed】 隔离级别存在【non-repeatable read】【phantom read】问题
	 * innoDb【REPEATABLE-READ】隔离级别存在【phantom read】问题
     * innoDb【SERIALIZABLE】解决了这些问题
     *
     * 介绍一下MySQL/InnoDB是如何定义这4种隔离级别的
     * http://hedengcheng.com/?p=771
	 * 
	 * To prevent phantoms, InnoDB uses an algorithm called next-key locking
	 * https://dev.mysql.com/doc/refman/5.7/en/innodb-next-key-locking.html
	 *
	 * 如果需要读数据库的最新提交的数据
	 * 使用READ COMMITTED isolation level 或者 a locking read:
	 * SELECT * FROM table LOCK IN SHARE MODE;
	 */
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public void phantomRead(){
		String orderNo = "O1001";
		//System.out.println(JSON.toJSONString(inbOrderJdbcDao.findByOrderNo(orderNo), true));
        System.out.println(JSON.toJSONString(inbOrderDao.findByOrderNo(orderNo), true));


        PtInbOrder entity = new PtInbOrder();
		entity.setOrderNo(orderNo);
		inbOrderService.saveTxNew(entity);
		//inbOrderMapper.save(entity);
		
		System.out.println(JSON.toJSONString(inbOrderDao.findByOrderNo(orderNo), true));
		//System.out.println(JSON.toJSONString(inbOrderJdbcDao.queryTxNew(orderNo), true));
		//System.out.println(JSON.toJSONString(inbOrderJdbcDao.findByOrderNo(orderNo), true));
		//System.out.println(JSON.toJSONString(inbOrderJdbcDao.findByOrderNoForUpdate(orderNo), true));
		//System.out.println(JSON.toJSONString(inbOrderJdbcDao.findByOrderNoInShareMode(orderNo), true));
	}
	
}
