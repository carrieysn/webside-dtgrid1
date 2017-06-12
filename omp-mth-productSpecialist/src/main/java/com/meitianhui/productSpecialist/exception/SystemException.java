package com.meitianhui.productSpecialist.exception;

import org.apache.log4j.Logger;

import com.meitianhui.productSpecialist.constant.CommonRspCode;

/**
 * 系统异常类</br>
 * 系统运行中的异常,如sql语句查询错误,数据类型转换错误等异常，这属于系统级异常<br>
 * 此异常类只捕获exception类,然后讲异常抛给controller统一处理<br>
 * 
 * @author Tiny
 * @since 2015年12月17日
 */
public class SystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7100482811885669445L;

	private static final Logger logger = Logger.getLogger(SystemException.class);
	/** 返回数据 **/
	private String msg_code;
	private String message;

	/**
	 * 自定义参数描述
	 * 
	 * @param code
	 * @param msg
	 */
	public SystemException(Exception e) {
		msg_code = CommonRspCode.SYSTEM_ERROR;
		message = CommonRspCode.MSG.get(CommonRspCode.SYSTEM_ERROR);
		logger.error(e);
	}

	public String getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(String msg_code) {
		this.msg_code = msg_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
