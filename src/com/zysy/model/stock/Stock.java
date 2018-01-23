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
	private  String size;
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
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



}
