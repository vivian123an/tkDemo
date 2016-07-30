package com.baturu.tkDemo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.baturu.tkDemo.entity.PtInbOrder;


@Repository
public interface PtInbOrderMapper extends Mapper<PtInbOrder>{

	/**
	 * 分页查询物流采购单
	 * @param params
	 * @return
	 */
	List<PtInbOrder> getInbOrderListPageAuto(Map<String,Object> params);
	
	/**
	 * 统计总记录数目
	 * 
	 * <p> 只支持单表（pt_inb_order）条件查询
	 * 
	 * @param params
	 * @return
	 */
	int countInbOrderTotal(Map<String,Object> params);
	
	/**
	 * 根据主键批量查询
	 * @param ids
	 * @return
	 */
	List<PtInbOrder> findByIds(@Param("ids")List<Long> ids);
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 */
	List<PtInbOrder> findByOrderNos(@Param("orderNos") List<String> orderNos);
}