package com.meitianhui.productSpecialist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meitianhui.productSpecialist.dao.GoodsDisplayAreaMapper;
import com.meitianhui.productSpecialist.entity.GoodsDisplayArea;
import com.meitianhui.productSpecialist.service.GoodsDisplayAreaService;

/***
 * 产品展馆逻辑处理实现类
 * 
 * @author 丁硕
 * @date 2016年4月25日
 */
@Service("goodsDisplayAreaService")
public class GoodsDisplayAreaServiceImpl implements GoodsDisplayAreaService {
	
	@Autowired
	private GoodsDisplayAreaMapper goodsDisplayAreaMapper;

	@Override
	public GoodsDisplayArea queryDisplayArea(String display_area_id) {
		return goodsDisplayAreaMapper.queryDisplayArea(display_area_id);
	}

	@Override
	public List<GoodsDisplayArea> queryDisplayAreaListFullInfo() {
		return goodsDisplayAreaMapper.queryDisplayAreaListFullInfo();
	}

	@Override
	public List<GoodsDisplayArea> queryDisplayAreaList() {
		return goodsDisplayAreaMapper.queryDisplayAreaList();
	}

	@Override
	public List<GoodsDisplayArea> queryDisplayAreaListByGoodsId(String goods_id) {
		return goodsDisplayAreaMapper.queryDisplayAreaListByGoodsId(goods_id);
	}

}
