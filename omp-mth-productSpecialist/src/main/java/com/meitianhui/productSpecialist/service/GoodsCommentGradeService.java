package com.meitianhui.productSpecialist.service;

import com.meitianhui.platform.entity.Page;
import com.meitianhui.productSpecialist.entity.GoodsGrade;

/***
 * 产品评论评分逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public interface GoodsCommentGradeService {

	/***
	 * 新增一条用户产品评论记录
	 * @param goods_id 产品标识
	 * @param grade 评分对象
	 * @param comment_content 评论内容
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public boolean addGoodsCommentGrade(String goods_id, GoodsGrade grade, String comment_content, String token);
	
	/**
	 * 添加产品评分
	 * @param grade
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public boolean addGoodsGrade(String goods_id, GoodsGrade grade, String token);
	
	/***
	 * 添加产品评论
	 * @param comment
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public boolean addGoodsCommment(String goods_id, String comment_content, String token);
	
	/***
	 * 查询产品评论分页信息
	 * @param pageNum
	 * @param pageSize
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public Page queryGoodsCommentPage(int pageNum, int pageSize, String goods_id);
}
