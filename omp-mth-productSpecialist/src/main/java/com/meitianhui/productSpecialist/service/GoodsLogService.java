package com.meitianhui.productSpecialist.service;

/***
 * 产品操作日志逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年3月3日
 */
public interface GoodsLogService {

	/***
	 * 保存产品操作日志
	 * @param token
	 * @param goods_id
	 * @param category
	 * @param event
	 * @author 丁硕
	 * @date   2016年3月3日
	 */
	public void saveGoodsLog(String token, String goods_id, String category, String event);
}
