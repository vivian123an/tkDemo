package com.baturu.tkDemo.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author chenwenan
 * @since  2016.07.30
 */

@Table(name = "pt_inb_order")
public class PtInbOrder {

    /**
     * 主键id
     */
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 销售订单Id（qp_order主键）
     */
    private Long orderId;

    /**
     * 销售订单编号
     */
    private String orderNo;

    /**
     * 供应商Id
     */
    private Integer supplierId;

    /**
     * 供应商订单id
     */
    private Long splyOrderId;

    /**
     * 客服定价id
     */
    private Long purchaseRefId;

    /**
     * 采购单编号
     */
    private String poNo;

    /**
     * 状态（100:待分配、200:待采购、300:已采购、400:已完成、900:已取消）
     * @see com.batulu.mobile.thirdPartyMerge.constants.PoStatus
     */
    private Integer status;

    /**
     * 采购分配人
     */
    private Integer distributeBy;

    /**
     * 采购分配时间
     */
    private String distributeTime;

    /**
     * 采购人Id
     */
    private Integer purchaseBy;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     * 取消人Id
     */
    private Integer cancelBy;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 作业类别（100:供应商打包、200:供应商不打包、300:自采）
     */
    private Integer poType;

    /**
     * 创建人Id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人Id
     */
    private String updateBy;

    /**
     * 最后更新时间
     */
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSplyOrderId() {
		return splyOrderId;
	}

	public void setSplyOrderId(Long splyOrderId) {
		this.splyOrderId = splyOrderId;
	}

	public Long getPurchaseRefId() {
		return purchaseRefId;
	}

	public void setPurchaseRefId(Long purchaseRefId) {
		this.purchaseRefId = purchaseRefId;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDistributeBy() {
		return distributeBy;
	}

	public void setDistributeBy(Integer distributeBy) {
		this.distributeBy = distributeBy;
	}

	public String getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(String distributeTime) {
		this.distributeTime = distributeTime;
	}

	public Integer getPurchaseBy() {
		return purchaseBy;
	}

	public void setPurchaseBy(Integer purchaseBy) {
		this.purchaseBy = purchaseBy;
	}

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public Integer getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(Integer cancelBy) {
		this.cancelBy = cancelBy;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getPoType() {
		return poType;
	}

	public void setPoType(Integer poType) {
		this.poType = poType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    
}
