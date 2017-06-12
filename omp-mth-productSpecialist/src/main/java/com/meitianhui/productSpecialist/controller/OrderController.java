package com.meitianhui.productSpecialist.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.platform.utils.PlatformApiUtil;
import com.meitianhui.platform.utils.openapi.order.PsOrderApiUtil;

/***
 * 订单处理控制层
 * 
 * @author 丁硕
 * @date 2016年3月24日
 */
@Controller
@RequestMapping("order")
public class OrderController {

	private Logger logger = Logger.getLogger(OrderController.class);
	
	/***
	 * 加载与组织相关商品订单总数
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月6日
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
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载订单数量失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载与组织相关商品订单总数失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载与组织相关的商品订单分页信息
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年5月6日
	 */
	@RequestMapping("loadTeamOrderListPage")
	public @ResponseBody ResultVO<Page> loadTeamOrderListPage(HttpServletRequest request, @RequestParam Map<String, Object> params,
			@RequestParam int pageNum, @RequestParam int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			//TODO 先屏蔽，让查询出来的数据为空
			params.put("status", "test");
			Page page = PsOrderApiUtil.queryPsOrderListPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载商品订单分页信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载商品订单分页信息失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 更新订单状态
	 * @param request
	 * @param order_id
	 * @param status
	 * @return
	 * @author 丁硕
	 * @date   2016年3月24日
	 */
	@RequestMapping("updateOrderStatus")
	public @ResponseBody ResultVO<String> updateOrderStatus(HttpServletRequest request, @RequestParam(required=true) String order_id,
			@RequestParam(required=true) String status){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			boolean result = PsOrderApiUtil.updatePsOrderStatus(order_id, status);
			if(result){
				resultVo.setMsg("更新订单状态成功！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
				User user = UserCache.getUser(ActionHelper.getInstance().getToken(request));
				logger.info("用户：" + (user == null ? null : user.getUser_id()) + " 更新订单状态【  " + status + " 】成功！order_id：" + order_id);
			} else{
				resultVo.setMsg("更新订单状态失败！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			}
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "更新订单状态失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("更新订单状态失败", e);
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
			resultVo.setMsg("订单退款成功！");
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
			resultVo.setMsg("订单发货成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "订单发货失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("订单发货失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 导出订单列表
	 * @param request
	 * @author 丁硕
	 * @date   2016年4月6日
	 */
	@RequestMapping("exportOrder")
	public String exportOrder(HttpServletRequest request, HttpServletResponse response){
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			String order_like = request.getParameter("order_like");
			if(StringUtils.isNotEmpty(order_like)){
				params.put("order_like", new String(order_like.getBytes("ISO-8859-1"),"UTF-8"));
			}
 			//TODO 先屏蔽，让查询出来的数据为空
			params.put("status", "test");
			long oldTime = System.currentTimeMillis();
			JSONArray dataset = PsOrderApiUtil.queryPsOrderList(params);
			logger.info("查询订单导出数据耗时：" + (System.currentTimeMillis() - oldTime) + " ms，共 " + dataset.size() + " 条数据，参数信息：" + params);
			if(dataset != null && dataset.size() > 0){
				String fileName = "我要批" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "订单.xlsx";
				response.setContentType("octets/stream");
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
				OutputStream out = response.getOutputStream();
				try{
					String[] headers = new String[]{"订单编号","订单号", "数量", "金额", "日期", "配送地址", "联系人", "联系电话","物流公司","物流单号", "订单状态", "用户备注", "后台备注"};
					String[] keys = new String[]{"order_id","order_no", "item_num", "total_fee", "order_date", "delivery_address", 
							"contact_person", "contact_tel","logistics_company","logistics_number","status", "remark", "biz_remark"};
					this.exportExcel(headers, keys, dataset, out);
				} finally{
					out.close();
				}
				logger.info("导出订单共耗时：" + (System.currentTimeMillis() - oldTime) + " ms");
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出订单列表失败！", e);
		}
		return null;
	}	
	
	/***
	 * 导出excel
	 * @param headers
	 * @param keys
	 * @param dataset
	 * @param os
	 * @author 丁硕
	 * @date   2016年4月6日
	 */
	private void exportExcel(String[] headers, String[] keys, JSONArray dataset, OutputStream os){
		 Workbook workbook = new SXSSFWorkbook(1000);
		 Sheet sheet = workbook.createSheet();
		 //产生表格标题行
		 Row row = sheet.createRow(0);
		 for(int i = 0; i < headers.length; i++) {
			 Cell cell = row.createCell(i);
			 HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			 cell.setCellValue(text);
			 if(i == 5){
				 sheet.setColumnWidth(i, 15 * 1000);
			 } else{
				 sheet.setColumnWidth(i, 5 * 1000);
			 }
		 }
		 //设置宽度
		 for(int i = 0;i < dataset.size();i++){
			 JSONObject data = dataset.getJSONObject(i);
			 row = sheet.createRow(i + 1);
			 for(int index = 0 ;index < keys.length;index++){
				 String key = keys[index];
				 Cell cell = row.createCell(index);
				 Object value = data.get(key);
				 String textValue = "";	//文本值
				 if("logistics_company".equals(key)){	//物流公司
					 String logistics = PlatformApiUtil.formatStr(data.get("logistics"));
					 if(StringUtils.isNotEmpty(logistics)){
						 JSONObject json = JSON.parseObject(logistics);
						 if(json.containsKey("company")){
							 textValue = json.getString("company");
						 }
					 }
				 } else if("logistics_number".equals(key)){	//物流单号
					 String logistics = PlatformApiUtil.formatStr(data.get("logistics"));
					 if(StringUtils.isNotEmpty(logistics + "")){
						 JSONObject json = JSON.parseObject(logistics);
						 if(json.containsKey("number")){
							 textValue = json.getString("number");
						 }
					 }
				 } else if(value == null || "".equals(value)){
					 textValue = "";
				 }else if("status".equals(key)){
					 if("non_paid".equals(value)){
						 textValue = "未支付";
					 } else if("processing".equals(value)){
						 textValue = "待确认";
					 } else if("confirmed".equals(value)){
						 textValue = "已确认";
					 } else if("delivered".equals(value)){
						 textValue = "已发货";
					 } else if("payed".equals(value)){
						 textValue = "已付款";
					 } else if("received".equals(value)){
						 textValue = "已完成";
					 } else if("activing".equals(value)){
						 textValue = "团购中";
					 } else if("cancelled".equals(value)){
						 textValue = "已取消";
					 } else{
						 textValue = "未知状态";
					 }
				 } else{
					 textValue = (String)value;
				 }
				 cell.setCellValue(textValue);
			 }
		 }
		 try {
			workbook.write(os);
		} catch (IOException e) {
			logger.error("导出订单失败", e);
		}
	}
}
