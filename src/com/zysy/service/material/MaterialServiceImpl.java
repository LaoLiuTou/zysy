package com.zysy.service.material;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.material.IMaterialMapper;
import com.zysy.model.material.Material;
public class MaterialServiceImpl  implements IMaterialService {

	@Autowired
	private IMaterialMapper iMaterialMapper;
	/**
 * 通过id选取
 * @return
 */
	public Material selectMaterialById(String id){
		return iMaterialMapper.selectmaterialById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Material> selectMaterialByParam(Map paramMap){ 
		return iMaterialMapper.selectmaterialByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountMaterialByParam(Map paramMap){ 
		return iMaterialMapper.selectCountmaterialByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateMaterial(Material material){
		return iMaterialMapper.updatematerial(material);
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addMaterial(Material material){
		return iMaterialMapper.addmaterial(material);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteMaterial(String id){
		return iMaterialMapper.deletematerial(id);
	}

}

