package com.meitianhui.productSpecialist.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.entity.User;
import com.meitianhui.productSpecialist.dao.GoodsLogMapper;
import com.meitianhui.productSpecialist.entity.GoodsLog;
import com.meitianhui.productSpecialist.service.GoodsLogService;

@Service("goodsLogService")
public class GoodsLogServiceImpl implements GoodsLogService{

	@Autowired
	private GoodsLogMapper goodsLogMapper;
	
	@Override
	public void saveGoodsLog(String token, String goods_id, String category, String event) {
		User user = UserCache.getUser(token);
		Team team = user.getTeam();
		GoodsLog log = new GoodsLog();
		log.setLog_id(UUID.randomUUID().toString());
		log.setTracked_by(user.getUser_id());
		log.setTracked_date(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		log.setCategory(category);
		log.setEvent(team.getTeam_name() + "|" + user.getName() + "|" + event);
		log.setGoods_id(goods_id);
		goodsLogMapper.saveGoodsLog(log);
	}

}
