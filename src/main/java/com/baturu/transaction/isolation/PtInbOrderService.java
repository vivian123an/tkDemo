package com.baturu.transaction.isolation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;
import com.baturu.tkDemo.mapper.PtInbOrderMapper;

@Service
public class PtInbOrderService {

	@Autowired
	private PtInbOrderMapper inbOrderMapper;
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public int saveTxNew(PtInbOrder entity){
		return inbOrderMapper.save(entity);
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public int updateTxNew(PtInbOrder entity){
		return inbOrderMapper.update(entity);
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<PtInbOrder> findTxNew(String orderNo){
		return inbOrderMapper.findByOrderNo(orderNo);
	}
}
