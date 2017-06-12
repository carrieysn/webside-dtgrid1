package com.meitianhui.productSpecialist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meitianhui.productSpecialist.entity.IeComment;
import com.meitianhui.productSpecialist.entity.IeSubject;

/***
 * 招商模块逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年12月7日
 */
public interface InvestmentMapper {

	/***
	 * 根据条件查询招商列表
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public List<Map<String, Object>> queryInvestmentList(Map<String, Object> params);
	
	/***
	 * 查询单个招商信息
	 * @param subject_id
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public Map<String, Object> queryOneInvestmentDetail(@Param("subject_id")String subject_id);
	
	/**
	 * 查询单个招商信息
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2017年3月17日
	 */
	public IeSubject queryOneInvestment(Map<String, Object> params);
	
	/***
	 * 新增招商信息
	 * @param subject
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public void insertInvestment(IeSubject subject);
	
	/***
	 * 修改招商信息
	 * @param subject
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public int updateInvestment(IeSubject subject);
	
	/***
	 * 删除招商信息
	 * @param subject_id
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public int deleteInvestment(@Param("subject_id")String subject_id);
	
	/***
	 * 查询用户招商总数
	 * @param publisher
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public int queryInvesetmentCount(@Param("publisher")String publisher);
	
	//=====================================评论相关=================================
	
	/***
	 * 查询招商评论列表
	 * @param subject_id
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public List<Map<String, Object>> queryCommentList(@Param("subject_id")String subject_id);
	
	/***
	 * 新增招商评论
	 * @param comment
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public void insertSubjectComment(IeComment comment);
}
