package com.meitianhui.productSpecialist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/***
 * 门店数据操作接口
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
public interface StoresMapper {

	/**
	 * 根据条件查询门店列表
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public List<Map<String, Object>> queryStoresList(Map<String, Object> params);
	
	/***
	 * 查询单个门店信息
	 * @param stores_id
	 * @return
	 * @author 丁硕
	 * @date   2016年11月1日
	 */
	public Map<String, Object> queryOneStores(@Param("stores_id") String stores_id);
	
	/***
	 * 根据条件查询供应商列表
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年8月25日
	 */
	public List<Map<String, Object>> querySupplier(Map<String, Object> params);
	
	/***
	 * 查询单个供应商信息
	 * @param supplier_id
	 * @return
	 * @author 丁硕
	 * @date   2016年8月25日
	 */
	public Map<String, Object> queryOneSupplier(@Param("supplier_id")String supplier_id);
}
