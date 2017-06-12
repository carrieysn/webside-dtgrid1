package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 买方现金日记账
 * 
 * @author 丁硕
 * @date 2016年5月30日
 */
public class PsCashDailyAccountBuyer implements Serializable {

	private static final long serialVersionUID = -6297303083451984260L;

	private String daily_account_id; // 日记账标识
	private String buyer_company_id; // 买家公司标识
	private String buyer_id; // 买家标识
	private String order_no; // 订单编号
	private String order_date; // 订单日期
	private String detail; // 订单说明（摘要）
	private BigDecimal amount; // 发生金额
	private String currency_code; // 币种符号（默认CNY）
	private String account_date; // 会计日期
	private String booking_mark; // 记账方向，可选值：记账方向，可选值：income（收入）、expenditure（支出）
	private String created_date; // 创建日期
	private String remark; // 备注

	public String getDaily_account_id() {
		return daily_account_id;
	}

	public void setDaily_account_id(String daily_account_id) {
		this.daily_account_id = daily_account_id;
	}

	public String getBuyer_company_id() {
		return buyer_company_id;
	}

	public void setBuyer_company_id(String buyer_company_id) {
		this.buyer_company_id = buyer_company_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
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

	public String getBooking_mark() {
		return booking_mark;
	}

	public void setBooking_mark(String booking_mark) {
		this.booking_mark = booking_mark;
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
