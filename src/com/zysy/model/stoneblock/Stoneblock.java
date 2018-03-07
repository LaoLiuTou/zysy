package com.zysy.model.stoneblock;
import java.util.Date;
/**
 * @author LT
 */
public class Stoneblock {

	/** 荒料id */
	private  Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/** 单据编号 */
	private  String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/** 来源 */
	private  String source;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	/**  */
	private  Date s_dt;
	public Date getS_dt() {
		return s_dt;
	}
	public void setS_dt(Date s_dt) {
		this.s_dt = s_dt;
	}
	/** 入库地点 */
	private  String place;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	/** 厂内料号 */
	private  String number;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	/** 长 */
	private  String length;
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	/** 宽 */
	private  String width;
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	/** 高 */
	private  String height;
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	/** 立方数（小数3位） */
	private  String cube;
	public String getCube() {
		return cube;
	}
	public void setCube(String cube) {
		this.cube = cube;
	}
	/** 单价 */
	private  String price;
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	/** 金额（小数2位） */
	private  String sum;
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	/** 车牌号 */
	private  String platenumber;
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	/** 账差 */
	private  String accountdiff;
	public String getAccountdiff() {
		return accountdiff;
	}
	public void setAccountdiff(String accountdiff) {
		this.accountdiff = accountdiff;
	}
	/** 审核人 */
	private  String auditor;
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	/** 录入人 */
	private  String editor;
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/** 备注 */
	private  String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/** 状态 */
	private  Long state;
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	/** 总块数 */
	private  Long blocknumber;
	public Long getBlocknumber() {
		return blocknumber;
	}
	public void setBlocknumber(Long blocknumber) {
		this.blocknumber = blocknumber;
	}
	/** 创建人id */
	private  Long c_id;
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}

	private Date rz_dt;
	
	public Date getRz_dt() {
		return rz_dt;
	}
	public void setRz_dt(Date rz_dt) {
		this.rz_dt = rz_dt;
	}
	
	private Date yf_dt;
	public Date getYf_dt() {
		return yf_dt;
	}
	public void setYf_dt(Date yf_dt) {
		this.yf_dt = yf_dt;
	}

	private String stocktype;
	public String getStocktype() {
		return stocktype;
	}
	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}
	private Long workshop;
	public Long getWorkshop() {
		return workshop;
	}
	public void setWorkshop(Long workshop) {
		this.workshop = workshop;
	}
	
	private String color;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
 
