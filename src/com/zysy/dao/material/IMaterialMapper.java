package com.zysy.dao.material;
import java.util.List;
import java.util.Map;
import com.zysy.model.material.Material;
	public interface IMaterialMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Material selectmaterialById(String id);
	/**
 	* 通过查询参数获取信息
 	* @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Material> selectmaterialByParam(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountmaterialByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updatematerial(Material material);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addmaterial(Material material);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deletematerial(String id);

}

