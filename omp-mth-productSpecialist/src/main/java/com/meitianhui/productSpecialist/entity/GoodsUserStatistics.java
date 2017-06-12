package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 用户货品统计信息实体类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public class GoodsUserStatistics implements Serializable {

	private static final long serialVersionUID = 5351713613295920256L;

	private String user_id; // 用户标识
	private int publish_num; // 发布数
	private int recommend_num; // 推荐数
	private int attention_num; // 关注数
	private int comment_num; // 评论数
	private String created_date; // 创建时间
	private String remark; // 备注

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getPublish_num() {
		return publish_num;
	}

	public void setPublish_num(int publish_num) {
		this.publish_num = publish_num;
	}

	public int getRecommend_num() {
		return recommend_num;
	}

	public void setRecommend_num(int recommend_num) {
		this.recommend_num = recommend_num;
	}

	public int getAttention_num() {
		return attention_num;
	}

	public void setAttention_num(int attention_num) {
		this.attention_num = attention_num;
	}

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
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
