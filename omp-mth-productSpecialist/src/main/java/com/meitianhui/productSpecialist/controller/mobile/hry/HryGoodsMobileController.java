package com.meitianhui.productSpecialist.controller.mobile.hry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meitianhui.platform.constant.DataDictConstant;
import com.meitianhui.platform.entity.Area;
import com.meitianhui.platform.service.AreaService;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.productSpecialist.entity.AppEnum;
import com.meitianhui.productSpecialist.entity.GoodsDisplayAreaEnum;

import jxl.common.Logger;

/***
 * 惠如意商品展示
 * 
 * @author 丁硕
 * @date 2016年9月26日
 */
@Controller
@RequestMapping("/mobile/hry/goods")
public class HryGoodsMobileController {
	
	private Logger logger = Logger.getLogger(HryGoodsMobileController.class);
	private final String company_category = "bt";	//合作商类型,主要靠页面传入的参数
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request, @RequestParam Map<String, Object> params){
		//添加token信息，加载页面时，直接将token加入到session中
		request.getSession().setAttribute("token", ActionHelper.getInstance().getToken(request));
		request.getSession().setAttribute(company_category, AppEnum.HRY.getApp_key());
		try{
			//查询库中的所有省份信息进行展示
			Area area = new Area();
			area.setType_key(DataDictConstant.AreaType.PROVINCE);
			List<Area> areaList = areaService.queryArea(area);
			request.setAttribute("areaList", areaList);
			
			//获取类目列表
			List<GoodsDisplayAreaEnum> displayAreaList = new ArrayList<GoodsDisplayAreaEnum>();
			for(GoodsDisplayAreaEnum areaEnum : GoodsDisplayAreaEnum.values()){
				if(AppEnum.HRY.getApp_display_area().contains(areaEnum.getDisplay_area())){
					displayAreaList.add(areaEnum);
				}
			}
			request.setAttribute("displayAreaList", displayAreaList);
			//特殊处理，对搜索关键字进行处理，将数据设置到页面分页参数中
			String keyword = request.getParameter("keyword");
			if(StringUtils.isNotBlank(keyword)) {
				keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
				request.setAttribute("keyword", keyword);
			}
		} catch(Exception e){
			logger.error("找好货页面加载失败！", e);
		}
		return "mobile/hry/find";
	}
	
}
