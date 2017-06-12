package com.meitianhui.productSpecialist.dao;

import java.util.Map;

import com.meitianhui.productSpecialist.entity.GoodsUserStatistics;

/***
 * 用户货品统计信息数据操作接口
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public interface GoodsUserStatisticsMapper {
	
	/***
	 * 查询用户货品统计信息
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public GoodsUserStatistics queryUserStatistics(String user_id);

	/***
	 * 判断是否存在用户统计信息
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public long existsUserStatistics(String user_id);
	
	/***
	 * 初始化用户产品统计信息
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public void initUserStatistics(String user_id);
	
	/***
	 * 根据不同类型，增加用户的统计信息，调用一次+1
	 * @param user_id
	 * @param type
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public int addUserStatisticsByType(Map<String, Object> params);
}
