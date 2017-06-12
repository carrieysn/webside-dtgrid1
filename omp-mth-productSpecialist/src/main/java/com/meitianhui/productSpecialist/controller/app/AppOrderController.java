package com.meitianhui.productSpecialist.controller.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.productSpecialist.constant.CommonRspCode;
import com.meitianhui.productSpecialist.exception.SystemException;
import com.meitianhui.productSpecialist.service.PsOrderService;

/***
 * 提供给APP端调用的订单接口
 * 
 * @author 丁硕
 * @date 2016年5月28日
 */
@Controller
@RequestMapping("app/order")
public class AppOrderController extends AppBaseController{

	@Autowired
	private PsOrderService psOrderService;
	
	@Override
	public Object operate(HttpServletRequest request, String service, Map<String, String> paramsMap) throws BusinessException, SystemException {
		try{
			String token = ActionHelper.getInstance().getToken(request);
			if ("order.orderCreate".equals(service)) {
				return psOrderService.createOrder(paramsMap, token);
			} else {
				throw new BusinessException(CommonRspCode.SERVICE_ERROR, CommonRspCode.MSG.get(CommonRspCode.SERVICE_ERROR));
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
}
