package com.meitianhui.productSpecialist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.Pagetion;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.service.TeamPlatformService;
import com.meitianhui.platform.utils.IDUtil;
import com.meitianhui.platform.utils.openapi.GoodsApiUtil;
import com.meitianhui.productSpecialist.dao.GoodsMapper;
import com.meitianhui.productSpecialist.entity.Goods;
import com.meitianhui.productSpecialist.entity.GoodsCategoryEnum;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.service.GoodsLogService;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;
import com.meitianhui.productSpecialist.service.StoresService;

/***
 * 货品逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsUserStatisticsService goodsUserStatisticsService;
	
	@Autowired
	private GoodsLogService goodsLogService;
	
	@Autowired
	private TeamPlatformService teamPlatformService;
	
	@Autowired
	private StoresService storesService; 
	
	@Override
	public Page queryProductPage(int pageNum, int pageSize, Map<String, Object> params) {
		Pagetion.startPage(pageNum, pageSize);
		goodsMapper.queryGoodsList(params);
		return Pagetion.endPage();
	}
	
	@Override
	public Page queryUserProductList(int pageNum, int pageSize, Map<String, Object> params) {
		Pagetion.startPage(pageNum, pageSize);
		goodsMapper.queryUserGoodsList(params);
		return Pagetion.endPage();
	}
	
	@Override
	@Transactional
	public Goods saveOrUpdateGoods(GoodsSellExt goods, String token) throws PlatformApiException {
		if(goods != null && StringUtils.isNotBlank(token)){
			User user = UserCache.getUser(token);
			String user_id = user.getUser_id();
			goods.setStatus(CommonConstant.status_normal);
			if(StringUtils.isBlank(goods.getGoods_id())) {  //新增
				goods.setGoods_id(IDUtil.getUUID()); //goods_id
				goods.setGoods_code(generateGoodsCode(goods.getCategory()));//生成对应的商品编码
				goods.setOwner_id(user_id);
				goods.setCreated_date(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				goodsMapper.saveGoods(goods);
				//增加用户发布数量
				goodsUserStatisticsService.addUserPushlishNum(user_id);
				//记录日志
				goodsLogService.saveGoodsLog(token, goods.getGoods_id(), "goods", "新增");
			} else{  //修改
				goodsMapper.updateGoods(goods);
				goodsLogService.saveGoodsLog(token, goods.getGoods_id(), "goods", "修改");
			}
			//同步信息到开放平台
			//设置对应的代理人信息
			Team team = teamPlatformService.getCompanyTeam(user.getTeam().getTeam_id());
			goods.setAgent(team == null ? "" : team.getTeam_name());
			//查询对应的供应商信息，同步到开放平台
			if(StringUtils.isNotEmpty(goods.getSupplier_id())){
				Map<String, Object> supplier = storesService.queryOneSupplier(goods.getSupplier_id());
				if(supplier != null && !supplier.isEmpty()){
					goods.setSupplier(supplier.get("supplier_name") + "");
				}
			}
			GoodsApiUtil.syncGoods(JSONObject.parseObject(JSONObject.toJSONString(goods)));
			return goods;
		}
		return null;
	}
	
	/**
	 * 生成商品code
	 * @param category_key
	 * @return
	 * @author 丁硕
	 * @date   2016年11月21日
	 */
	private synchronized String generateGoodsCode(String category_key){
		GoodsCategoryEnum  categoryEnum = GoodsCategoryEnum.getCategory(category_key);
		return categoryEnum.getCode() + StringUtils.substring((new Date()).getTime() + "", 0, 12);
	}
	
	@Override
	@Transactional
	public boolean deleteGoods(String goods_id, String token) throws PlatformApiException {
		if(StringUtils.isNotBlank(goods_id)) {
			GoodsSellExt goods = goodsMapper.queryOneGoods(goods_id);
			if(goods != null){
				String user_id = UserCache.getUser(token).getUser_id();
				int i = goodsMapper.deleteGoods(goods_id, user_id);
				if(i > 0){
					//删除用户的产品统计信息
					goodsMapper.deleteUserPublishStatistics(goods_id);
					goodsMapper.deleteUserRecommendStatistics(goods_id);
					goodsMapper.deleteUserAttentionStatistics(goods_id);
					goodsMapper.deleteUserCommentStatistics(goods_id);
					//同步信息到开放平台
					GoodsApiUtil.syncGoodsStatus(goods_id, "delete");
				} else{
					throw new PlatformApiException("删除失败，未找到指定产品！");
				}
			} else{
				throw new PlatformApiException("删除失败，未找到指定产品！");
			}
		}
		return false;
	}

	@Override
	public GoodsSellExt queryOneGoods(String goods_id) throws PlatformApiException {
		if(StringUtils.isNotEmpty(goods_id)) {
			GoodsSellExt goods = goodsMapper.queryOneGoods(goods_id);
			goods = this.loadGoodsSellExtInfo(goods);
			return goods;
		}
		return null;
	}
	
	@Override
	public GoodsSellExt loadGoodsSellExtInfo(GoodsSellExt goods) throws PlatformApiException {
		if(goods != null){
			String goods_id = goods.getGoods_id();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("goods_id", goods_id);
			JSONObject json =  GoodsApiUtil.queryGoodsDetail(params);
			if(json != null){
				try{
					BeanUtils.populate(goods, json);
				} catch(Exception e){
					throw new PlatformApiException("500", "加载商品信息出错！", e);
				}
			}
		}
		return goods;
	}

	@Override
	public boolean addProductRecommendNum(String goods_id, String user_id) {
		return addProductNumByType(goods_id, user_id, "recommend");
	}

	@Override
	public boolean addProductAttentionNum(String goods_id, String user_id) {
		return addProductNumByType(goods_id, user_id, "attention");
	}

	@Override
	public boolean addProductGradeNum(String goods_id, String user_id) {
		return addProductNumByType(goods_id, user_id, "grade");
	}

	@Override
	public boolean addProductCommentNum(String goods_id, String user_id) {
		return addProductNumByType(goods_id, user_id, "comment");
	}
	
	private boolean addProductNumByType(String goods_id, String user_id, String type){
		if(StringUtils.isNotEmpty(goods_id) && StringUtils.isNotEmpty(type)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("goods_id", goods_id);
			params.put("type", type);
			int i = goodsMapper.addProductNumByType(params);
			return i > 0;
		}
		return false;
	}
	
	@Override
	public List<String> queryGoodsIdListByTeam(String team_id) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotEmpty(team_id)){
			list = goodsMapper.queryGoodsIdListByTeam(team_id);
		}
		if(list.size() < 1){	//如果没有，则显示不存在的商品
			list.add("-1");
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCountByArea() {
		return goodsMapper.queryCountByArea();
	}

}
