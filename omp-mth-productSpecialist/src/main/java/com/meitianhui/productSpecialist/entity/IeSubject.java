package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 招商信息实体类
 * 
 * @author 丁硕
 * @date 2016年12月7日
 */
public class IeSubject implements Serializable {

	private static final long serialVersionUID = -5196369517894264051L;
	private String subject_id; // 主题标识
	private String title; // 标题
	private String content; // 内容
	private String publisher; // 发布人
	private String expiration_date; // 有效期
	private String created_date; // 创建时间
	private String modified_date; // 修改时间
	private String remark; // 备注

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getModified_date() {
		return modified_date;
	}

	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
