package com.meitianhui.productSpecialist.service;

import java.util.Map;

import com.meitianhui.platform.entity.Page;
import com.meitianhui.productSpecialist.entity.IeComment;
import com.meitianhui.productSpecialist.entity.IeSubject;

/***
 * 招商模块逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年12月7日
 */
public interface InvestmentService {

	/***
	 * 根据条件查询招商分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public Page queryInvestmentListPage(int pageNum, int pageSize, Map<String, Object> params);
	
	/***
	 * 查询我的招商分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public Page queryMyInvestmentListPage(int pageNum, int pageSize, String token);
	
	/***
	 * 查询单个招商信息
	 * @param subject_id
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public Map<String, Object> queryOneInvestment(String subject_id);
	
	/***
	 * 查询招商信息，用于编辑
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2017年3月17日
	 */
	public IeSubject queryOneInvestment(Map<String, Object> params);
	
	/***
	 * 保存招商信息
	 * @param subject
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public void saveInvestment(IeSubject subject, String token);
	
	/***
	 * 删除招商信息
	 * @param subject_id
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public void deleteInvestment(String subject_id);
	
	/***
	 * 查询用户招商信息总数
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public int queryInvesetmentCount(String token);
	
	
	//======================评论相关===============
	/***
	 * 查询招商评论列表
	 * @param pageNum
	 * @param pageSize
	 * @param subject_id
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public Page queryCommentListPage(int pageNum, int pageSize, String subject_id);
	
	/***
	 * 保存招商评论信息
	 * @param comment
	 * @param token
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	public void saveSubjectComment(IeComment comment, String token);
}
