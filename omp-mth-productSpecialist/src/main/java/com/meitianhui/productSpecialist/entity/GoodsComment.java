package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 货品评论实体类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public class GoodsComment implements Serializable {

	private static final long serialVersionUID = 2044691778177693456L;

	private String comment_id; // 评论标识
	private String goods_id; // 货物标识
	private String user_id; // 评论用户
	private String content; // 评论内容
	private String created_date; // 创建时间

	// 扩展属性
	private String user_name; // 用户名

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
