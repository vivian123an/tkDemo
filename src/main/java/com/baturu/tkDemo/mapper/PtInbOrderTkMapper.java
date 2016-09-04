package com.baturu.tkDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import com.baturu.tkDemo.entity.PtInbOrder;


@Repository
public interface PtInbOrderTkMapper extends Mapper<PtInbOrder>,InsertListMapper<PtInbOrder>{

	@Select(" select o.* from pt_inb_order o left join qpu_org g on g.orgId = o.supplierId where g.orgName like concat('%', #{supplierName}, '%') ")
	List<PtInbOrder> findBySupplierName(@Param("supplierName")String supplierName);
	
}