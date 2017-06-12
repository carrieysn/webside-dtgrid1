package com.meitianhui.productSpecialist.controller.mobile;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.constant.DataDictConstant;
import com.meitianhui.platform.entity.Area;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.service.AreaService;
import com.meitianhui.platform.service.TeamPlatformService;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.platform.utils.openapi.OperateApiUtil;

/***
 * 报表页面
 * 
 * @author 丁硕
 * @date 2016年7月8日
 */
@Controller
@RequestMapping("/mobile/report")
public class ReportMobileController {
	
	private Logger logger = Logger.getLogger(ReportMobileController.class);
	
	@Autowired
	private TeamPlatformService teamPlatformService;
	
	@Autowired
	private AreaService areaService;
	
	/***
	 * 销售报表首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年7月8日
	 */
	@RequestMapping("salesReport")
	public String salesReport(HttpServletRequest request){
		String token = ActionHelper.getInstance().getToken(request);
		Team team = UserCache.getUser(token).getTeam();
		if(DataDictConstant.TeamType.TEAM.equals(team.getTeam_type_key())){	//如果不是公司组织，则需要进行查询判断
			team = teamPlatformService.getCompanyTeam(team.getParent_id());
		}
		Area province =  areaService.findArea(team.getProviceAreaId());
		if(DataDictConstant.TeamType.HEAD.equals(team.getTeam_type_key()) || DataDictConstant.TeamType.BRANCH.equals(team.getTeam_type_key())){  //全国
			request.setAttribute("area_type", "country");
			request.setAttribute("area_path", "");
		} else if(DataDictConstant.TeamType.PROVINCIAL.equals(team.getTeam_type_key())){
			request.setAttribute("area_type", "province");
			request.setAttribute("area_path", province.getPath());
		} else if(DataDictConstant.TeamType.CITY.equals(team.getTeam_type_key())){
			Area city = areaService.findArea(team.getCityAreaId());
			request.setAttribute("area_type", "city");
			request.setAttribute("area_path", city.getPath());
		} else if(DataDictConstant.TeamType.DISTRICT.equals(team.getTeam_type_key())){
			Area area = areaService.findArea(team.getArea_id());
			request.setAttribute("area_type", "district");
			request.setAttribute("area_path", area.getPath());
		}
		return "mobile/report/salesReport";
	}
	
	/***
	 * 查询销售报表信息
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年7月11日
	 */
	@RequestMapping("querySaleSummaryList")
	public @ResponseBody ResultVO<Object> querySaleSummaryList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			String date_type = params.get("date_type") + "";
			if("yesterday".equals(date_type)){
				params.put("begin_date", DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"));
			} else if("week".equals(date_type)){
				params.put("begin_date", DateFormatUtils.format(DateUtils.addDays(new Date(), -7), "yyyy-MM-dd"));
			} else if("last_month".equals(date_type)){
				params.put("begin_date", DateFormatUtils.format(DateUtils.addDays(new Date(), -30), "yyyy-MM-dd"));
			}
			JSONArray array = OperateApiUtil.querySaleSummaryList(params);
			resultVo.setData(array);
			resultVo.setMsg("查询销售报表信息成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "查询销售报表信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询销售报表信息失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载超级返订单
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年9月20日
	 */
	@RequestMapping("loadFgOrderStatisticsList")
	public @ResponseBody ResultVO<Object> loadFgOrderStatisticsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			JSONArray array = OperateApiUtil.loadReportDetailList(params);
			resultVo.setData(array);
			resultVo.setMsg("加载超级返订单报表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载领了么报表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载超级返订单报表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载我要批报表
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年9月20日
	 */
	@RequestMapping("loadPsOrderStatisticsList")
	public @ResponseBody ResultVO<Object> loadPsOrderStatisticsList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			JSONArray array = OperateApiUtil.queryStatisticsSalesAreaWyp(params);
			resultVo.setData(array);
			resultVo.setMsg("加载我要批订单报表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载我要批报表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载我要批订单报表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载明星榜
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年9月20日
	 */
	@RequestMapping("loadStoreRankingList")
	public @ResponseBody ResultVO<Object> loadStoreRankingList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			JSONArray array = OperateApiUtil.loadStoresRankList(params);
			resultVo.setData(array);
			resultVo.setMsg("加载明星榜成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载明星榜失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载明星榜失败！", e);
		}
		return resultVo;
	}
	
	
	
	
	//=================================2016.12.9 新改内容=================================
	
	/***
	 * 按日期统计
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年10月11日
	 */
	@RequestMapping("queryDateOrderList")
	public @ResponseBody ResultVO<JSONArray> queryDateOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			params.put("date_type", "specify_time");	//自定义时间段
			JSONArray array = OperateApiUtil.loadReportDetailList(params);
			resultVo.setData(array);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("加载城市报表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载城市报表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询按门店统计订单
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年10月20日
	 */
	@RequestMapping("queryStoresOrderList")
	public @ResponseBody ResultVO<JSONArray> queryStoresOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			params.put("date_type", "specify_time");	//自定义时间段
			JSONArray array = OperateApiUtil.loadStoresOrderList(params);
			resultVo.setData(array);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询按门店统计订单失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询按门店统计订单失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询按区域统计订单
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年10月20日
	 */
	@RequestMapping("queryAreaOrderList")
	public @ResponseBody ResultVO<JSONArray> queryAreaOrderList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			params.put("date_type", "specify_time");	//自定义时间段
			JSONArray array = OperateApiUtil.loadAreaOrderList(params);
			resultVo.setData(array);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询按区域统计订单失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询按区域统计订单失败！", e);
		}
		return resultVo;
	}
	
}