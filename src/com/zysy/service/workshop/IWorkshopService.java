package com.zysy.service.workshop;
import java.util.List;
import java.util.Map;
import com.zysy.model.workshop.Workshop;
public interface IWorkshopService {
	/**
 * 通过id选取
 * @return
 */
	public Workshop selectWorkshopById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Workshop> selectWorkshopByParam(Map paramMap); 
	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountWorkshopByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateWorkshop(Workshop workshop);
	/**
 * 添加 
 * @return
 */ 
	public  int addWorkshop(Workshop workshop);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteWorkshop(String id);

}

