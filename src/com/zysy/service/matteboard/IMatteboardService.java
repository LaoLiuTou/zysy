package com.zysy.service.matteboard;
import java.util.List;
import java.util.Map;
import com.zysy.model.matteboard.Matteboard;
import com.zysy.model.stock.Stock;
public interface IMatteboardService {
	/**
 * 通过id选取
 * @return
 */
	public Matteboard selectMatteboardById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Matteboard> selectMatteboardByParam(Map paramMap); 
	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountMatteboardByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateMatteboard(Matteboard matteboard,Stock stock);
	/**
 * 添加 
 * @return
 */ 
	public  int addMatteboard(Matteboard matteboard,Stock stock);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteMatteboard(String id);

}

