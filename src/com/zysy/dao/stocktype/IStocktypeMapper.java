package com.zysy.dao.stocktype;
import java.util.List;
import java.util.Map;
import com.zysy.model.stocktype.Stocktype;
	public interface IStocktypeMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Stocktype selectstocktypeById(String id);
	/**
 	* 通过查询参数获取信息
 	* @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stocktype> selectstocktypeByParam(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountstocktypeByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updatestocktype(Stocktype stocktype);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addstocktype(Stocktype stocktype);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deletestocktype(String id);

}

