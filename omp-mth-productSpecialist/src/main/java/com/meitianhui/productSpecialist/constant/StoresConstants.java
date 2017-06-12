package com.meitianhui.productSpecialist.constant;

import java.util.ArrayList;
import java.util.List;

import com.meitianhui.platform.cache.DataDictCache;
import com.meitianhui.platform.entity.DataDict;

public class StoresConstants {

	/***
	 * 联盟商所包括的会员类型
	 */
	public static List<DataDict> BUSINESS_TYPE_LIST = new ArrayList<DataDict>();
	
	static{
		DataDictCache.reload("MDLX");
		BUSINESS_TYPE_LIST = DataDictCache.getDict("MDLX");
	}
}
