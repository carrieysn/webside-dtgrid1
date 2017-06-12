package com.meitianhui.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meitianhui.platform.utils.HttpClientUtil;
import com.meitianhui.productSpecialist.constant.OrderConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		confirmOrder();
	}
	
	public static void createOrder(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("item_num", "2");
		params.put("payment_way_key", "ZFFS_01");
		params.put("total_fee", "46");
		params.put("is_prepayment", "false");
		Map<String, String> goodsInfo = new HashMap<String, String>();
		goodsInfo.put("goods_id", "6373d80f07ad4bc9a6e42be406b6c67a");
		goodsInfo.put("qty", "2");
		List<Map<String, String>> goodsList = new ArrayList<>();
		goodsList.add(goodsInfo);
		params.put("ps_goods", JSONArray.fromObject(goodsList).toString());
		try {
			String result = requestUrl("http://localhost:8080/omp-mth-productSpecialist/app/order", "order.orderCreate", params);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void confirmOrder(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("transaction_no", "201606011555135867567498");
		params.put("transaction_status", OrderConstant.TRANSACTION_STATUS_CONFIRMED);
		try {
			String result = requestUrl("http://localhost:8080/omp-mth-productSpecialist/app/order", "order.orderConfirm", params);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String requestUrl(String url, String service, Map<String, String> params) throws Exception{
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("service", service);
		reqParams.put("token", "55075e58476f47b0a25a58277dc41f24");
		reqParams.put("params", JSONObject.fromObject(params).toString());
		String result = HttpClientUtil.post(url, reqParams);
		return result;
	}
}
