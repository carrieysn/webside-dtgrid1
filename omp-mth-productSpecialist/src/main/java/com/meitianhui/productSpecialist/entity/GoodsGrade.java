package com.meitianhui.productSpecialist.entity;

import java.io.Serializable;

/***
 * 货品评实体类
 * 
 * @author 丁硕
 * @date 2016年2月25日
 */
public class GoodsGrade implements Serializable {

	private static final long serialVersionUID = 4073192666969474989L;

	private String grade_id; // 评分标识',
	private String goods_id; // 货物标识',
	private String user_id; // 评分用户',
	private float total_grade; // 总评分',
	private int brand_grade; // 品牌评分',
	private int supplier_grade; // 供应商评分',
	private int pack_grade; // 包装评分',
	private int cost_performance_grade; // 性价比评分',
	private int quality_grade; // 质量评分',
	private int competitiveness_grade; // 竞争力评分',
	private int transportation_grade; // 运输评分',
	private int appearance_grade; // 外观评分',
	private String created_date; // 创建时间',
	private String remark; // 备注

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public float getTotal_grade() {
		return total_grade;
	}

	public void setTotal_grade(float total_grade) {
		this.total_grade = total_grade;
	}

	public int getBrand_grade() {
		return brand_grade;
	}

	public void setBrand_grade(int brand_grade) {
		this.brand_grade = brand_grade;
	}

	public int getSupplier_grade() {
		return supplier_grade;
	}

	public void setSupplier_grade(int supplier_grade) {
		this.supplier_grade = supplier_grade;
	}

	public int getPack_grade() {
		return pack_grade;
	}

	public void setPack_grade(int pack_grade) {
		this.pack_grade = pack_grade;
	}

	public int getCost_performance_grade() {
		return cost_performance_grade;
	}

	public void setCost_performance_grade(int cost_performance_grade) {
		this.cost_performance_grade = cost_performance_grade;
	}

	public int getQuality_grade() {
		return quality_grade;
	}

	public void setQuality_grade(int quality_grade) {
		this.quality_grade = quality_grade;
	}

	public int getCompetitiveness_grade() {
		return competitiveness_grade;
	}

	public void setCompetitiveness_grade(int competitiveness_grade) {
		this.competitiveness_grade = competitiveness_grade;
	}

	public int getTransportation_grade() {
		return transportation_grade;
	}

	public void setTransportation_grade(int transportation_grade) {
		this.transportation_grade = transportation_grade;
	}

	public int getAppearance_grade() {
		return appearance_grade;
	}

	public void setAppearance_grade(int appearance_grade) {
		this.appearance_grade = appearance_grade;
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
