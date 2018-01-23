package com.zysy.service.workshop;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.workshop.IWorkshopMapper;
import com.zysy.model.workshop.Workshop;
public class WorkshopServiceImpl  implements IWorkshopService {

	@Autowired
	private IWorkshopMapper iWorkshopMapper;
	/**
 * 通过id选取
 * @return
 */
	public Workshop selectWorkshopById(String id){
		return iWorkshopMapper.selectworkshopById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Workshop> selectWorkshopByParam(Map paramMap){ 
		return iWorkshopMapper.selectworkshopByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountWorkshopByParam(Map paramMap){ 
		return iWorkshopMapper.selectCountworkshopByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateWorkshop(Workshop workshop){
		return iWorkshopMapper.updateworkshop(workshop);
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addWorkshop(Workshop workshop){
		return iWorkshopMapper.addworkshop(workshop);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteWorkshop(String id){
		return iWorkshopMapper.deleteworkshop(id);
	}

}

