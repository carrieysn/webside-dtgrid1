package com.meitianhui.productSpecialist.controller.app;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meitianhui.platform.utils.ConfigurationHelper;
import com.meitianhui.productSpecialist.constant.OrderConstant;
import com.meitianhui.productSpecialist.service.PsOrderService;
import com.meitianhui.productSpecialist.utils.AliPayUtil;

import jxl.common.Logger;
import net.sf.json.JSONObject;

/***
 * 支付异步结果通知控制层
 * 
 * @author 丁硕
 * @date 2016年5月31日
 */
@Controller
@RequestMapping("notify")
public class PayNotifyControler {

	private Logger logger = Logger.getLogger(PayNotifyControler.class);
	
	private final static String ALIPAY_PUBLIC_KEY = ConfigurationHelper.getInstance().getValue("alipay.public_key");	//支付宝公钥 
	private final static String ALIPAY_PARTNER = ConfigurationHelper.getInstance().getValue("alipay.partner");	//支付宝合作者id
	
	@Autowired
	private PsOrderService psOrderService;
	
	/***
	 * 支付
	 * 
	 * @param request
	 * @param response
	 * @author 丁硕
	 * @date 2016年5月31日
	 */
	@RequestMapping("alipay")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
		// 返回的结果信息
		String returnMsg = "success";
		Map<String, Object> params = new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				params.put(name, StringUtils.join(values, ","));
			}
			// 打印参数
			logger.info("aplipay return data：" + params.toString());
			
			// 交易状态
			String trade_status = (String) params.get("trade_status");
			// 获取支付宝的通知返回参数
			if (verify(params)) {
				// 校验成功验证成功
				logger.info("aplipay verify success");
				Map<String, String> resultMap = new HashMap<String, String>();
				String transaction_status = OrderConstant.TRANSACTION_STATUS_CONFIRMED;
				// 是否需要记录结果
				boolean flag = true;
				if (trade_status.equals("WAIT_BUYER_PAY")) {
					// 等待买家付款
					flag = false;
				} else if (trade_status.equals("TRADE_CLOSED")) {
					// 交易取消
					transaction_status = OrderConstant.TRANSACTION_STATUS_ERROR;
				}
				/** 交易完成,处理对应的业务逻辑 **/
				if (flag) {
					// 商户订单号
					logger.info("支付异步结果通知:调用交易确认接口");
					String transaction_no = (String) params.get("out_trade_no");
					resultMap.put("transaction_status", transaction_status);
					resultMap.put("transaction_no", transaction_no);
					resultMap.put("transaction_body", JSONObject.fromObject(params).toString());
					psOrderService.confirmOrder(resultMap);
				}
			} else {
				logger.info("consumer aplipay verify fail");
			}
		} catch (Exception e) {
			returnMsg = "fail";
			logger.error("接受支付宝异步通知异常," + e.getMessage());
		} finally {
			out.println(returnMsg);
		}
	}
	
	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	private boolean verify(Map<String, Object> params) {
		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "true";
		if (params.get("notify_id") != null) {
			String notify_id = params.get("notify_id").toString();
			responseTxt = AliPayUtil.verifyResponse(notify_id, ALIPAY_PARTNER);
		}
		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign").toString();
		}
		boolean isSign = AliPayUtil.getSignVeryfy(params, ALIPAY_PUBLIC_KEY, sign);
		if (isSign && responseTxt.equals("true")) {
			return true;
		} else {
			logger.info("responseTxt:" + responseTxt + ",isSign:" + isSign);
			return false;
		}
	}

}
