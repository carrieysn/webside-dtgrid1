package com.meitianhui.productSpecialist.entity;

import java.math.BigDecimal;

/***
 * 产品销售扩展字段
 * 
 * @author 丁硕
 * @date 2016年3月11日
 */
public class GoodsSellExt extends Goods {

	private static final long serialVersionUID = -666174835985773369L;

	private String sku; // SKU码
	private String specification; // 规格
	private String pack; // 包装
	private BigDecimal cost_price = new BigDecimal(0); // 进价（成本价）
	private BigDecimal market_price; // 市场价
	private BigDecimal discount_price; // 优惠价
	private BigDecimal shipping_fee = new BigDecimal(0); // 邮费
	private String ladder_price; // 阶梯价
	private String product_source; // 商品来源
	private String producer; // 产地
	private String agent; // 代理人（分公司）
	private String supplier_id; // 供应商ID
	private String supplier; // 供应商
	private String manufacturer; // 生产商
	private int min_buy_qty; // 起订量
	private int max_buy_qty; // 限购数量
	private int sale_qty; // 可销售库存量
	private int stock_qty; // 库存量
	private String goods_unit; // 单位
	private String valid_thru; // 有效期
	private String warehouse_id; // 仓库ID
	private String warehouse; // 仓库
	private String delivery_area; // 配送地区
	private String payment_way; // 支付方式，可选值：online（在线支付） offline（货到付款）

	private String delivery_area_path; // 配送地区显示地址

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public BigDecimal getCost_price() {
		return cost_price;
	}

	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}

	public BigDecimal getMarket_price() {
		return market_price;
	}

	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}

	public BigDecimal getDiscount_price() {
		return discount_price;
	}

	public void setDiscount_price(BigDecimal discount_price) {
		this.discount_price = discount_price;
	}

	public BigDecimal getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(BigDecimal shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getLadder_price() {
		return ladder_price;
	}

	public void setLadder_price(String ladder_price) {
		this.ladder_price = ladder_price;
	}

	public String getProduct_source() {
		return product_source;
	}

	public void setProduct_source(String product_source) {
		this.product_source = product_source;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getMin_buy_qty() {
		return min_buy_qty;
	}

	public void setMin_buy_qty(int min_buy_qty) {
		this.min_buy_qty = min_buy_qty;
	}

	public int getMax_buy_qty() {
		return max_buy_qty;
	}

	public void setMax_buy_qty(int max_buy_qty) {
		this.max_buy_qty = max_buy_qty;
	}

	public int getSale_qty() {
		return sale_qty;
	}

	public void setSale_qty(int sale_qty) {
		this.sale_qty = sale_qty;
	}

	public int getStock_qty() {
		return stock_qty;
	}

	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}

	public String getGoods_unit() {
		return goods_unit;
	}

	public void setGoods_unit(String goods_unit) {
		this.goods_unit = goods_unit;
	}

	public String getValid_thru() {
		return valid_thru;
	}

	public void setValid_thru(String valid_thru) {
		this.valid_thru = valid_thru;
	}

	public String getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getDelivery_area() {
		return delivery_area;
	}

	public void setDelivery_area(String delivery_area) {
		this.delivery_area = delivery_area;
	}

	public String getPayment_way() {
		return payment_way;
	}

	public void setPayment_way(String payment_way) {
		this.payment_way = payment_way;
	}

	public String getDelivery_area_path() {
		return delivery_area_path;
	}

	public void setDelivery_area_path(String delivery_area_path) {
		this.delivery_area_path = delivery_area_path;
	}

}
