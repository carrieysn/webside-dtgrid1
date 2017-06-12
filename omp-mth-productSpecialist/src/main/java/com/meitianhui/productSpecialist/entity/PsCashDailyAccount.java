package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 公司现金日记
 * 
 * @author 丁硕
 * @date 2016年5月30日
 */
public class PsCashDailyAccount implements Serializable {

	private static final long serialVersionUID = -2037856966566164489L;

	private String daily_account_id; // 日记账标识
	private String company_id; // 总公司标识
	private String order_no; // 订单编号
	private String order_date; // 订单日期
	private String detail; // 订单说明（摘要）
	private String payment_way_key; // 支付方式，引用字典分类（ZFFS）
	private String trans_company_type; // 参入交易公司类型， buyer（买家）、seller（卖方）
	private String trans_company_id; // 参入交易公司标识
	private String trans_user_id; // 参入交易人标识
	private String booking_mark; // 记账方向，可选值：income（收入）、expenditure（支出）
	private BigDecimal amount; // 发生金额
	private String currency_code; // 币种符号（默认CNY）
	private String account_date; // 会计日期
	private String created_date; // 创建日期
	private String remark; // 备注',

	public String getDaily_account_id() {
		return daily_account_id;
	}

	public void setDaily_account_id(String daily_account_id) {
		this.daily_account_id = daily_account_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPayment_way_key() {
		return payment_way_key;
	}

	public void setPayment_way_key(String payment_way_key) {
		this.payment_way_key = payment_way_key;
	}

	public String getTrans_company_type() {
		return trans_company_type;
	}

	public void setTrans_company_type(String trans_company_type) {
		this.trans_company_type = trans_company_type;
	}

	public String getTrans_company_id() {
		return trans_company_id;
	}

	public void setTrans_company_id(String trans_company_id) {
		this.trans_company_id = trans_company_id;
	}

	public String getTrans_user_id() {
		return trans_user_id;
	}

	public void setTrans_user_id(String trans_user_id) {
		this.trans_user_id = trans_user_id;
	}

	public String getBooking_mark() {
		return booking_mark;
	}

	public void setBooking_mark(String booking_mark) {
		this.booking_mark = booking_mark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getAccount_date() {
		return account_date;
	}

	public void setAccount_date(String account_date) {
		this.account_date = account_date;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
