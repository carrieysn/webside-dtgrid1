package com.meitianhui.productSpecialist.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.utils.ActionHelper;

/***
 * 登录拦截器，主要判断用户带入的token的有效性，是否进行过验证登录
 * 
 * @author 丁硕
 * @date 2016年2月23日
 */
public class MobileLoginInterceptor implements HandlerInterceptor{

	private static final Log log = LogFactory.getLog(MobileLoginInterceptor.class);
	
	//应用权限菜单，同买手菜单
	private final String app_authority_id = "edbc0f9e-98f0-11e5-823a-fcaa1490ccaf";
	
	/**
	 * 进入Controller层之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //对用户进行验证，判断请求参数中是否有token信息，如果有信息，则从缓存中取出相关信息去比较数据的有效性
		String token = ActionHelper.getInstance().getToken(request);
		User user = ActionHelper.getInstance().getUser(token);
		String request_url = request.getRequestURI();
		if(request_url.endsWith("/mobile/user/logout")){
			return true;
		} else if(user == null){  //没有进行登录
			String current_url = ActionHelper.getInstance().getFullRequestUrl(request);
			response.sendRedirect(request.getContextPath() + "/mobile/user/logout");
			log.info("用户未登录，拦截请求地址：" + current_url);
			return false;
 		}
		if(request_url.endsWith("/mobile/user/unauthorized")){ //放行
			return true;
		} else{
			boolean hasAuth = user.getMenuIdList().contains(app_authority_id);
			if(!hasAuth){
				response.sendRedirect(request.getContextPath() + "/mobile/user/unauthorized?token=" + token);
				log.info("登录用户未具备相应菜单权限，登录用户ID：" + user.getUser_id() + ", 账号：" + user.getUser_account() + ", 用户名：" + user.getName());
				return false;
			}
		}
		return true;
	}
	
	/***
	 * 页面渲染完成后
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
	}

	/**
	 * controller层业务逻辑处理完成后
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
	}

}
