package com.meitianhui.productSpecialist.controller.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.constant.DataDictConstant;
import com.meitianhui.platform.entity.Area;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.service.AreaService;
import com.meitianhui.platform.service.TeamPlatformService;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.platform.utils.openapi.FinanceApiUtil;
import com.meitianhui.platform.utils.openapi.MemberApiUtil;
import com.meitianhui.productSpecialist.constant.StoresConstants;
import com.meitianhui.productSpecialist.service.StoresService;
import com.meitianhui.productSpecialist.utils.OperateApiUtil;

import net.sf.json.JSONArray;

/***
 * 门店查询控制层
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
@Controller
@RequestMapping("/mobile/stores")
public class StoresMobileController {

	private Logger logger = Logger.getLogger(StoresMobileController.class);
	
	@Autowired
	private StoresService storesService;
	
	@Autowired
	private TeamPlatformService teamPlatformService;
	
	@Autowired
	private AreaService areaService;
	
	/***
	 * 门店查询首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		try{
			User user = UserCache.getUser(ActionHelper.getInstance().getToken(request));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("contact_tel", StringUtils.isEmpty(user.getMobile()) ? "-1" : user.getMobile());
			Map<String,Object> map = MemberApiUtil.storesAssistantDetailFind(params);
			if(map != null && !map.isEmpty()){
				request.setAttribute("assistant_id", map.get("assistant_id"));
			}
		} catch(PlatformApiException e){
			logger.warn("门店查询页面出错，" + e.getMessage());
		} catch(Exception e){
			logger.error("门店查询首页失败！", e);
		}
		return "mobile/stores/storesList";
	}
	
	/***
	 * 根据条件查询门店分页列表信息
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	@RequestMapping("queryStoresListPage")
	public @ResponseBody ResultVO<Page> queryStoresListPage(HttpServletRequest request, @RequestParam(required=true) Map<String, Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Page page = MemberApiUtil.findStoreListByAssistantPage(params, pageNum, pageSize);
			resultVo.setData(page);
			resultVo.setMsg("询门店分页列表信息成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("询门店分页列表信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("询门店分页列表信息失败！", e);
		}
		return resultVo;
	} 
	
	/***
	 * 跳转进入门店详情页面
	 * @param request
	 * @param stores_id
	 * @return
	 * @author 丁硕
	 * @date   2016年11月1日
	 */
	@RequestMapping("detail")
	public String storesDetail(HttpServletRequest request, @RequestParam(required=true) String stores_id){
		try{
			Map<String, Object> stores = storesService.queryOneStores(stores_id);
			request.setAttribute("stores", stores);
			//查询会员资产
			Map<String, Object> storesAsset = FinanceApiUtil.queryMemberAsset(stores_id);
			request.setAttribute("storesAsset", storesAsset);
		} catch(Exception e){
			logger.error("查询门店详情失败！", e);
		}
		return "mobile/stores/storesDetail";
	}
	
	@RequestMapping("queryStoresConsumerList")
	public @ResponseBody ResultVO<List<Map<String, Object>>> queryStoresConsumerList(HttpServletRequest request, @RequestParam(required=true) String stores_id){
		ResultVO<List<Map<String, Object>>> resultVo = new ResultVO<List<Map<String, Object>>>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("stores_id", stores_id);
			List<Map<String, Object>> data = MemberApiUtil.storesRelConsumerList(params);
			resultVo.setData(data);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "查询会员列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询会员列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询门店交易统计
	 * @param request
	 * @param stores_id
	 * @return
	 * @author 丁硕
	 * @date   2016年11月2日
	 */
	@RequestMapping("queryStatisticsTransStore")
	public @ResponseBody ResultVO<JSONArray> queryStatisticsTransStore(HttpServletRequest request, @RequestParam(required=true) String stores_id){
		ResultVO<JSONArray> resultVo = new ResultVO<JSONArray>();
		try{
			JSONArray array = OperateApiUtil.queryStatisticsTransStore(stores_id); 
			resultVo.setData(array);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "查询门店交易统计失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询门店交易统计失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 商户注册
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年7月7日
	 */
	@RequestMapping("register")
	public String register(HttpServletRequest request){
		// 查询加盟店的商业类型
		try {
			String token = ActionHelper.getInstance().getToken(request);
			Team team = UserCache.getUser(token).getTeam();
			if(DataDictConstant.TeamType.TEAM.equals(team.getTeam_type_key())){	//如果登录人是属于部门下的，则需查询对应的公司信息
				team = teamPlatformService.getCompanyTeam(team.getParent_id());
			}
			Area area = areaService.findArea(team.getArea_id());
			request.setAttribute("_path_", area.getPath());
			//联盟商所有的门店类型
			request.setAttribute("businessTypeList", StoresConstants.BUSINESS_TYPE_LIST);
		} catch (Exception e) {
			logger.error("跳转进入商户注册页面查询相关数据失败！", e);
		}
		return "mobile/stores/register";
	}
	
	/***
	 * 提交注册
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年7月8日
	 */
	@RequestMapping("submitReg")
	public @ResponseBody ResultVO<String> submitReg(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			params.put("token", ActionHelper.getInstance().getToken(request));
			if("HYLX_02".equals(params.get("stores_type_key")) || "HYLX_04".equals(params.get("stores_type_key"))){	//注册门店
				OperateApiUtil.registerStore(params);
			} else{
				throw new PlatformApiException("未指定的合作意愿！");
			}
			resultVo.setMsg("提交商户注册审核成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "提交商户注册审核失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("提交商户注册审核失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 商户注册成功页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年7月8日
	 */
	@RequestMapping("regSuccess")
	public String regSuccess(HttpServletRequest request){
		return "mobile/stores/regSuccess";
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
			if(StringUtils.isEmpty(stores_id)){	//手机号码不存在，可注册
				resultVo.setData("1");
			} else{	//存在，查询对应的领了么订单数据
				params.clear();
				params.put("member_id", stores_id);
				params.put("member_type_key", CommonConstant.member_type_stores);
				JSONObject json = com.meitianhui.platform.utils.openapi.OperateApiUtil.queryMemberFgOrderCount(params);
				int orderCount = json.getIntValue("order_qty");
				resultVo.setData(orderCount > 0 ? "2" : "3");
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "检测联系电话失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("检测联系电话失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 搜索查询供应商列表
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年12月6日
	 */
	@RequestMapping("querySupplierList")
	public @ResponseBody ResultVO<Object> querySupplierList(HttpServletRequest request, @RequestParam Map<String, Object> params){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			Page page = storesService.querySupplierListPage(1, 15, params);
			resultVo.setData(page.getResult());
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询供应商列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询供应商列表失败！", e);
		}
		return resultVo;
	}
	
}
