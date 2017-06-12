package com.meitianhui.productSpecialist.utils;

import java.util.HashMap;
import java.util.Map;
import com.meitianhui.platform.constant.OperateServerContant;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.PlatformApiUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 运营内部系统接口请求工具类
 * 
 * @author 丁硕
 * @date 2016年8月15日
 */
public class OperateApiUtil {

	/***
	 * 加载订单统计信息
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static JSONObject loadOrderDashBoard() throws PlatformApiException{
		Map<String, Object> params = new HashMap<String, Object>();
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/order", "order.queryOrderDashboard", params);
		JSONObject json = JSONObject.fromObject(data);
		return json;
	}
	
	/***
	 * 加载热销商品
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static JSONArray loadSalesGoodsList() throws PlatformApiException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("top", "10");
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/order", "order.queryTopSaleGoodsList", params);
		JSONArray array = JSONArray.fromObject(data);
		return array;
	}
	
	/***
	 * 查询销售报表信息
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static JSONArray querySaleSummaryList(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.querySaleSummaryForBuyer", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询我要批订单统计
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年9月20日
	 */
	public static JSONArray queryStatisticsSalesAreaWyp(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesAreaWyp", params);
		return JSONArray.fromObject(data); 
	}
	
	/***
	 * 查询超级返订单统计
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年9月20日
	 */
	public static JSONArray queryStatisticsSalesAreaCjf(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesAreaCjf", params);
		return JSONArray.fromObject(data); 
	}
	
	/***
	 * 查询门店交易排行
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年10月12日
	 */
	public static JSONArray queryStatisticsTransStorePop(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsTransStorePop", params);
		return JSONArray.fromObject(data); 
	}
	
	/***
	 * 查询门店交易统计
	 * @param stores_id
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月2日
	 */
	public static JSONArray queryStatisticsTransStore(String stores_id) throws PlatformApiException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stores_id", stores_id);
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsTransStore", params);
		return JSONArray.fromObject(data); 
	}
	
	/***
	 * 门店注册
	 * @param params
	 * @throws PlatformApiException
	 */
	public static void registerStore(Map<String, Object> params) throws PlatformApiException{
		PlatformApiUtil.requestServiceApi(OperateServerContant.STORE_API_SERVER + "/app/stores", "stores.registerStores", params);
	}
	
	/***
	 * 查询交易概况信息
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static JSONObject queryTradeOverview(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.TRADEOVERVIEW_API_SERVER + "/app/tradeOverview", "statistics.tradeOverview", params);
		return JSONObject.fromObject(data);
	}
	
	
	//====================================即时播报=================================================
	/***
	 * 查询统计红包抽奖总计
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	public static JSONObject querySalesGiftLuckyTotal(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesGiftLuckyTotal", params);
		JSONArray array = JSONArray.fromObject(data);
		return array.size() > 0 ? array.getJSONObject(0) : new JSONObject();
	}
	
	/***
	 * 查询统计红包抽奖历史总计
	 * @param end_date 结束时间
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	public static JSONObject querySalesGiftLuckyTotalHistory(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesGiftLuckyTotalHistory", params);
		JSONArray array = JSONArray.fromObject(data);
		return array.size() > 0 ? array.getJSONObject(0) : new JSONObject();
	}
	
	/***
	 * 查询超级返实时订单统计
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	public static JSONObject querySalesCjfTotal(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfTotal", params);
		JSONArray array = JSONArray.fromObject(data);
		return array.size() > 0 ? array.getJSONObject(0) : new JSONObject();
	}
	
	/***
	 * 查询超级返历史订单统计
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	public static JSONObject querySalesCjfTotalHistory(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfTotalHistory", params);
		JSONArray array = JSONArray.fromObject(data);
		return array.size() > 0 ? array.getJSONObject(0) : new JSONObject();
	}
	
	/***
	 * 查询超级返实时订单流量
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	public static JSONArray querySalesCjfTraffic(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.querystatisticsSalesCjfTraffic", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询超级返历史订单流量
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	public static JSONArray querySalesCjfTrafficHistory(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.querystatisticsSalesCjfTrafficHistory", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询超级返当日品类销量占比
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	public static JSONArray querySalesCjfGoods(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfGoods", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询超级返历史品类销量占比
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	public static JSONArray querySalesCjfGoodsHistory(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfGoodsHistory", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询超级返当日参入会员数
	 * @param params
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月8日
	 */
	public static JSONArray querySalesCjfMemberNum(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfMemberNum", params);
		return JSONArray.fromObject(data);
	}
	
	/***
	 * 查询超级返历史参入会员数
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年11月14日
	 */
	public static JSONArray querySalesCjfMemberNumHistory(Map<String, Object> params) throws PlatformApiException{
		String data = PlatformApiUtil.requestServiceApi(OperateServerContant.DATAWAREHOUSE_API_SERVER + "/api/procedure", "procedure.queryStatisticsSalesCjfMemberNumHistory", params);
		return JSONArray.fromObject(data);
	}
	
}
