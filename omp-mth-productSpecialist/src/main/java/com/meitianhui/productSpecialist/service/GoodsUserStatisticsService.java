package com.meitianhui.productSpecialist.service;

import com.meitianhui.productSpecialist.entity.GoodsUserStatistics;

/***
 * 用户货品统计信息逻辑接口
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public interface GoodsUserStatisticsService {

	/***
	 * 查询用户货品统计信息
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public GoodsUserStatistics queryUserStatistics(String user_id);
	
	/***
	 * 
	 * @param user_id
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public boolean addUserPushlishNum(String user_id);
	
	/***
	 * 增加用户推荐产品数量
	 * @param user_id
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public boolean addUserRecommendNum(String user_id);
	
	/**
	 * 增加用户关注产品数量
	 * @param user_id
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public boolean addUserAttentionNum(String user_id);
	
	/***
	 * 增加用户评论数量
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public boolean addUserCommentNum(String user_id);
}
