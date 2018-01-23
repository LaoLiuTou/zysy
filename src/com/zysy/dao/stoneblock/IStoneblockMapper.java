package com.zysy.dao.stoneblock;
import java.util.List;
import java.util.Map;
import com.zysy.model.stoneblock.Stoneblock;
	public interface IStoneblockMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Stoneblock selectstoneblockById(String id);
	/**
 	* 通过查询参数获取信息
 	* @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stoneblock> selectstoneblockByParam(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountstoneblockByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updatestoneblock(Stoneblock stoneblock);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addstoneblock(Stoneblock stoneblock);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deletestoneblock(String id);

}

