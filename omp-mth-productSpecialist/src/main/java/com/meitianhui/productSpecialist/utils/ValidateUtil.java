package com.meitianhui.productSpecialist.utils;

import java.util.Map;

import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.productSpecialist.constant.CommonRspCode;
import com.meitianhui.productSpecialist.exception.SystemException;

/**
 * 参数校验工具类
 * 
 * @author Tiny
 *
 */
public class ValidateUtil {

	/**
	 * 校验必填业务参数是否缺失
	 * 
	 * @param map
	 *            参数
	 * @param String
	 *            [] 需要校验的参数
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public static void validateParams(Map<String, String> map, String[] validateParam)
			throws BusinessException, SystemException {
		try {
			for (String str : validateParam) {
				if (null == map.get(str) || map.get(str).trim().length() == 0) {
					throw new BusinessException(CommonRspCode.PARAM_MISS, "业务参数" + str + "缺失");
				}
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
}
