package com.meitianhui.productSpecialist.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务处理异常码及描述</br>
 * 业务码规则:英文或英文简写描述,单词和单词之间用下划线("_")分开</br>
 * 此类定义一些常用的异常码,所有业务的异常码都要集成此类
 * 
 * @author tiny
 * 
 */
public class CommonRspCode {

	/** 异常描述 **/
	public static Map<String, String> MSG = new HashMap<String, String>();
	public static String RESPONSE_SUCC = "succ";
	public static String RESPONSE_FAIL = "fail";

	/**系统内部异常**/
	public static String SYSTEM_ERROR = "system_error";
	/**服务接口错误**/
	public static String SERVICE_ERROR = "service_error";
	/**参数缺失**/
	public static String PARAM_MISS = "param_miss";
	/**参数格式错误**/
	public static String BEAN_TO_MAP_ERROR = "bean_to_map_error";
	/**分页对象不存在**/
	public static String PAGE_NOT_EXIST = "page_not_exist";
	/**用户不存在**/
	public static String USER_NOT_EXIST = "user_not_exist";
	/**支付方式不存在**/
	public static String PAYMENT_WAY_NOT_EXIST = "payment_way_not_exist";
	
	/**支付方式不存在**/
	public static String NO_PARAMS_UPDATE = "no_params_update";
	static {
		MSG.put(SYSTEM_ERROR, "系统内部异常");
		MSG.put(SERVICE_ERROR, "服务接口错误");
		MSG.put(PARAM_MISS, "业务参数缺失");
		MSG.put(BEAN_TO_MAP_ERROR, "实体类转换MAP出错");
		MSG.put(PAGE_NOT_EXIST, "分页参数不存在");
		MSG.put(USER_NOT_EXIST, "用户不存在");
		MSG.put(PAYMENT_WAY_NOT_EXIST, "支付方式不存在");
		MSG.put(NO_PARAMS_UPDATE, "没有参数更新");
	}

}
