package com.meitianhui.productSpecialist.dao;

import java.util.List;
import java.util.Map;

import com.meitianhui.productSpecialist.entity.GoodsAttention;
import com.meitianhui.productSpecialist.entity.GoodsRecommend;

/***
 * 产品推荐数据处理接口
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public interface GoodsRecommendMapper {

	/***
	 * 添加一条用户推荐产品记录
	 * @param goods_id
	 * @param category_id
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public int addGoodsRecommend(GoodsRecommend recommend);
	
	/***
	 * 添加用户关注产品记录
	 * @param attention
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public int addGoodsAttention(GoodsAttention attention);
	
	
	/***
	 * 查询用户推荐产品到哪些展馆列表，每个用户只能推荐到不同展馆一次
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public List<GoodsRecommend> queryUserRecommendListByGoodsId(Map<String, String> params);
	
	/***
	 * 查询用户是否推荐过产品到某个展馆
	 * @param goods_id
	 * @param category_id 为空时，查询是否推荐过相同产品
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public long isRecommended(Map<String, String> params);
	
	/***
	 * 查询用户是否关注过指定产品，每个用户只能关注相同产品一次
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public long isAttentioned(Map<String, String> params);
	
}
