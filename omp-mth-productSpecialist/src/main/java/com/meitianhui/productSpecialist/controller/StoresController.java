package com.meitianhui.productSpecialist.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.productSpecialist.service.StoresService;

@Controller
@RequestMapping("stores")
public class StoresController {
	
	private Logger logger = Logger.getLogger(StoresController.class);

	@Autowired
	private StoresService storesService;
	
	/***
	 * 根据条件查询供应商信息
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年8月25日
	 */
	@RequestMapping("querySupplierListPage")
	public @ResponseBody ResultVO<Page> querySupplierListPage(HttpServletRequest request, @RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize) {
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			//搜索关键字特殊处理,进行字符转码
			String search_input = request.getParameter("search_input");
			if(StringUtils.isNotBlank(search_input)){
				params.put("search_input", new String(search_input.getBytes("ISO-8859-1"),"UTF-8"));
			}
			Page page = storesService.querySupplierListPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg("根据条件查询供应商分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("根据条件查询供应商分页列表失败！", e);
		}
		return resultVo;
	} 
}
