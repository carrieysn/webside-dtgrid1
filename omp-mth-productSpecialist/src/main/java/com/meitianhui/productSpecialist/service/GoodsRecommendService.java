package com.meitianhui.productSpecialist.service;

import java.util.List;

import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.productSpecialist.entity.GoodsAttention;
import com.meitianhui.productSpecialist.entity.GoodsRecommend;

/***
 * 产品推荐逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public interface GoodsRecommendService {

	/***
	 * 用户推荐产品
	 * 
	 * @param goods_id
	 * @param display_area_id
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date 2016年2月26日
	 */
	public GoodsRecommend recommendGoods(String goods_id, String display_area_id, String token) throws PlatformApiException;
	
	/***
	 * 用户关注产品
	 * @param goods_id
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public GoodsAttention attentionGoods(String goods_id, String token) throws PlatformApiException;
	
	/***
	 * 查询用户推荐产品到哪些展馆列表，每个用户只能推荐到不同展馆一次
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public List<GoodsRecommend> queryUserRecommendListByGoodsId(String goods_id, String user_id);
	
	/***
	 * 查询用户是否推荐过产品到某个展馆
	 * @param goods_id
	 * @param display_area_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public boolean isRecommended(String goods_id, String display_area_id, String user_id);
	
	/***
	 * 查询用户是否关注过指定产品，每个用户只能关注相同产品一次
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public boolean isAttentioned(String goods_id, String user_id);

}
