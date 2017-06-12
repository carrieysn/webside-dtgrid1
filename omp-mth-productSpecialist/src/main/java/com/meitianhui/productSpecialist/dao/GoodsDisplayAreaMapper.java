package com.meitianhui.productSpecialist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meitianhui.productSpecialist.entity.GoodsDisplayArea;

/**
 * 产品展馆数据层处理接口
 * 
 * @author 丁硕
 * @date 2016年2月26日
 */
public interface GoodsDisplayAreaMapper {
	
	/***
	 * 根据标识查询展馆信息
	 * @param display_area_id
	 * @return
	 * @author 丁硕
	 * @date   2016年4月25日
	 */
	public GoodsDisplayArea queryDisplayArea(@Param("display_area_id")String display_area_id);

	/***
	 * 查询展馆所有信息列表，并查询出相应展馆推荐产品数量
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
