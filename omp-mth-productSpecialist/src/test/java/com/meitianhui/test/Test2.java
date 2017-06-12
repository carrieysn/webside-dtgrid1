package com.meitianhui.test;

import java.util.HashMap;
import java.util.Map;

import com.meitianhui.platform.constant.AppportalServerContant;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.PlatformApiUtil;

public class Test2 {

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			//params.put("goods_id", "078dc1b100ce4bdd883645a3e93f95ec");
			String data = PlatformApiUtil.requestServiceApi(AppportalServerContant.GOODS_SERVER_URL, "goods.gdViewSellDetailPageFind", params, 1, 10);
			System.out.println(data);
		} catch (PlatformApiException e) {
			e.printStackTrace();
		}
	}
}
