package com.meitianhui.productSpecialist.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.Pagetion;
import com.meitianhui.productSpecialist.dao.GoodsCommentGradeMapper;
import com.meitianhui.productSpecialist.entity.GoodsComment;
import com.meitianhui.productSpecialist.entity.GoodsGrade;
import com.meitianhui.productSpecialist.service.GoodsCommentGradeService;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;

/***
 * 产品评论评分逻辑处理实现类
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
@Service("goodsCommentGradeService")
public class GoodsCommentGradeServiceImpl implements GoodsCommentGradeService{

	@Autowired
	private GoodsCommentGradeMapper goodsCommentGradeMapper;
	
	@Autowired
	private GoodsUserStatisticsService goodsUserStatisticsService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Override
	@Transactional
	public boolean addGoodsCommentGrade(String goods_id, GoodsGrade grade, String comment_content, String token) {
		return this.addGoodsGrade(goods_id, grade, token) & this.addGoodsCommment(goods_id, comment_content, token);
	}

	@Override
	@Transactional
	public boolean addGoodsGrade(String goods_id, GoodsGrade grade, String token) {
		if(grade != null && StringUtils.isNotEmpty(token)){
			String user_id = UserCache.getUser(token).getUser_id();
			grade.setUser_id(user_id);
			grade.setGoods_id(goods_id);
			goodsCommentGradeMapper.addGoodsGrade(grade);
			//增加产品的评分数
			goodsService.addProductGradeNum(goods_id, user_id);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean addGoodsCommment(String goods_id, String comment_content, String token) {
		if(StringUtils.isNotEmpty(goods_id) && StringUtils.isNotEmpty(comment_content) && StringUtils.isNotEmpty(token)){
			String user_id = UserCache.getUser(token).getUser_id();
			GoodsComment comment = new GoodsComment();
			comment.setContent(comment_content);
			comment.setGoods_id(goods_id);
			comment.setUser_id(user_id);
			goodsCommentGradeMapper.addGoodsComment(comment);
			//增加产品的评论数
			goodsService.addProductCommentNum(goods_id, user_id);
			
			//判断用户是否对产品评论过，如果评论过，则不添加评论产品数
			Map<String, String> params = new HashMap<String, String>();
			params.put("goods_id", goods_id);
			params.put("user_id", user_id);
			long count = goodsCommentGradeMapper.isCommented(params);
			if(count == 1) {
				//增加用户的评论数
				goodsUserStatisticsService.addUserCommentNum(user_id);
			}
			return true;
		}
		return false;
	}

	@Override
	public Page queryGoodsCommentPage(int pageNum, int pageSize, String goods_id) {
		Pagetion.startPage(pageNum, pageSize);
		goodsCommentGradeMapper.queryGoodsCommentList(goods_id);
		return Pagetion.endPage();
	}
	

}
