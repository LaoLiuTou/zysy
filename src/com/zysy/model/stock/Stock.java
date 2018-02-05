package com.zysy.model.stock;
import java.util.Date;
/**
 * @author LT
 */
public class Stock {

	/** 毛板-磨光 */
	private  Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/** 物品名称 */
	private  String material;
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	/** 库别 */
	private  String stocktype;
	public String getStocktype() {
		return stocktype;
	}
	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}
	/** 车间id */
	private  Long workshop;
	public Long getWorkshop() {
		return workshop;
	}
	public void setWorkshop(Long workshop) {
		this.workshop = workshop;
	}
	/** 单位 */
	private  String unit;
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/** 板材尺寸 */
	private  String msize;
	 
	public String getMsize() {
		return msize;
	}
	public void setMsize(String msize) {
		this.msize = msize;
	}
	/** 厚 */
	private  String height;
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	/** 数量 */
	private  String number;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	/** 备注 */
	private  String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/** 破损  是，否 */
	private  String damage;
	public String getDamage() {
		return damage;
	}
	public void setDamage(String damage) {
		this.damage = damage;
	}
	/** 状态 */
	private  Long state;
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	/**  */
	private  Date c_dt;
	public Date getC_dt() {
		return c_dt;
	}
	public void setC_dt(Date c_dt) {
		this.c_dt = c_dt;
	}
	/**  */
	private  Date u_dt;
	public Date getU_dt() {
		return u_dt;
	}
	public void setU_dt(Date u_dt) {
		this.u_dt = u_dt;
	}
	/** 创建人id */
	private  Long c_id;
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}


	
	private String material_name;
	private String workshop_name;
	private String stocktype_name;
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getWorkshop_name() {
		return workshop_name;
	}
	public void setWorkshop_name(String workshop_name) {
		this.workshop_name = workshop_name;
	}
	public String getStocktype_name() {
		return stocktype_name;
	}
	public void setStocktype_name(String stocktype_name) {
		this.stocktype_name = stocktype_name;
	}
	private String sum_number;
	public String getSum_number() {
		return sum_number;
	}
	public void setSum_number(String sum_number) {
		this.sum_number = sum_number;
	}
	private String sum_in;
	private String sum_out;
	public String getSum_in() {
		return sum_in;
	}
	public void setSum_in(String sum_in) {
		this.sum_in = sum_in;
	}
	public String getSum_out() {
		return sum_out;
	}
	public void setSum_out(String sum_out) {
		this.sum_out = sum_out;
	}
	
	
}
