package com.meitianhui.productSpecialist.controller.app;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.productSpecialist.constant.CommonRspCode;
import com.meitianhui.productSpecialist.exception.SystemException;

import net.sf.json.JSONObject;

/***
 * 提供给APP的基础控制层
 * 
 * @author 丁硕
 * @date 2016年5月28日
 */
public abstract class AppBaseController {

	private Logger logger = Logger.getLogger(AppBaseController.class);
	
	/***
	 * 公共处理请求入口
	 * @param request
	 * @param response
	 * @author 丁硕
	 * @date   2016年5月31日
	 */
	@RequestMapping(params = "service")
	public void handleRequest(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		long startTime = System.currentTimeMillis();
		String service = request.getParameter("service");
		try{
			Map<String, String> paramsMap = formatParams(request);
			Object data = operate(request, service, paramsMap);
			resultMap.put("rsp_code", CommonRspCode.RESPONSE_SUCC);
			resultMap.put("data", data == null ? new Object() : data);
		} catch (BusinessException e) {
			resultMap.put("rsp_code", CommonRspCode.RESPONSE_FAIL);
			resultMap.put("error_code", e.getMsg_code());
			resultMap.put("error_msg", e.getMessage());
			logger.error("BusinessException->operate:" + request.getParameter("service") + ", errorInfo:" + e.getMessage());
		} catch (SystemException e) {
			resultMap.put("rsp_code", CommonRspCode.RESPONSE_FAIL);
			resultMap.put("error_code", e.getMsg_code());
			resultMap.put("error_msg", e.getMessage());
			logger.error("Exception->service:" + request.getParameter("service"), e);
		} catch (Exception e) {
			resultMap.put("rsp_code", CommonRspCode.RESPONSE_FAIL);
			resultMap.put("error_code", CommonRspCode.SYSTEM_ERROR);
			resultMap.put("error_msg", CommonRspCode.MSG.get(CommonRspCode.SYSTEM_ERROR));
			logger.error("Exception->service:" + request.getParameter("service"), e);
		}
		String result = JSONObject.fromObject(resultMap).toString();
		String jsonCallback = request.getParameter("jsoncallback");
		if(StringUtils.isNotEmpty(jsonCallback)){
			result = jsonCallback + "(" + result + ")";
		}
		logger.info("requestIP: " + ActionHelper.getInstance().getIpAddr(request) + ", request:" + getParamsMap(request) + 
				", response:" + result + ", 耗时" + (System.currentTimeMillis() - startTime) + "ms");
		this.write(response, result);  //写出数据
	}
	
	/***
	 * 格式化请求的参数信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> formatParams(HttpServletRequest request) throws BusinessException, SystemException{
		String params = request.getParameter("params");
		if (null == params) {
			throw new BusinessException(CommonRspCode.MSG.get(CommonRspCode.PARAM_MISS), CommonRspCode.PARAM_MISS);
		}
		JSONObject json = JSONObject.fromObject(params);
		return (Map<String, String>) JSONObject.toBean(json, HashMap.class);
	}
	
	/***
	 * 获取请求参数
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年3月26日
	 */
	private Map<String, String> getParamsMap(HttpServletRequest request){
		Map<String, String> params = new HashMap<String, String>();
		for(String key : request.getParameterMap().keySet()){
			params.put(key, request.getParameter(key));
		}
		return params;
	}
	
	/**
	 * 返回结果
	 * 
	 * @param response
	 * @param result
	 */
	private void write(HttpServletResponse response, String result) {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			if (out != null) {
				out.close();
			}
			e.printStackTrace();
		}
	}
	
	
	/***
	 * 方法实现抽象接口
	 * @param request
	 * @param service
	 * @param paramsMap
	 * @return
	 * @throws BusinessException
	 * @author 丁硕
	 * @date   2016年3月26日
	 */
	public abstract Object operate(HttpServletRequest request, String service, Map<String, String> paramsMap) throws BusinessException, SystemException;
	
}
