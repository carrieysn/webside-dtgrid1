package com.meitianhui.productSpecialist.service;

import java.util.Map;

import com.meitianhui.platform.entity.Page;

/***
 * 门店逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
public interface StoresService {

	/***
	 * 根据条件查询门店分页列表信息
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public Page queryStoresListPage(int pageNum, int pageSize, Map<String, Object> params);
	
	/***
	 * 查询单个门店信息
	 * @param stores_id
	 * @return
	 * @author 丁硕
	 * @date   2016年11月1日
	 */
	public Map<String, Object> queryOneStores(String stores_id);
	
	/***
	 * 根据条件查询供应商信息
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年8月25日
	 */
	public Page querySupplierListPage(int pageNum, int pageSize, Map<String, Object> params);
	
	/***
	 * 查询单个供应商信息
	 * @param supplier_id
	 * @return
	 * @author 丁硕
	 * @date   2016年8月25日
	 */
	public Map<String, Object> queryOneSupplier(String supplier_id);
}
