package com.zysy.dao.workshop;
import java.util.List;
import java.util.Map;
import com.zysy.model.workshop.Workshop;
	public interface IWorkshopMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Workshop selectworkshopById(String id);
	/**
 	* 通过查询参数获取信息
 	* @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Workshop> selectworkshopByParam(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountworkshopByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updateworkshop(Workshop workshop);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addworkshop(Workshop workshop);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deleteworkshop(String id);

}

