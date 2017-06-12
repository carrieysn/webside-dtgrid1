package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 用户产品推荐实体类
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public class GoodsRecommend implements Serializable {

	private static final long serialVersionUID = -7332594964910822172L;

	public String recommend_id; // 推荐标识
	public String goods_id; // 货物标识
	public String user_id; // 推荐用户
	public String display_area_id; // '推荐展馆标识
	public String created_date; // '创建时间
	public String remark; // '备注

	public String getRecommend_id() {
		return recommend_id;
	}

	public void setRecommend_id(String recommend_id) {
		this.recommend_id = recommend_id;
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

	public String getDisplay_area_id() {
		return display_area_id;
	}

	public void setDisplay_area_id(String display_area_id) {
		this.display_area_id = display_area_id;
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
