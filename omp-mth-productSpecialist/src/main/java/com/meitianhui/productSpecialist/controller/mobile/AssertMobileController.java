package com.meitianhui.productSpecialist.controller.mobile;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 资产控制层
 * 
 * @author 丁硕
 * @date 2016年11月1日
 */
@Controller
@RequestMapping("/mobile/assert")
public class AssertMobileController {

	/***
	 * 资产首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月1日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		
		return "mobile/asset/asset";
	}
}
