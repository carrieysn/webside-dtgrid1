package com.meitianhui.productSpecialist.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.meitianhui.platform.constant.AppportalServerContant;
import com.meitianhui.platform.exception.BusinessException;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.utils.PlatformApiUtil;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 我要批商品接口请求工具类
 * 
 * @author 丁硕
 * @date 2016年8月15日
 */
@Deprecated
public class PsGoodsApiUtil {

	private static Logger logger = Logger.getLogger(PsGoodsApiUtil.class);
	
	/***
	 * 同步商品信息
	 * @param goods
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static void syncGoods(GoodsSellExt goods) throws PlatformApiException {
		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(goods);
		for(Object key : json.keySet()){
			params.put(key + "", PlatformApiUtil.formatStr(json.get(key)));
		}
		PlatformApiUtil.requestServiceApi(AppportalServerContant.GOODS_SERVER_URL, "goods.psGoodsSync", params);
		logger.info("同步产品信息成功！产品标识：" + goods.getGoods_id());
	}
	
	/***
	 * 加载商品扩展信息
	 * @param goods
	 * @return
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static GoodsSellExt loadGoodsSellExtInfo(GoodsSellExt goods) throws PlatformApiException {
		if(goods != null){
			String goods_id = goods.getGoods_id();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("goods_id", goods_id);
			String data = PlatformApiUtil.requestServiceApi(AppportalServerContant.GOODS_SERVER_URL, "goods.psGoodsDetailFind" , params);
			if(StringUtils.isNotBlank(data)){
				JSONObject resultJson = JSONObject.fromObject(data);
				JSONArray array = resultJson.getJSONArray("list");
				if(array.size() > 0){
					JSONObject json = array.getJSONObject(0);
					json.remove("pic_info");
					json.remove("pic_detail_info");
					try {
						BeanUtils.populate(goods, array.getJSONObject(0));
					} catch (Exception e){
						logger.error("加载商品信息出错！", e);
						throw new PlatformApiException("加载商品信息出错！");
					}
				}
			}
		}
		return goods;
	}

	/***
	 * 同步商品状态
	 * @param goods_id
	 * @param status
	 * @throws BusinessException
	 * @author 丁硕
	 * @date   2016年8月15日
	 */
	public static void syncGoodsStatus(String goods_id, String status) throws PlatformApiException{}
	
}
