package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 买手订单商品
 * 
 * @author 丁硕
 * @date 2016年5月30日
 */
public class PsOrderItem implements Serializable {

	private static final long serialVersionUID = -3719936910086303600L;

	private String order_item_id; // 订单商品标识
	private String order_id; // 订单标识
	private String goods_id; // 商品标识
	private String goods_title; // 商品标题
	private String goods_pic_info; // 货品图片信息，JSON格式，{[title=原始图,path_id:xxxxx1],[title=缩小图,path_id:xxxxx2]}
	private int qty; // 数量
	private Integer weight; // 重量
	private String goods_unit; // 单位
	private BigDecimal sale_price; // 售价
	private BigDecimal total_fee; // 金额小计
	private BigDecimal discount_fee; // 优惠金额
	private String status; // 状态，可选值：normal（正常）delete（删除）
	private String created_date; // 创建时间
	private String remark; // 备注

	public String getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(String order_item_id) {
		this.order_item_id = order_item_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_title() {
		return goods_title;
	}

	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}

	public String getGoods_pic_info() {
		return goods_pic_info;
	}

	public void setGoods_pic_info(String goods_pic_info) {
		this.goods_pic_info = goods_pic_info;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getGoods_unit() {
		return goods_unit;
	}

	public void setGoods_unit(String goods_unit) {
		this.goods_unit = goods_unit;
	}

	public BigDecimal getSale_price() {
		return sale_price;
	}

	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
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
