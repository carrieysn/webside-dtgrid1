package com.meitianhui.productSpecialist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meitianhui.productSpecialist.entity.Goods;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;

/***
 * 货品数据处理接口
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public interface GoodsMapper {

	/**
	 * 根据条件查询产品列表
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public List<Goods> queryGoodsList(Map<String, Object> params);
	
	/***
	 * 根据条件查询用户产品分页信息
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public List<Goods> queryUserGoodsList(Map<String, Object> params);
	
	/***
	 * 新增货品信息
	 * @param goods
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public void saveGoods(Goods goods);
	
	/***
	 * 更新货品信息
	 * @param goods
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public void updateGoods(Goods goods);
	
	/***
	 * 删除产品信息，实际只是将字段进行更新
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	public int deleteGoods(@Param("goods_id")String goods_id, @Param("user_id")String user_id);
	
	/***
	 * 删除用户对产品的发布统计信息
	 * @param goods_id
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	public void deleteUserPublishStatistics(@Param("goods_id")String goods_id);
	
	/***
	 * 删除用户对产品的推荐统计信息
	 * @param goods_id
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	public void deleteUserRecommendStatistics(@Param("goods_id")String goods_id);
	
	/**
	 * 删除用户对产品的关注统计信息
	 */
	public void deleteUserAttentionStatistics(@Param("goods_id")String goods_id);
	
	/***
	 * 删除用户对产品的评论统计信息
	 * @param goods_id
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	public void deleteUserCommentStatistics(@Param("goods_id")String goods_id);
	
	/***
	 * 根据货品编号查询单个货品信息
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public GoodsSellExt queryOneGoods(String goods_id);
	
	/***
	 * 根据类型增加产品相应属性的数量
	 * @param goods_id
	 * @param type
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	public int addProductNumByType(Map<String, Object> params);
	
	/***
	 * 根据组织编号查询所有的组织下所有的产品编号
	 * @param team_id
	 * @return
	 * @author 丁硕
	 * @date   2016年5月6日
	 */
	public List<String> queryGoodsIdListByTeam(@Param("team_id")String team_id);
	
	
	//============首页展示需要显示的数据===============
	/***
	 * 查询出每个地区的产品数
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public List<Map<String, Object>> queryCountByArea();
	
	/***
	 * 查询商品不同类型的总数信息
	 * @return
	 * @author 丁硕
	 * @date   2016年4月25日
	 */
	public List<Map<String, Object>> queryCategoryCount();
	
}
