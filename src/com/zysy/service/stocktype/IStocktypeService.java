package com.zysy.service.stocktype;
import java.util.List;
import java.util.Map;
import com.zysy.model.stocktype.Stocktype;
public interface IStocktypeService {
	/**
 * 通过id选取
 * @return
 */
	public Stocktype selectStocktypeById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stocktype> selectStocktypeByParam(Map paramMap); 
	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStocktypeByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateStocktype(Stocktype stocktype);
	/**
 * 添加 
 * @return
 */ 
	public  int addStocktype(Stocktype stocktype);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteStocktype(String id);

}

