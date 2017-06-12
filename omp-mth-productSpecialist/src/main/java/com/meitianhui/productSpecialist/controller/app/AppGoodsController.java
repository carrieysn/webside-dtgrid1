package com.meitianhui.productSpecialist.controller.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.productSpecialist.constant.CommonRspCode;
import com.meitianhui.productSpecialist.constant.OrderRspCode;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.exception.SystemException;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.utils.ValidateUtil;

/***
 * 提供给APP端调用的订单接口
 * 
 * @author 丁硕
 * @date 2016年5月28日
 */
@Controller
@RequestMapping("app/goods")
public class AppGoodsController extends AppBaseController{

	@Autowired
	private GoodsService goodsService; 
	
	@Override
	public Object operate(HttpServletRequest request, String service, Map<String, String> paramsMap) throws BusinessException, SystemException {
		try{
			if ("goods.findGoodsDetail".equals(service)) {	//根据ID查询商品详情
				return findGoods(paramsMap);
			} else {
				throw new BusinessException(CommonRspCode.SERVICE_ERROR, CommonRspCode.MSG.get(CommonRspCode.SERVICE_ERROR));
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	private GoodsSellExt findGoods(Map<String, String> paramsMap) throws BusinessException, SystemException{
		ValidateUtil.validateParams(paramsMap, new String[]{"goods_id"});
		String goods_id = paramsMap.get("goods_id");
		GoodsSellExt goods = null;
		try {
			goods = goodsService.queryOneGoods(goods_id);
		} catch (PlatformApiException e) {
			throw new SystemException(e);
		}
		if(goods == null){
			throw new BusinessException(CommonRspCode.MSG.get(OrderRspCode.GOODS_NOT_FOUND), OrderRspCode.GOODS_NOT_FOUND);
		}
		return goods;
		
	}

}
