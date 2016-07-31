package com.baturu.tkDemo.mapper;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import com.baturu.tkDemo.entity.PtInbOrder;


@Repository
public interface PtInbOrderMapper extends Mapper<PtInbOrder>,InsertListMapper<PtInbOrder>{

}