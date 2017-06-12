package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 用户产品关注实体类
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public class GoodsAttention implements Serializable {

	private static final long serialVersionUID = 6132656095850525524L;

	private String attention_id; // 关注标识
	private String goods_id; // 货物标识
	private String user_id; // 关注用户
	private String created_date; // 创建时间
	private String remark; // 备注

	public String getAttention_id() {
		return attention_id;
	}

	public void setAttention_id(String attention_id) {
		this.attention_id = attention_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
