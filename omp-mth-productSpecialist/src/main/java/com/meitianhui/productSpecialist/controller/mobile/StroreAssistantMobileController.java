package com.meitianhui.productSpecialist.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.openapi.MemberApiUtil;
import com.meitianhui.productSpecialist.constant.StoresConstants;
import com.meitianhui.productSpecialist.utils.OperateApiUtil;

/***
 * 店东助手APP相关处理控制层
 * 
 * @author 丁硕
 * @date 2017年2月14日
 */
@Controller
@RequestMapping("/mobile/stores/app")
public class StroreAssistantMobileController {

	private Log logger = LogFactory.getLog(StroreAssistantMobileController.class);
	
	/***
	 * 店东助手APP注册页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2017年2月14日
	 */
	@RequestMapping("register")
	public String register(HttpServletRequest request){
		request.setAttribute("businessTypeList", StoresConstants.BUSINESS_TYPE_LIST);
		return "mobile/stores/app/register";
	}
	
	/***
	 * 提交注册
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2017年2月14日
	 */
	@RequestMapping("submitReg")
	public @ResponseBody ResultVO<String> submitReg(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			if("HYLX_02".equals(params.get("stores_type_key")) || "HYLX_04".equals(params.get("stores_type_key"))){	//注册门店
				OperateApiUtil.registerStore(params);
			} else{
				throw new PlatformApiException("未指定的合作意愿！");
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "提交注册新店审核失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("提交注册新店审核失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 注册成功页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2017年2月14日
	 */
	@RequestMapping("regSuccess")
	public String regSuccess(HttpServletRequest request){
		return "mobile/stores/app/regSuccess";
	}
	
	/***
	 * 检测门店手机号相关信息
	 * @param request
	 * @param mobile
	 * @return
	 * @author 丁硕
	 * @date   2016年12月6日
	 */
	@RequestMapping("checkMobileInfo")
	public @ResponseBody ResultVO<String> checkMobileInfo(HttpServletRequest request, @RequestParam(required=true)String mobile){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			//1、根据手机号查询会员信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			params.put("member_type_key", CommonConstant.member_type_stores);
			String stores_id = "";
			try{
				String storesInfo = MemberApiUtil.findMemberInfoByMobile(params);
				stores_id = JSONObject.parseObject(storesInfo).getString("stores_id");
			} catch(PlatformApiException e){
				logger.error(e);
			}
			resultVo.setData(StringUtils.isEmpty(stores_id) ? "no" : "yes");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "检测联系电话失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("检测联系电话失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 检查对应的业务员手机号是否存在
	 * @param request
	 * @param mobile
	 * @return
	 * @author 丁硕
	 * @date   2017年2月14日
	 */
	@RequestMapping("checkStoresAssistantInfo")
	public @ResponseBody ResultVO<String> checkBusinessDeveloperInfo(HttpServletRequest request, @RequestParam(required=true)String business_developer){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			//1、根据手机号查询会员信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("contact_tel", business_developer);
			Map<String,Object> map = MemberApiUtil.storesAssistantDetailFind(params);
			resultVo.setData((map == null || map.isEmpty()) ? "no" : "yes");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(PlatformApiException e){
			resultVo.setMsg("业务员手机不存在！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("检测业务员手机号失败！", e);
		} catch(Exception e){
			resultVo.setMsg("检测业务员手机号失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("检测业务员手机号失败！", e);
		}
		return resultVo;
	}
}
