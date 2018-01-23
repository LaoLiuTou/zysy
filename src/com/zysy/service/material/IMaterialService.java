package com.zysy.service.material;
import java.util.List;
import java.util.Map;
import com.zysy.model.material.Material;
public interface IMaterialService {
	/**
 * 通过id选取
 * @return
 */
	public Material selectMaterialById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Material> selectMaterialByParam(Map paramMap); 
	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountMaterialByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateMaterial(Material material);
	/**
 * 添加 
 * @return
 */ 
	public  int addMaterial(Material material);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteMaterial(String id);

}

