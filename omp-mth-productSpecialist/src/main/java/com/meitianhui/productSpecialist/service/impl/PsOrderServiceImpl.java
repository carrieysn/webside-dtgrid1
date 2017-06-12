package com.meitianhui.productSpecialist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.AppportalServerContant;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Area;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.service.AreaService;
import com.meitianhui.platform.service.TeamPlatformService;
import com.meitianhui.platform.utils.IDUtil;
import com.meitianhui.platform.utils.PlatformApiUtil;
import com.meitianhui.productSpecialist.constant.OrderConstant;
import com.meitianhui.productSpecialist.constant.OrderRspCode;
import com.meitianhui.productSpecialist.constant.PsOrderStatusEnum;
import com.meitianhui.productSpecialist.dao.PsOrderMapper;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.entity.PsCashDailyAccount;
import com.meitianhui.productSpecialist.entity.PsCashDailyAccountBuyer;
import com.meitianhui.productSpecialist.entity.PsCashDailyAccountSeller;
import com.meitianhui.productSpecialist.entity.PsOrder;
import com.meitianhui.productSpecialist.entity.PsOrderItem;
import com.meitianhui.productSpecialist.exception.SystemException;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.PsOrderService;
import com.meitianhui.productSpecialist.utils.ValidateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 商品订单逻辑处理实现类
 * 
 * @author 丁硕
 * @date 2016年5月31日
 */
@Service("psOrderService")
public class PsOrderServiceImpl implements PsOrderService{
	
	private Logger logger = Logger.getLogger(PsOrderServiceImpl.class);
	
	@Autowired
	private PsOrderMapper psOrderMapper;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private TeamPlatformService teamPlatformService;
	
	@Autowired
	private AreaService areaService;

	/**
	 * 取出商品信息，查询并判断对应的库存是否存在
	 * 计算订单商品信息，计算对应的金额是否对应，实付款=应付款（预付、总金额？）
	 * 可支付多个商品信息,paramsMap:{ps_goods:[{goods_id,qty}]}
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String createOrder(Map<String, String> paramsMap, String token) throws BusinessException, SystemException {
		ValidateUtil.validateParams(paramsMap, new String[]{"item_num", "payment_way_key", "total_fee", "is_prepayment", "ps_goods"});
		JSONArray goodsArray = JSONArray.fromObject(paramsMap.get("ps_goods"));	//商品信息
		if(goodsArray.size() < 1){
			throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.GOODS_EMPTY), OrderRspCode.GOODS_EMPTY);
		}
		//查询对应的商品信息
		String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");	//发生时间
		final String order_id = IDUtil.getUUID();	//订单标识
		int item_num = Integer.parseInt(paramsMap.get("item_num"));	//商品总数
		BigDecimal total_fee = new BigDecimal(paramsMap.get("total_fee"));	//用户实际付款的金额
		if(item_num < 1 || total_fee.doubleValue() < 0){
			throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.INVALID_ORDER), OrderRspCode.INVALID_ORDER);
		}
		
		/**暂时只支付一种商品的订单，直接取第一个商品信息**/
		JSONObject goodsInfo = goodsArray.getJSONObject(0);
		String goods_id = goodsInfo.getString("goods_id");
		BigDecimal qty = new BigDecimal(goodsInfo.getString("qty"));	//商品数量
		GoodsSellExt goods = null;
		try {
			goods = goodsService.queryOneGoods(goods_id);
		} catch (PlatformApiException e) {
			throw new SystemException(e);
		}
		if(goods == null){
			logger.error("创建订单失败，商品：" + goods_id + " 不存在！");
			throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.GOODS_NOT_FOUND), OrderRspCode.GOODS_NOT_FOUND);
		}
		//判断库存是否充足,只拿可销售库存进行逻辑判断
		if(goods.getSale_qty() - qty.intValue() < 0){
			throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.PS_GOODS_STOCK_NOT_ENOUGH), OrderRspCode.PS_GOODS_STOCK_NOT_ENOUGH);
		}
		
		/**构建订单商品信息**/
		PsOrderItem item = new PsOrderItem();
		item.setOrder_item_id(IDUtil.getUUID());
		item.setOrder_id(order_id);
		item.setGoods_id(goods.getGoods_id());
		item.setGoods_title(goods.getTitle());
		item.setGoods_pic_info(goods.getPic_info());
		item.setQty(qty.intValue());
		item.setGoods_unit(goods.getGoods_unit());
		item.setSale_price(goods.getMarket_price());	//市场价就是销售价
		item.setTotal_fee(goods.getDiscount_price().multiply(qty));	//金额小计，实际付款金额
		item.setDiscount_fee(goods.getMarket_price().multiply(qty).subtract(item.getTotal_fee()));	//优惠金额，价格差乘以数量
		item.setStatus(CommonConstant.status_normal);
		item.setCreated_date(now);
		
		/**构建订单主表信息**/
		User user = UserCache.getUser(token);	//当前登录用户
		Team buyerTeam = user.getTeam();	//当前登录用户组织信息
		Area buyerArea = areaService.findArea(buyerTeam.getArea_id());	//当前登录用户组织所在的地区
		Team sellTeam = teamPlatformService.queryTeamById(psOrderMapper.getTeamIdByUserId(goods.getOwner_id()));
		PsOrder psOrder = new PsOrder();
		psOrder.setOrder_id(order_id);
		psOrder.setOrder_no(IDUtil.getTradeNo());
		psOrder.setDesc1(goods.getTitle());
		psOrder.setItem_num(item_num);
		psOrder.setBuyer_company_id(buyerTeam.getTeam_id());
		psOrder.setBuyer_id(user.getUser_id());
		psOrder.setSeller_company_id(sellTeam.getTeam_id());
		psOrder.setSeller_id(goods.getOwner_id());
		psOrder.setPayment_way_key(paramsMap.get("payment_way_key"));	//支付方式
		//psOrder.setTotal_fee(item.getTotal_fee());	//订单总金额
		psOrder.setDiscount_fee(item.getDiscount_fee());	//订单优惠总金额
		psOrder.setSale_fee(goods.getMarket_price().multiply(qty));	//订单销售总金额,市场价乘以数量
		
		/***临时处理逻辑，如果实际付款与订单总金额相同，则表示不是预付款，否则表示全是预付款**/
		if("true".equals(paramsMap.get("is_prepayment"))){	//实际付款为预付款
			psOrder.setPrepayment(total_fee);
			psOrder.setTotal_fee(item.getTotal_fee());	//订单总金额
		} else{
			//记录订单总金额
			psOrder.setPrepayment(new BigDecimal(0));
			if(total_fee.floatValue() == item.getTotal_fee().floatValue()){
				psOrder.setTotal_fee(total_fee);	//订单总金额
			} else{
				throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.ORDER_AMOUNT_NOT_SAME), OrderRspCode.ORDER_AMOUNT_NOT_SAME);
			}
		}
		psOrder.setDelivery_address((buyerArea == null ? "" : buyerArea.getPath()) + buyerTeam.getAddress());
		psOrder.setContact_person(buyerTeam.getContact_person());
		psOrder.setContact_tel(buyerTeam.getContact_tel());
		psOrder.setStatus(PsOrderStatusEnum.PROCESSING.getValue());
		psOrder.setOrder_date(now);
		psOrder.setCreated_date(now);
		
		//保存订单相关信息
		psOrderMapper.insertPsOrder(psOrder);
		List<PsOrderItem> itemList = new ArrayList<PsOrderItem>();
		itemList.add(item);
		psOrderMapper.insertPsOrderItem(itemList);
		return psOrder.getOrder_no();
	}

	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String confirmOrder(Map<String, String> paramsMap) throws BusinessException, SystemException {
		ValidateUtil.validateParams(paramsMap, new String[]{"transaction_no", "transaction_status"});
		final String transaction_no = paramsMap.get("transaction_no");	//订单交易号
		final String transaction_status = paramsMap.get("transaction_status");	//交易状态
		PsOrder psOrder = psOrderMapper.queryPsOrderByNo(transaction_no);
		if(psOrder == null){
			throw new BusinessException(OrderRspCode.MSG.get(OrderRspCode.TRADE_NO_EXIST), OrderRspCode.TRADE_NO_EXIST);
		}
		//只有待确定的才能进行订单确认
		if(OrderConstant.TRANSACTION_STATUS_CONFIRMED.equals(transaction_status) && PsOrderStatusEnum.PROCESSING.getValue().equals(psOrder.getStatus())){
			this.confirmOrder(psOrder);
		} else{
			logger.warn("订单信息：" + paramsMap + " 未进行确认操作！");
		}
		return null;
	}
	
	/***
	 * 1、插入现金记帐表
	 * 2、更新订单状态
	 * 3、调用接口，更新商品库存
	 * @param psOrder
	 * @throws BusinessException
	 * @throws SystemException
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	@Transactional(rollbackFor=Exception.class)
	private void confirmOrder(PsOrder psOrder) throws BusinessException, SystemException{
		String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");	//发生时间
		
		BigDecimal amount = psOrder.getPrepayment().floatValue() > 0 ? psOrder.getPrepayment() : psOrder.getTotal_fee();	//实际发生金额
		/**买方现金日记账**/
		PsCashDailyAccountBuyer accountBuyer = new PsCashDailyAccountBuyer();
		accountBuyer.setDaily_account_id(IDUtil.getUUID());
		accountBuyer.setBuyer_company_id(psOrder.getBuyer_company_id());
		accountBuyer.setBuyer_id(psOrder.getBuyer_id());
		accountBuyer.setOrder_no(psOrder.getOrder_no());
		accountBuyer.setOrder_date(psOrder.getOrder_date());
		accountBuyer.setDetail(null);
		accountBuyer.setAmount(amount);
		accountBuyer.setCurrency_code("CNY");
		accountBuyer.setAccount_date(now);
		accountBuyer.setBooking_mark("expenditure");
		accountBuyer.setCreated_date(now);
		
		/***卖方现金日记账**/
		PsCashDailyAccountSeller accountSeller = new PsCashDailyAccountSeller();
		accountSeller.setDaily_account_id(IDUtil.getUUID());
		accountSeller.setSeller_company_id(psOrder.getSeller_company_id());
		accountSeller.setSeller_id(psOrder.getSeller_id());
		accountSeller.setOrder_no(psOrder.getOrder_no());
		accountSeller.setOrder_date(psOrder.getOrder_date());
		accountBuyer.setDetail(null);
		accountSeller.setAmount(amount);
		accountSeller.setCurrency_code("CNY");
		accountSeller.setAccount_date(now);
		accountSeller.setBooking_mark("income");
		accountSeller.setCreated_date(now);
		
		PsCashDailyAccount dailyAccount = new PsCashDailyAccount();
		dailyAccount.setCompany_id(null);
		dailyAccount.setOrder_no(psOrder.getOrder_no());
		dailyAccount.setOrder_date(psOrder.getOrder_date());
		dailyAccount.setDetail(null);
		dailyAccount.setPayment_way_key(psOrder.getPayment_way_key());
		dailyAccount.setAmount(amount);
		dailyAccount.setCurrency_code("CNY");
		dailyAccount.setAccount_date(now);
		dailyAccount.setCreated_date(now);
		
		/**记录买方信息**/
		dailyAccount.setDaily_account_id(IDUtil.getUUID());
		dailyAccount.setTrans_company_type("buyer");
		dailyAccount.setTrans_company_id(accountBuyer.getBuyer_company_id());
		dailyAccount.setTrans_user_id(accountBuyer.getBuyer_id());
		dailyAccount.setBooking_mark(accountBuyer.getBooking_mark());
		psOrderMapper.insertPsCashDailyAccount(dailyAccount);
		psOrderMapper.insertPsCashDailyAccountBuyer(accountBuyer);
		
		/**记录卖方信息**/
		dailyAccount.setDaily_account_id(IDUtil.getUUID());
		dailyAccount.setTrans_company_type("seller");
		dailyAccount.setTrans_company_id(accountSeller.getSeller_company_id());
		dailyAccount.setTrans_user_id(accountSeller.getSeller_id());
		dailyAccount.setBooking_mark(accountSeller.getBooking_mark());
		psOrderMapper.insertPsCashDailyAccount(dailyAccount);
		psOrderMapper.insertPsCashDailyAccountSeller(accountSeller);
		
		/***更新订单信息**/
		psOrderMapper.updatePsOrderStatus(psOrder.getOrder_id(), PsOrderStatusEnum.PAYED.getValue());	//直接变成已支付状态
		
		/**调用接口，更新商品库存信息**/
		List<PsOrderItem> orderItemList = psOrderMapper.queryPsOrderItemByOrderId(psOrder.getOrder_id());
		if(orderItemList != null && orderItemList.size() > 0){
			for(PsOrderItem orderItem : orderItemList){
				Map<String, Object> goodsParams = new HashMap<String, Object>();
				goodsParams.put("goods_id", orderItem.getGoods_id());
				goodsParams.put("sell_qty", orderItem.getQty() + "");
				try {
					PlatformApiUtil.requestServiceApi(AppportalServerContant.GOODS_SERVER_URL, "psGoodsSaleQtyEdit", goodsParams);
				} catch (Exception e) {
					logger.error("更新商品库存信息失败", e);
				} 
			}
		}
	}
	
	
}
