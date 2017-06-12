package com.meitianhui.productSpecialist.service;

import java.util.List;
import java.util.Map;

import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.productSpecialist.entity.Goods;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;

/***
 * 货品逻辑处理接口
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public interface GoodsService {

	/***
	 * 根据条件查询产品分页信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public Page queryProductPage(int pageNum, int pageSize, Map<String, Object> params);
	
	/***
	 * 根据条件查询用户产品分页信息
	 * @param pageNum
	 * @param pageSize
	 * @param params {user_id: 用户标识，statistics_type: 统计类型}
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	public Page queryUserProductList(int pageNum, int pageSize, Map<String, Object> params);
	
	/***
	 * 保存或更新产品信息
	 * @param goods
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年3月11日
	 */
	public Goods saveOrUpdateGoods(GoodsSellExt goods, String token) throws PlatformApiException;
	
	/***
	 * 删除产品信息
	 * @param goods_id
	 * @param token
	 * @return
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	public boolean deleteGoods(String goods_id, String token) throws PlatformApiException;

	/***
	 * 根据货品编号查询单个货品信息
	 * 
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public GoodsSellExt queryOneGoods(String goods_id) throws PlatformApiException;
	
	/***
	 * 加载商品信息到对象中
	 * @param goods
	 * @throws PlatformApiException
	 * @author 丁硕
	 * @date   2016年12月15日
	 */
	public GoodsSellExt loadGoodsSellExtInfo(GoodsSellExt goods) throws PlatformApiException;

	/***
	 * 增加产品推荐数
	 * 
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public boolean addProductRecommendNum(String goods_id, String user_id);

	/***
	 * 增加产品关注数
	 * 
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public boolean addProductAttentionNum(String goods_id, String user_id);

	/***
	 * 增加产品评分数
	 * 
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public boolean addProductGradeNum(String goods_id, String user_id);

	/***
	 * 增加产品评论数
	 * 
	 * @param goods_id
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date 2016年2月25日
	 */
	public boolean addProductCommentNum(String goods_id, String user_id);
	
	/***
	 * 根据组织编号查询所有的组织下所有的产品编号
	 * @param team_id
	 * @return
	 * @author 丁硕
	 * @date   2016年5月6日
	 */
	public List<String> queryGoodsIdListByTeam(String team_id);
	
	
	//============首页展示需要显示的数据===============
	/***
	 * 查询出每个地区的产品数
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	public List<Map<String, Object>> queryCountByArea();
	
}
