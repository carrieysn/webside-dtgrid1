package com.meitianhui.productSpecialist.controller.app;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.utils.ConfigurationHelper;
import com.meitianhui.productSpecialist.constant.CommonRspCode;
import com.meitianhui.productSpecialist.exception.SystemException;

/***
 * 提供给APP调用的财务接口
 * 
 * @author 丁硕
 * @date 2016年5月28日
 */
@Controller
@RequestMapping("/app/finanace")
public class AppFinanaceController extends AppBaseController{

	@Override
	public Object operate(HttpServletRequest request, String service, Map<String, String> paramsMap) throws BusinessException, SystemException {
		try{
			if ("finance.getAlipayInfo".equals(service)) {
				return getAlipayInfo(paramsMap);
			} else {
				throw new BusinessException(CommonRspCode.SERVICE_ERROR, CommonRspCode.MSG.get(CommonRspCode.SERVICE_ERROR));
			}
		} catch (BusinessException e) {
			throw e;
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 获取支付宝信息
	 * 
	 * @param paramMap
	 * @param result
	 * @throws BusinessException
	 */
	public Map<String, String> getAlipayInfo(Map<String, String> paramsMap) throws BusinessException, SystemException {
		try {
			Map<String, String> resultMap = new LinkedHashMap<String, String>();
			// 合作身份者ID
			resultMap.put("partner", ConfigurationHelper.getInstance().getValue("alipay.partner"));
			// 卖家账号
			resultMap.put("seller_id", ConfigurationHelper.getInstance().getValue("alipay.seller_id"));
			// 私钥
			resultMap.put("private_key", ConfigurationHelper.getInstance().getValue("alipay.private_key"));
			// 异步通知url
			resultMap.put("notify_url", ConfigurationHelper.getInstance().getValue("alipay.notify_url"));
			return resultMap;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
}
