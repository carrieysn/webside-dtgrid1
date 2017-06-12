package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 招商评论
 * 
 * @author 丁硕
 * @date 2016年12月7日
 */
public class IeComment implements Serializable {

	private static final long serialVersionUID = -2727707205496824777L;

	private String comment_id; // 评论标识
	private String subject_id; // 主题标识
	private String content; // 内容
	private String commentator; // 评论人
	private String created_date; // 创建时间

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentator() {
		return commentator;
	}

	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

}
