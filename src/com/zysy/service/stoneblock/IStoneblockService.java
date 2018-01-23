package com.zysy.service.stoneblock;
import java.util.List;
import java.util.Map;
import com.zysy.model.stoneblock.Stoneblock;
public interface IStoneblockService {
	/**
 * 通过id选取
 * @return
 */
	public Stoneblock selectStoneblockById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stoneblock> selectStoneblockByParam(Map paramMap); 
	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStoneblockByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateStoneblock(Stoneblock stoneblock);
	/**
 * 添加 
 * @return
 */ 
	public  int addStoneblock(Stoneblock stoneblock);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteStoneblock(String id);

}

