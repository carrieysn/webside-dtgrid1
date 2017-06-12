package com.meitianhui.productSpecialist.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.productSpecialist.utils.OperateApiUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 即时播报视图控制层
 * 
 * @author 丁硕
 * @date 2016年11月8日
 */
@Controller
@RequestMapping("/mobile/broadcast/history")
public class BroadcastHistoryMobileController {

	private Log logger = LogFactory.getLog(BroadcastHistoryMobileController.class);
	
	/***
	 * 首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		return "mobile/broadcast/broadcastHistory";
	}
	
	/**
	 * 加载红包抽奖统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadGiftLuckyHistoryTotal")
	public @ResponseBody ResultVO<JSONObject> loadGiftLuckyHistoryTotal(HttpServletRequest request){
		ResultVO<JSONObject> resultVo = new ResultVO<JSONObject>();
		try{
			Map<String, Object> params = this.getParamsByType(request);
			JSONObject json = OperateApiUtil.querySalesGiftLuckyTotalHistory(params);
			resultVo.setData(json);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载红包抽奖统计失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载红包抽奖统计失败！");
		}
		return resultVo;
	}
	
	/***
	 * 加载历史订单统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadHistoryOrderTotal")
	public @ResponseBody ResultVO<JSONObject> loadHistoryOrderTotal(HttpServletRequest request){
		ResultVO<JSONObject> resultVo = new ResultVO<JSONObject>();
		try{
			Map<String, Object> params = this.getParamsByType(request);
			JSONObject json = OperateApiUtil.querySalesCjfTotalHistory(params);
			resultVo.setData(json);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载今日订单统计失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载今日订单统计失败！");
		}
		return resultVo;
	}
	
	/***
	 * 加载历史订单流量
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadHistoryOrderTraffic")
	public @ResponseBody ResultVO<JSONArray> loadHistoryOrderTraffic(HttpServletRequest request){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			Map<String, Object> params = this.getParamsByType(request);
			JSONArray array = OperateApiUtil.querySalesCjfTrafficHistory(params);
			resultVo.setData(array);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载实时订单流量图失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载实时订单流量图失败！");
		}
		return resultVo;
	}
	
	/***
	 * 加载历史商品分类统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadHistoryGoodsCateoryStatistics")
	public @ResponseBody ResultVO<Map<String, Object>> loadHistoryGoodsCateoryStatistics(HttpServletRequest request){
		ResultVO<Map<String, Object>> resultVo = new ResultVO<Map<String, Object>>();
		try{
			Map<String, Object> params = this.getParamsByType(request);
			JSONArray array = OperateApiUtil.querySalesCjfGoodsHistory(params);
			//计算总数量
			int total = 0;
			for(int i = 0;i < array.size();i++){
				JSONObject json = array.getJSONObject(i);
				total += json.getInt("goods_qty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", array);
			map.put("total_qty", total);
			resultVo.setData(map);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载商品分类统计失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载商品分类统计失败！");
		}
		return resultVo;
	}
	
	/***
	 * 加载历史参与会员数
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadHistoryOrderMemberNum")
	public @ResponseBody ResultVO<JSONArray> loadHistoryOrderMemberNum(HttpServletRequest request){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			Map<String, Object> params = this.getParamsByType(request);
			JSONArray data = OperateApiUtil.querySalesCjfMemberNumHistory(params);
			resultVo.setData(data);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载参与会员数失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载参与会员数失败！");
		}
		return resultVo;
	}
	
	/***
	 * 根据Type获取对应的接口请求参数
	 * @param type
	 * @return
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	private Map<String, Object> getParamsByType(HttpServletRequest request){
		String type = request.getParameter("type");
		Map<String, Object> params = new HashMap<String, Object>();
		if("2016.10.18".equals(type)){	//10.18号活动
			params.put("begin_date", "2016-10-18");
			params.put("end_date", "2016-10-18");
		} else if("2016.11.11".equals(type)){	//双十一活动11.08 - 11.11
			params.put("begin_date", "2016-11-08");
			params.put("end_date", "2016-11-11");
		} else if("2017.03.18".equals(type)) {	//3.18
			params.put("begin_date", "2017-03-18");
			params.put("end_date", "2017-03-18");
		} else if("2017.05.20".equals(type)) {	//3.18
			params.put("begin_date", "2017-05-20");
			params.put("end_date", "2017-05-20");
		}
		return params;
	}
	
	
}
