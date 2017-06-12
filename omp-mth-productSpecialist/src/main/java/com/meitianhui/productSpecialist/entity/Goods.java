package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 货品实体类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public class Goods implements Serializable {

	private static final long serialVersionUID = -2428880697545937152L;
	private String goods_id; // 货品标识
	private String goods_code; // 商品编码
	private String title; // 标题
	private String desc1; // 描述
	private String area_id; // 地区标识
	private String label; // 标签
	private String owner_id; // 发布货品用户
	private String category; // 类型，可选值：尾货、新品、预售、试样、二手、其它
	private String display_area; // 类目，可选值：工艺、美食、母婴、日杂、家具、兑换
	private String contact_person; // 联系人
	private String contact_tel; // 联系人电话
	private String pic_info; // 货品图片信息，JSON格式，{[title=原始图,path_id:xxxxx1],[title=缩小图,path_id:xxxxx2]}
	private String pic_detail_info; // 货品详细，JSON格式，{[title=原始图,path_id:xxxxx1],[title=缩小图,path_id:xxxxx2]}
	private Integer recommend_num; // 推荐数
	private Integer attention_num; // 关注数
	private Integer grade_num; // 评分数
	private Integer comment_num; // 评论数
	private String status; // 状态，可选值：normal（正常）delete（删除）
	private String created_date; // 创建时间
	private String remark; // 备注

	// 发布产品用户相关信息，主要用于页面显示
	private String user_name; // 用户名
	private String user_tel; // 用户手机号
	private String grade_avg; // 总评分

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_code() {
		return goods_code;
	}

	public void setGoods_code(String goods_code) {
		this.goods_code = goods_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDisplay_area() {
		return display_area;
	}

	public void setDisplay_area(String display_area) {
		this.display_area = display_area;
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

	public String getPic_info() {
		return pic_info;
	}

	public void setPic_info(String pic_info) {
		this.pic_info = pic_info;
	}

	public String getPic_detail_info() {
		return pic_detail_info;
	}

	public void setPic_detail_info(String pic_detail_info) {
		this.pic_detail_info = pic_detail_info;
	}

	public Integer getRecommend_num() {
		return recommend_num;
	}

	public void setRecommend_num(Integer recommend_num) {
		this.recommend_num = recommend_num;
	}

	public Integer getAttention_num() {
		return attention_num;
	}

	public void setAttention_num(Integer attention_num) {
		this.attention_num = attention_num;
	}

	public Integer getGrade_num() {
		return grade_num;
	}

	public void setGrade_num(Integer grade_num) {
		this.grade_num = grade_num;
	}

	public Integer getComment_num() {
		return comment_num;
	}

	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public String getGrade_avg() {
		return grade_avg;
	}

	public void setGrade_avg(String grade_avg) {
		this.grade_avg = grade_avg;
	}

}
