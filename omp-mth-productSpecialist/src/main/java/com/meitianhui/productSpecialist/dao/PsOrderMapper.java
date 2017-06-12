package com.meitianhui.productSpecialist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meitianhui.productSpecialist.entity.PsCashDailyAccount;
import com.meitianhui.productSpecialist.entity.PsCashDailyAccountBuyer;
import com.meitianhui.productSpecialist.entity.PsCashDailyAccountSeller;
import com.meitianhui.productSpecialist.entity.PsOrder;
import com.meitianhui.productSpecialist.entity.PsOrderItem;

/***
 * 商品订单数据操作层
 * 
 * @author 丁硕
 * @date 2016年6月1日
 */
public interface PsOrderMapper {

	/***
	 * 根据用户标识查询组织标识
	 * @param user_id
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public String getTeamIdByUserId(@Param("user_id")String user_id);
	
	/***
	 * 新增商品订单信息
	 * @param psOrder
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int insertPsOrder(PsOrder psOrder);
	
	/***
	 * 批量新增订单商品信息
	 * @param orderItemList
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int insertPsOrderItem(@Param("orderItemList") List<PsOrderItem> orderItemList);
	
	/***
	 * 更新订单状态信息
	 * @param order_id
	 * @param status
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int updatePsOrderStatus(@Param("order_id") String order_id, @Param("status")String status);
	
	/***
	 * 根据订单号查询订单信息
	 * @param order_no
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public PsOrder queryPsOrderByNo(@Param("order_no") String order_no);
	
	/**
	 * 根据订单标识查询订单商品信息
	 * @param order_id
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public List<PsOrderItem> queryPsOrderItemByOrderId(@Param("order_id") String order_id);
	
	/***
	 * 新增公司现金日记账记录
	 * @param dailyAccount
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int insertPsCashDailyAccount(PsCashDailyAccount dailyAccount);
	
	/***
	 * 新增买方现金日记账
	 * @param dailyAccountBuyer
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int insertPsCashDailyAccountBuyer(PsCashDailyAccountBuyer dailyAccountBuyer);
	
	/***
	 * 新增卖方现金日记账
	 * @param dailyAccountSeller
	 * @return
	 * @author 丁硕
	 * @date   2016年6月1日
	 */
	public int insertPsCashDailyAccountSeller(PsCashDailyAccountSeller dailyAccountSeller);
}
