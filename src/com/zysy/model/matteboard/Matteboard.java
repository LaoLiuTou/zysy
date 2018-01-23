package com.zysy.model.matteboard;
import java.util.Date;
/**
 * @author LT
 */
public class Matteboard {

	/** 大锯哑光板 */
	private  Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/** 荒料id */
	private  Long sb_id;
	public Long getSb_id() {
		return sb_id;
	}
	public void setSb_id(Long sb_id) {
		this.sb_id = sb_id;
	}
	/** 荒料编号 */
	private  String sb_code;
	public String getSb_code() {
		return sb_code;
	}
	public void setSb_code(String sb_code) {
		this.sb_code = sb_code;
	}
	/** 荒料规格 */
	private  String sb_spec;
	public String getSb_spec() {
		return sb_spec;
	}
	public void setSb_spec(String sb_spec) {
		this.sb_spec = sb_spec;
	}
	/** 立方数（小数3位） */
	private  String sb_cube;
	public String getSb_cube() {
		return sb_cube;
	}
	public void setSb_cube(String sb_cube) {
		this.sb_cube = sb_cube;
	}
	/** 料层-上中下 */
	private  String layer;
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
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
	/** 总块数 */
	private  Long blocknumber;
	public Long getBlocknumber() {
		return blocknumber;
	}
	public void setBlocknumber(Long blocknumber) {
		this.blocknumber = blocknumber;
	}
	/** 平方 */
	private  String square;
	public String getSquare() {
		return square;
	}
	public void setSquare(String square) {
		this.square = square;
	}
	/** 不合格块数 */
	private  Long belowgradeblock;
	public Long getBelowgradeblock() {
		return belowgradeblock;
	}
	public void setBelowgradeblock(Long belowgradeblock) {
		this.belowgradeblock = belowgradeblock;
	}
	/** 不合格平方数 */
	private  String belowgradesquare;
	public String getBelowgradesquare() {
		return belowgradesquare;
	}
	public void setBelowgradesquare(String belowgradesquare) {
		this.belowgradesquare = belowgradesquare;
	}
	/** 备注 */
	private  String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	/** 状态 */
	private  Long state;
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}



}
