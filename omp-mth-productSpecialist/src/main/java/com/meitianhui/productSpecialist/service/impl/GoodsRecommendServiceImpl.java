package com.meitianhui.productSpecialist.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.productSpecialist.dao.GoodsRecommendMapper;
import com.meitianhui.productSpecialist.entity.GoodsAttention;
import com.meitianhui.productSpecialist.entity.GoodsRecommend;
import com.meitianhui.productSpecialist.service.GoodsRecommendService;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;

@Service("goodsRecommendService")
public class GoodsRecommendServiceImpl implements GoodsRecommendService{
	
	@Autowired
	private GoodsRecommendMapper goodsRecommendMapper;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsUserStatisticsService goodsUserStatisticsService;

	@Override
	@Transactional
	public GoodsRecommend recommendGoods(String goods_id, String display_area_id, String token) throws PlatformApiException {
		if(StringUtils.isNotEmpty(goods_id) && StringUtils.isNotEmpty(display_area_id) && StringUtils.isNotEmpty(token)) {
			String user_id = UserCache.getUser(token).getUser_id();
			//查询用户是否推荐过此产品到该展馆，只能推荐一次
			boolean isRecommended = this.isRecommended(goods_id, display_area_id, user_id);
			if(!isRecommended) {
				GoodsRecommend recommend = new GoodsRecommend();
				recommend.setGoods_id(goods_id);
				recommend.setUser_id(user_id);
				recommend.setDisplay_area_id(display_area_id);
				goodsRecommendMapper.addGoodsRecommend(recommend);
				//增加产品的推荐数
				goodsService.addProductRecommendNum(goods_id, user_id);
				
				//如果推荐过同样的产品，不计用户推荐统计数
				Map<String, String> params = new HashMap<String, String>();
				params.put("goods_id", goods_id);
				params.put("user_id", user_id);
				long count = goodsRecommendMapper.isRecommended(params);
				if(count == 1) {
					//增加用户的推荐数
					goodsUserStatisticsService.addUserRecommendNum(user_id);
				}
				return recommend;
			} else{
				throw new PlatformApiException("请勿重复推荐！");
			}
		}
		return null;
	}

	@Override
	@Transactional
	public GoodsAttention attentionGoods(String goods_id, String token) throws PlatformApiException {
		if(StringUtils.isNotEmpty(goods_id) && StringUtils.isNotEmpty(token)) {
			String user_id = UserCache.getUser(token).getUser_id();
			//查询用户是否关注过此产品，只能关注一次
			boolean isAttentioned = this.isAttentioned(goods_id, user_id);
			if(!isAttentioned) {
				GoodsAttention attention = new GoodsAttention();
				attention.setGoods_id(goods_id);
				attention.setUser_id(user_id);
				goodsRecommendMapper.addGoodsAttention(attention);
				//增加用户的关注数
				goodsUserStatisticsService.addUserAttentionNum(user_id);
				//增加产品的关注数
				goodsService.addProductAttentionNum(goods_id, user_id);
				return attention;
			} else{
				throw new PlatformApiException("请勿重复关注！");
			}
		}
		return null;
	}

	@Override
	public List<GoodsRecommend> queryUserRecommendListByGoodsId(String goods_id, String user_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("goods_id", goods_id);
		params.put("user_id", user_id);
		return goodsRecommendMapper.queryUserRecommendListByGoodsId(params);
	}
	
	@Override
	public boolean isRecommended(String goods_id, String display_area_id, String user_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("goods_id", goods_id);
		params.put("display_area_id", display_area_id);
		params.put("user_id", user_id);
		long cunt = goodsRecommendMapper.isRecommended(params);
		return cunt > 0;
	}

	@Override
	public boolean isAttentioned(String goods_id, String user_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("goods_id", goods_id);
		params.put("user_id", user_id);
		long cunt = goodsRecommendMapper.isAttentioned(params);
		return cunt > 0;
	}
	
}
