package com.meitianhui.productSpecialist.controller.mobile;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.productSpecialist.utils.OperateApiUtil;

import net.sf.json.JSONObject;

/***
 * 移动端统计分析处理控制层
 * 
 * @author 丁硕
 * @date 2016年7月11日
 */
@Controller
@RequestMapping("/mobile/statistics")
public class StatisticsMobileController {
	
	private Logger logger = Logger.getLogger(StatisticsMobileController.class);
	
	/***
	 * 跳转进入统计分析页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年7月11日
	 */
	@RequestMapping("analysis")
	public String analysis(HttpServletRequest request){
		return "mobile/statistics/analysis";
	}
	
	/***
	 * 根据数据来源查询对应的交易概况
	 * @param request
	 * @param data_source_type 数据来源
	 * @param params	查询条件
	 * @return
	 * @author 丁硕
	 * @date   2016年7月11日
	 */
	@RequestMapping("queryTradeOverview")
	public @ResponseBody ResultVO<Object> queryTradeOverview(HttpServletRequest request, @RequestParam String data_source_type, 
			@RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			Date start_date = DateUtils.addDays(new Date(), Integer.parseInt(params.get("date_type") + ""));
			params.put("start_date", DateFormatUtils.format(start_date, "yyyy-MM-dd"));
			params.put("end_date", DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"));	//前一天
			JSONObject data = OperateApiUtil.queryTradeOverview(params);
			resultVo.setData(data);
			resultVo.setMsg("查询交易概况信息成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "查询交易概况信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询交易概况信息失败！", e);
		}
		return resultVo;
	}
	
	
}
