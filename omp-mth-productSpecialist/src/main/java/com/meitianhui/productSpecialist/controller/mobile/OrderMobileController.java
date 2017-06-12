package com.meitianhui.productSpecialist.controller.mobile;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.openapi.order.PsOrderApiUtil;
import com.meitianhui.productSpecialist.utils.OperateApiUtil;

import net.sf.json.JSONObject;

/***
 * APP订单处理控制层
 * 
 * @author 丁硕
 * @date 2016年5月11日
 */
@Controller
@RequestMapping("/mobile/order")
public class OrderMobileController {
	
	private Logger logger = Logger.getLogger(OrderMobileController.class);
	
	/***
	 * 跳转进入我的卖单页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("sellorder")
	public String sellOrder(HttpServletRequest request){
		return "mobile/order/sellorderList";
	}
	
	/**
	 * 加载我的卖单分页列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年5月16日
	 */
	@RequestMapping("loadSellOrderListPage")
	public @ResponseBody ResultVO<Page> loadSellOrderListPage(HttpServletRequest request, @RequestParam Map<String, Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			//TODO 先屏蔽，让查询出来的数据为空
			params.put("status", "test");
			Page page = PsOrderApiUtil.queryPsOrderListPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载我的卖单分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载我的卖单列表分页失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 更新订单信息
	 * @param request
	 * @param orderParams
	 * @return
	 * @author 丁硕
	 * @date   2016年4月7日
	 */
	@RequestMapping("editOrder")
	public @ResponseBody ResultVO<String> editOrder(HttpServletRequest request,@RequestParam Map<String, Object> orderParams){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			PsOrderApiUtil.editPsOrder(orderParams);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
			logger.info("更新订单信息【  " + orderParams + " 】成功！");
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "更新订单信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("更新订单信息失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 订单退款，退款后，订单为取消状态
	 * @param request
	 * @param order_no
	 * @param amount
	 * @param remark
	 * @return
	 * @author 丁硕
	 * @date   2016年5月17日
	 */
	@RequestMapping("refundOrder")
	public @ResponseBody ResultVO<String> refundOrder(HttpServletRequest request, @RequestParam(required=true) String order_no,
			@RequestParam(required=true) BigDecimal amount, @RequestParam String remark){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			PsOrderApiUtil.refundPsOrder(order_no, amount, remark);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "订单退款失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("订单退款失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 订单发货，填写对应的发货信息
	 * @param request
	 * @param order_id
	 * @param logistics
	 * @return
	 * @author 丁硕
	 * @date   2016年8月1日
	 */
	@RequestMapping("deliveryOrder")
	public @ResponseBody ResultVO<String> deliveryOrder(HttpServletRequest request, @RequestParam(required=true) String order_id, @RequestParam(required=true) String logistics){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			PsOrderApiUtil.deliveryPsOrder(order_id, logistics);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "订单发货失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("订单发货失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载订单统计信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年8月11日
	 */
	@RequestMapping("loadOrderDashBoard")
	public @ResponseBody ResultVO<Object> loadOrderDashBoard(HttpServletRequest request){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			JSONObject json = OperateApiUtil.loadOrderDashBoard();
			resultVo.setData(json);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(PlatformApiException e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载订单统计信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error(e);
		}
		return resultVo;
	}
	
}
