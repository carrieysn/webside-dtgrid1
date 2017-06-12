package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/**
 * 产品展馆实体类
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public class GoodsDisplayArea implements Serializable {

	private static final long serialVersionUID = 6599829877247969053L;
	private String display_area_id; // 展馆标识
	private String display_area; // 展馆名称
	private String is_sell; // 是否开放销售展馆，Y（是）N（否）
	private String icon_path; // 图标路径
	private Integer sort_order; // 排序
	private String status; // 状态，可选值：normal（正常）delete（删除）
	private String created_date; // 创建时间
	private String remark; // 备注

	// 业务扩展属性
	private long goods_count; // 推荐产品数量

	public String getDisplay_area_id() {
		return display_area_id;
	}

	public void setDisplay_area_id(String display_area_id) {
		this.display_area_id = display_area_id;
	}

	public String getDisplay_area() {
		return display_area;
	}

	public void setDisplay_area(String display_area) {
		this.display_area = display_area;
	}

	public String getIs_sell() {
		return is_sell;
	}

	public void setIs_sell(String is_sell) {
		this.is_sell = is_sell;
	}

	public String getIcon_path() {
		return icon_path;
	}

	public void setIcon_path(String icon_path) {
		this.icon_path = icon_path;
	}

	public Integer getSort_order() {
		return sort_order;
	}

	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
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

	public long getGoods_count() {
		return goods_count;
	}

	public void setGoods_count(long goods_count) {
		this.goods_count = goods_count;
	}

}
