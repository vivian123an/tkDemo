package com.baturu.transaction.isolation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baturu.tkDemo.entity.PtInbOrder;

/**
 * @author pingan
 * @since  2016.09.03
 */

@Service
public class PtInbOrderJdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public int saveTxNew(PtInbOrder entity){
		String sql = " insert into pt_inb_order (orderNo) values ( ? ) ";
		return jdbcTemplate.update(sql, new Object[]{entity.getOrderNo()});
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public int updateTxNew(PtInbOrder entity){
		String sql = " update pt_inb_order set status = ? where id = ?  ";
		return jdbcTemplate.update(sql, new Object[]{entity.getStatus(), entity.getId()});
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<PtInbOrder> queryTxNew(String orderNo){
		String sql = " select id, orderNo, status from pt_inb_order where orderNo = ?  ";
		return jdbcTemplate.query(sql, new Object[]{orderNo}, new PtInbOrderMapper());
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public List<PtInbOrder> findByOrderNo(String orderNo){
		String sql = " select id, orderNo, status from pt_inb_order where orderNo = ?  ";
		return jdbcTemplate.query(sql, new Object[]{orderNo}, new PtInbOrderMapper());
	}
	
	@Transactional(readOnly = false, value = "txManager", propagation = Propagation.REQUIRED)
	public PtInbOrder get(Long id){
		String sql = " select id, orderNo, status from pt_inb_order where id = ?  ";
		return jdbcTemplate.query(sql, new Object[]{id}, new PtInbOrderMapper()).get(0);
	}
	
	private static final class PtInbOrderMapper implements RowMapper<PtInbOrder> {
	    public PtInbOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	PtInbOrder entity = new PtInbOrder();
	    	entity.setId(rs.getLong("id"));
	    	entity.setOrderNo(rs.getString("orderNo"));
	    	entity.setStatus(rs.getInt("status"));
	        return entity;
	    }
	}
}
