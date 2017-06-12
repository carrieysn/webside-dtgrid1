package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 货品日实体类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public class GoodsLog implements Serializable {

	private static final long serialVersionUID = 5019139421170877470L;

	private String log_id; // 日志标识
	private String goods_id; // 货品标识
	private String category; // 事件类别
	private String tracked_date; // 发生时间
	private String tracked_by; // 用户标识
	private String event; // 事件

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTracked_date() {
		return tracked_date;
	}

	public void setTracked_date(String tracked_date) {
		this.tracked_date = tracked_date;
	}

	public String getTracked_by() {
		return tracked_by;
	}

	public void setTracked_by(String tracked_by) {
		this.tracked_by = tracked_by;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
