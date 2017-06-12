package com.meitianhui.productSpecialist.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.Pagetion;
import com.meitianhui.productSpecialist.dao.StoresMapper;
import com.meitianhui.productSpecialist.service.StoresService;

/***
 * 门店逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
@Service("storesService")
public class StoresServiceImpl implements StoresService{

	@Autowired
	private StoresMapper storesMapper;
	
	@Override
	public Page queryStoresListPage(int pageNum, int pageSize, Map<String, Object> params) {
		Pagetion.startPage(pageNum, pageSize);
		storesMapper.queryStoresList(params);
		return Pagetion.endPage();
	}
	
	@Override
	public Map<String, Object> queryOneStores(String stores_id) {
		return storesMapper.queryOneStores(stores_id);
	}


	@Override
	public Page querySupplierListPage(int pageNum, int pageSize, Map<String, Object> params) {
		Pagetion.startPage(pageNum, pageSize);
		storesMapper.querySupplier(params);
		return Pagetion.endPage();
	}

	@Override
	public Map<String, Object> queryOneSupplier(String supplier_id) {
		return storesMapper.queryOneSupplier(supplier_id);
	}
	
}
