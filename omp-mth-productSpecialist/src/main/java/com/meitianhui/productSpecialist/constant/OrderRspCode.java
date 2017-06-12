package com.meitianhui.productSpecialist.constant;

/***
 * 订单处理异常code
 * 
 * @author 丁硕
 * @date 2016年5月31日
 */
public class OrderRspCode extends CommonRspCode{

	/**商品不存在**/
	public final static String GOODS_NOT_FOUND = "goods_not_found";
	/**商品信息为空**/
	public final static String GOODS_EMPTY = "goods_empty";
	/**预售商品库存不足**/
	public final static String PS_GOODS_STOCK_NOT_ENOUGH = "ps_goods_stock_not_enough";
	/**无效的订单信息**/
	public final static String INVALID_ORDER = "invalid_order";
	/**订单金额不一致**/
	public final static String ORDER_AMOUNT_NOT_SAME = "order_amount_not_same";
	/**交易流水号不存在**/
	public static String TRADE_NO_EXIST = "trade_no_exist";
	
	static{
		MSG.put(GOODS_NOT_FOUND, "未找到商品");
		MSG.put(GOODS_EMPTY, "商品信息为空");
		MSG.put(PS_GOODS_STOCK_NOT_ENOUGH, "预售商品库存不足");
		MSG.put(INVALID_ORDER, "无效的订单信息");
		MSG.put(ORDER_AMOUNT_NOT_SAME, "订单金额不一致");
		MSG.put(TRADE_NO_EXIST, "交易流水号不存在");
	}
}
