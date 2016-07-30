package com.baturu.tkDemo.mapper;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.util.BaseServiceTest;

public class PtInbOrderMapperTest extends BaseServiceTest{
	@Autowired
	private PtInbOrderMapper ptInbOrderMapper;
	
	
	public void get(){
	    //根据主键查询
	    Assert.assertNotNull(ptInbOrderMapper.selectByPrimaryKey(1));
	}
	
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
	 * 多条件查询
	 */
	@Test
	public void find(){
		Example example = new Example(PtInbOrder.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo", "O100001");
        criteria.andLike("poNo", "%10001%");
         
        Criteria Criteria2 = example.or();
        Criteria2.andEqualTo("poNo", "PO2016061600011");
       
        System.out.println(JSON.toJSONString(ptInbOrderMapper.selectByExample(example),true));
	}
	
}
