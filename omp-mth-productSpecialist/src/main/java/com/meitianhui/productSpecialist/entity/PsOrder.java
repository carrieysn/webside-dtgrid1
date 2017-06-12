package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 买手订单
 * 
 * @author 丁硕
 * @date 2016年5月30日
 */
public class PsOrder implements Serializable {

	private static final long serialVersionUID = 831378736541646353L;

	private String order_id; // 订单标识
	private String order_no; // 订单编号
	private String order_date; // 订单日期
	private String desc1; // 订单商品描述
	private String desc2; // 订单其它描述
	private int item_num; // 订单商品数量
	private String buyer_company_id; // 买家公司标识
	private String buyer_id; // 买家标识
	private String seller_company_id; // 卖家公司标识
	private String seller_id; // 卖家标识
	private String payment_way_key; // 支付方式
	private BigDecimal total_fee; // 订单总金额
	private BigDecimal discount_fee; // 订单优惠总金额
	private BigDecimal sale_fee; // 订单销售总金额
	private BigDecimal prepayment; // 预付账款
	private Integer weight; // 订单商品的总重量
	private String delivery_address; // 配送地址
	private String contact_person; // 联系人
	private String contact_tel; // 联系人电话
	private String logistics; // 物流信息
	private String status; // 状态，可选值：processing（订单处理中）、（confirmed（订单确定）、payed（已支付）、delivered（已发货）、received（已收货）、cancelled（订单取消）、closed（订单完成）
	private String created_date; // 创建时间
	private String remark; // 备注

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public int getItem_num() {
		return item_num;
	}

	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}

	public String getBuyer_company_id() {
		return buyer_company_id;
	}

	public void setBuyer_company_id(String buyer_company_id) {
		this.buyer_company_id = buyer_company_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSeller_company_id() {
		return seller_company_id;
	}

	public void setSeller_company_id(String seller_company_id) {
		this.seller_company_id = seller_company_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getPayment_way_key() {
		return payment_way_key;
	}

	public void setPayment_way_key(String payment_way_key) {
		this.payment_way_key = payment_way_key;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	public BigDecimal getDiscount_fee() {
		return discount_fee;
	}

	public void setDiscount_fee(BigDecimal discount_fee) {
		this.discount_fee = discount_fee;
	}

	public BigDecimal getSale_fee() {
		return sale_fee;
	}

	public void setSale_fee(BigDecimal sale_fee) {
		this.sale_fee = sale_fee;
	}

	public BigDecimal getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(BigDecimal prepayment) {
		this.prepayment = prepayment;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
