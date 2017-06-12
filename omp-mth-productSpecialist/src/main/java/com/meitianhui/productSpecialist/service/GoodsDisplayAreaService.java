package com.meitianhui.productSpecialist.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meitianhui.productSpecialist.entity.GoodsDisplayArea;

/***
 * 产品展馆逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年4月25日
 */
public interface GoodsDisplayAreaService {

	/**
	 * 根据标识查询分类信息
	 * @param category_id
	 * @return
	 * @author 丁硕
	 * @date   2016年3月11日
	 */
	public GoodsDisplayArea queryDisplayArea(String display_area_id);
	
	/***
	 * 查询展馆所有信息列表，并查询出相应展馆推荐产品数量,对展馆与产品相同的进行去重
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public List<GoodsDisplayArea> queryDisplayAreaListFullInfo();
	
	/***
	 * 查询开放销售展馆列表信息,用于用户进行推荐操作
	 * @return
	 * @author 丁硕
	 * @date   2016年3月11日
	 */
	public List<GoodsDisplayArea> queryDisplayAreaList();
	
	/***
	 * 根据产品标识，查询产品所有的展馆信息
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年4月25日
	 */
	public List<GoodsDisplayArea> queryDisplayAreaListByGoodsId(@Param("goods_id")String goods_id);
}
