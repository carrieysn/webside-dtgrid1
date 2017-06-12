package com.meitianhui.productSpecialist.dao;

import com.meitianhui.productSpecialist.entity.GoodsLog;

/***
 * 产品日志数据数据接口
 * 
 * @author 丁硕
 * @date 2016年3月3日
 */
public interface GoodsLogMapper {

	/***
	 * 保存产品操作日志信息
	 * @param goodsLog
	 * @author 丁硕
	 * @date   2016年3月3日
	 */
	public void saveGoodsLog(GoodsLog goodsLog);
}
