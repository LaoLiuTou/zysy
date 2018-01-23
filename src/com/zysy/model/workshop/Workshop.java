package com.zysy.model.workshop;
import java.util.Date;
/**
 * @author LT
 */
public class Workshop {

	/** 车间 */
	private  Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**  */
	private  String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/** 负责人 */
	private  String leader;
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	/**  */
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
