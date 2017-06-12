package com.meitianhui.productSpecialist.controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
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
@RequestMapping("/mobile/broadcast")
public class BroadcastMobileController {

	private Log logger = LogFactory.getLog(BroadcastMobileController.class);
	
	/***
	 * 首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		request.setAttribute("now", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return "mobile/broadcast/broadcast";
	}
	
	/**
	 * 加载红包抽奖统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadGiftLuckyTotal")
	public @ResponseBody ResultVO<JSONObject> loadGiftLuckyTotal(HttpServletRequest request){
		ResultVO<JSONObject> resultVo = new ResultVO<JSONObject>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			JSONObject json = OperateApiUtil.querySalesGiftLuckyTotal(params);
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
	 * 加载今日订单统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadTodayOrderTotal")
	public @ResponseBody ResultVO<JSONObject> loadTodayOrderTotal(HttpServletRequest request){
		ResultVO<JSONObject> resultVo = new ResultVO<JSONObject>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			JSONObject json = OperateApiUtil.querySalesCjfTotal(params);
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
	 * 加载实时订单流量
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadOrderTraffic")
	public @ResponseBody ResultVO<JSONArray> loadOrderTraffic(HttpServletRequest request){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			JSONArray array = OperateApiUtil.querySalesCjfTraffic(params);
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
	 * 加载商品分类统计
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadGoodsCateoryStatistics")
	public @ResponseBody ResultVO<Map<String, Object>> loadGoodsCateoryStatistics(HttpServletRequest request){
		ResultVO<Map<String, Object>> resultVo = new ResultVO<Map<String, Object>>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			JSONArray array = OperateApiUtil.querySalesCjfGoods(params);
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
	 * 加载参与会员数
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	@RequestMapping("loadOrderMemberNum")
	public @ResponseBody ResultVO<JSONArray> loadOrderMemberNum(HttpServletRequest request){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			JSONArray data = OperateApiUtil.querySalesCjfMemberNum(params);
			resultVo.setData(data);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("加载参与会员数失败！", e);
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			resultVo.setMsg("加载参与会员数失败！");
		}
		return resultVo;
	}
	
	
}
