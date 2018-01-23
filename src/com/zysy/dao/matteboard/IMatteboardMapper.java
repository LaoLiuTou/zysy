package com.zysy.dao.matteboard;
import java.util.List;
import java.util.Map;
import com.zysy.model.matteboard.Matteboard;
	public interface IMatteboardMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Matteboard selectmatteboardById(String id);
	/**
 	* 通过查询参数获取信息
 	* @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Matteboard> selectmatteboardByParam(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountmatteboardByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updatematteboard(Matteboard matteboard);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addmatteboard(Matteboard matteboard);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deletematteboard(String id);

}

