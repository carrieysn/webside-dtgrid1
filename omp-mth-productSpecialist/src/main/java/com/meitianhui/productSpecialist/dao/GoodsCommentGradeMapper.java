package com.meitianhui.productSpecialist.dao;

import java.util.List;
import java.util.Map;

import com.meitianhui.productSpecialist.entity.GoodsComment;
import com.meitianhui.productSpecialist.entity.GoodsGrade;

/***
 * 产品评论评分数据层处理接口
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public interface GoodsCommentGradeMapper {

	/***
	 * 添加产品评分
	 * @param grade
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public void addGoodsGrade(GoodsGrade grade);
	
	/***
	 * 添加产品评论
	 * @param comment
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public void addGoodsComment(GoodsComment comment);
	
	/***
	 * 查询用户是否对产品评论过
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年3月3日
	 */
	public long isCommented(Map<String, String> params);
	
	/***
	 * 查询产品评论列表信息
	 * @param goods_id
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public List<GoodsComment> queryGoodsCommentList(String goods_id);
}
