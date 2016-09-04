package com.baturu.tkDemo.mapper;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.util.BaseServiceTest;
import com.google.common.collect.Lists;

public class PtInbOrderMapperTest extends BaseServiceTest{
	@Autowired
	private PtInbOrderTkMapper ptInbOrderMapper;
	
	/**
	 * 根据单个Id查询
	 */
	public void getById(){
	    Assert.assertNotNull(ptInbOrderMapper.selectByPrimaryKey(1));
	}
	
	
	/**
	 * 根据多个条件查询
	 */
	public void getByCondition(){
		Condition condition = new Condition(PtInbOrder.class);
		Criteria criteria = condition.createCriteria();
		criteria.andCondition("length(orderId)<6");//复杂语句,需要手写
		criteria.andIn("id",Lists.newArrayList(47631,47630));
		
		Criteria criteria2 = condition.or();
		criteria2.andLike("orderNo", "%O3344%");
		
		System.out.println(JSON.toJSONString(ptInbOrderMapper.selectByExample(condition), true));
	}
	
	
	/**
	 * 单个保存
	 */
	public void save(){
		PtInbOrder entity = new PtInbOrder();
		entity.setOrderNo("O100001");
		entity.setSupplierId(123456);
		entity.setOrderId(100001l);
	    //保存一个实体，null的属性不会保存，会使用数据库默认值
	    Assert.assertEquals(1, ptInbOrderMapper.insertSelective(entity));
	    //保存一个实体，null的属性也会保存，不会使用数据库默认值
	    Assert.assertEquals(1, ptInbOrderMapper.insert(entity));
	}
	
	/**
	 * 批量保存
	 */
	public void saveBatch(){
		PtInbOrder entity1 = new PtInbOrder();
		entity1.setOrderNo("O100002");
		entity1.setSupplierId(1234567);
		entity1.setOrderId(100002l);
		
		PtInbOrder entity2 = new PtInbOrder();
		entity2.setOrderNo("O100003");
		entity2.setSupplierId(12345678);
		entity2.setOrderId(100003l);
		
		Assert.assertEquals(2, ptInbOrderMapper.insertList(Lists.newArrayList(entity1,entity2)));
	}
	
	
	/**
	 * 根据主键Id更新
	 */
	public void update(){
		//根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
		PtInbOrder entity = ptInbOrderMapper.selectByPrimaryKey(47631);
		entity.setSplyOrderId(99999l);
		entity.setPoNo("PO10001");
		entity.setPoType(200);
		entity.setCreateBy("cwa");
		entity.setCreateTime(new Date());
		entity.setPurchaseTime(new Date());
		entity.setStatus(300);
		//根据主键更新属性不为null的值
		ptInbOrderMapper.updateByPrimaryKeySelective(entity);
	}
	
	
	/**
	 * 根据多个条件更新
	 */
	public void updateByCondition(){
		Condition condition = new Condition(PtInbOrder.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("orderNo", "O100001");
        criteria.andLike("poNo", "%10001%");
         
        Criteria Criteria2 = condition.or();
        Criteria2.andEqualTo("poNo", "PO2016061600011");
       
        System.out.println(JSON.toJSONString(ptInbOrderMapper.selectByExample(condition),true));
	}
	
	@Test
	public void joinTable(){
		System.out.println(JSON.toJSONString(ptInbOrderMapper.findBySupplierName("阡陌"),true));
	}
	
	
	
}
