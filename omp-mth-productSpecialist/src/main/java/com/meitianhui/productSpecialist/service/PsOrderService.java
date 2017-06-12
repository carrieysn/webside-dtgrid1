package com.meitianhui.productSpecialist.service;

import java.util.Map;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.productSpecialist.exception.SystemException;

/***
 * 商品订单逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年5月31日
 */
public interface PsOrderService {

	/***
	 * 创建订单信息
	 * @param paramsMap
	 * @return
	 * @author 丁硕
	 * @date   2016年5月31日
	 */
	public String createOrder(Map<String, String> paramsMap, String token) throws BusinessException, SystemException;
	
	/***
	 * 订单确认
	 * @param paramsMap
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public String confirmOrder(Map<String, String> paramsMap) throws BusinessException, SystemException;
}
