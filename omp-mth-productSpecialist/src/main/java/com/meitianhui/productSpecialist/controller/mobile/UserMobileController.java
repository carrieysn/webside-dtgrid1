package com.meitianhui.productSpecialist.controller.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.platform.utils.openapi.order.PsOrderApiUtil;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.entity.GoodsUserStatistics;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;

/***
 * 用户管理页面
 * 
 * @author 丁硕
 * @date 2016年5月11日
 */
@Controller
@RequestMapping("/mobile/user")
public class UserMobileController {
	
	private Logger logger = Logger.getLogger(UserMobileController.class);
	
	@Autowired
	private GoodsUserStatisticsService goodsUserStatisticsService;
	
	@Autowired
	private GoodsService goodsService;
	
	/***
	 * 用户管理中心首页
	 * @param request
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("center")
	public String center(HttpServletRequest request){
		String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
		GoodsUserStatistics statistics = goodsUserStatisticsService.queryUserStatistics(user_id);
		request.setAttribute("statistics", statistics);
		return "mobile/user/center";
	}
	
	/***
	 * 用户相关的商品列表
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("goodsList")
	public String goodsList(HttpServletRequest request){
		String statistics_type = request.getParameter("statistics_type");
		request.setAttribute("statistics_type", statistics_type);
		return "mobile/user/goodsList";
	}
	
	/***
	 * 查询用户相关的商品分页列表
	 * @param request
	 * @param statistics_type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("queryGoodsListPage")
	public @ResponseBody ResultVO<Page> queryGoodsListPage(HttpServletRequest request, @RequestParam(required=true) String statistics_type,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Map<String,  Object> params = new HashMap<String, Object>();
			params.put("user_id", ActionHelper.getInstance().getSessionUser(request).getUser_id());
			params.put("statistics_type", statistics_type);
			Page page = goodsService.queryUserProductList(pageNum, pageSize, params);
			//TODO 到时候些断代码删除掉，临时从开放平台获取数据
			if(page != null && page.getResult().size() > 0){
				List<Object> result = page.getResult();
				for(Object o : result){
					GoodsSellExt goods = (GoodsSellExt) o;
					goodsService.loadGoodsSellExtInfo(goods);
				}
			}
			resultVo.setData(page);
			resultVo.setMsg("根据条件查询商品分页列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg("根据条件查询商品分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error(e);
		}
		return resultVo;
	}
	
	/**
	 * 加载我的卖单数量
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("loadTeamOrderCount")
	public @ResponseBody ResultVO<Long> loadTeamOrderCount(HttpServletRequest request){
		ResultVO<Long> resultVo = new ResultVO<Long>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			//TODO 先屏蔽，让查询出来的数据为空
			params.put("status", "test");
			Page page = PsOrderApiUtil.queryPsOrderListPage(1, 1, params);
			resultVo.setData(page.getTotal());
			resultVo.setMsg("加载与组织相关商品订单总数成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("加载与组织相关商品订单总数失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载与组织相关商品订单总数失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 跳转进入用户设置页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月18日
	 */
	@RequestMapping("site")
	public String site(HttpServletRequest request){
		return "mobile/user/site";
	}
	
	/***
	 * 用户登出
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		
		return "mobile/user/logout";
	}
	
	/***
	 * 跳转进入未认证页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月17日
	 */
	@RequestMapping("unauthorized")
	public String unauthorized(HttpServletRequest request){
		return "mobile/user/unauthorized";
	}
	
	/***
	 * 跳转进入修改密码页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年7月8日
	 */
	@RequestMapping("modifyPwd")
	public String modifyPwd(HttpServletRequest request){
		return "mobile/user/modifyPwd";
	}
}
