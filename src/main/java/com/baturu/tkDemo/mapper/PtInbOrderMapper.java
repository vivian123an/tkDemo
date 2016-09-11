package com.baturu.tkDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baturu.tkDemo.base.BaseMapper;
import com.baturu.tkDemo.entity.PtInbOrder;


@Repository
public interface PtInbOrderMapper extends BaseMapper<PtInbOrder>{

	@Select(" select * from pt_inb_order where orderNo = #{orderNo} ")
	@Options(useCache = false, flushCache = false, timeout = 10000)
	List<PtInbOrder> findByOrderNo(@Param("orderNo")String orderNo);
}