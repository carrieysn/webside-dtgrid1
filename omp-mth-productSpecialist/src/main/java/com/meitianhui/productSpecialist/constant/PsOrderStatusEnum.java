package com.meitianhui.productSpecialist.constant;

/***
 * 订单状态枚举类
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
public enum PsOrderStatusEnum {

	/** 订单处理中 **/
	PROCESSING("processing"),
	/** 订单确定 **/
	CONFIRMED("confirmed"),
	/** 已支付 **/
	PAYED("payed"),
	/** 已发货 **/
	DELIVERED("delivered"),
	/** 已收货 **/
	RECEIVED("received"),
	/** 订单取消 **/
	CANCELLED("cancelled"),
	/** 订单完成 **/
	CLOSED("closed");

	PsOrderStatusEnum(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
