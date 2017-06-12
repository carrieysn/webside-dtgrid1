package com.meitianhui.productSpecialist.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meitianhui.productSpecialist.dao.GoodsUserStatisticsMapper;
import com.meitianhui.productSpecialist.entity.GoodsUserStatistics;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;

/**
 * 用户货品统计信息逻辑实现类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
@Service("goodsUserStatisticsService")
public class GoodsUserStatisticsServiceImpl implements GoodsUserStatisticsService {

	@Autowired
	private GoodsUserStatisticsMapper goodsUserStatisticsMapper;
	
	@Override
	public GoodsUserStatistics queryUserStatistics(String user_id) {
		if(StringUtils.isNotEmpty(user_id)) {
			return goodsUserStatisticsMapper.queryUserStatistics(user_id);
		}
		return null;
	}

	@Override
	public boolean addUserPushlishNum(String user_id) {
		return this.addUserStatisticsByType(user_id, "publish");
	}

	@Override
	public boolean addUserRecommendNum(String user_id) {
		return this.addUserStatisticsByType(user_id, "recommend");
	}

	@Override
	public boolean addUserAttentionNum(String user_id) {
		return this.addUserStatisticsByType(user_id, "attention");
	}

	@Override
	public boolean addUserCommentNum(String user_id) {
		return this.addUserStatisticsByType(user_id, "comment");
	}
	
	@Transactional
	private boolean addUserStatisticsByType(String user_id, String type){
		if(StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(type)) {
			//首先判断用户是否存在统计信息
			synchronized (user_id) {
				long count = goodsUserStatisticsMapper.existsUserStatistics(user_id);
				if(count < 1){  //不存在，则先需要初始化相关数据
					goodsUserStatisticsMapper.initUserStatistics(user_id);
				}
				//新增对应的统计数量
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("user_id", user_id);
				params.put("type", type);
				int i = goodsUserStatisticsMapper.addUserStatisticsByType(params);
				return i > 0;
			}
		}
		return false;
	}

}
