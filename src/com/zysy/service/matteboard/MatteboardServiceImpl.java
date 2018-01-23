package com.zysy.service.matteboard;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.matteboard.IMatteboardMapper;
import com.zysy.model.matteboard.Matteboard;
public class MatteboardServiceImpl  implements IMatteboardService {

	@Autowired
	private IMatteboardMapper iMatteboardMapper;
	/**
 * 通过id选取
 * @return
 */
	public Matteboard selectMatteboardById(String id){
		return iMatteboardMapper.selectmatteboardById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Matteboard> selectMatteboardByParam(Map paramMap){ 
		return iMatteboardMapper.selectmatteboardByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountMatteboardByParam(Map paramMap){ 
		return iMatteboardMapper.selectCountmatteboardByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateMatteboard(Matteboard matteboard){
		return iMatteboardMapper.updatematteboard(matteboard);
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addMatteboard(Matteboard matteboard){
		return iMatteboardMapper.addmatteboard(matteboard);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteMatteboard(String id){
		return iMatteboardMapper.deletematteboard(id);
	}

}

