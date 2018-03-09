package com.zysy.dao.stock;
import java.util.List;
import java.util.Map;
import com.zysy.model.stock.Stock;
	public interface IStockMapper {
	/**
 	* 通过id选取
 	* @return
 	*/
	public Stock selectstockById(String id);
	/**
	 	* 查询唯一outtype
	 	* @return
	 */  
	public List<Stock> selectDistinctstockById(); 
	 /**
	  * 通过查询参数获取信息
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
	 public List<Stock> selectstockByParam(Map paramMap); 
	 /**
	  * 库存
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
    public List<Stock> selectReportStock(Map paramMap); 
	 /**
	  * 进出库
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
	 public List<Stock> selectStockInOut(Map paramMap); 
	 /**
	  * 车间进出
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
	 public List<Stock> selectWorkshopInOut(Map paramMap); 
	 /**
	  * 日报
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
	 public List<Stock> selectYield(Map paramMap); 
	 /**
	  * 破损
	  * @return
	  */ 
	 @SuppressWarnings("rawtypes")
	 public List<Stock> selectDamage(Map paramMap); 
	/**
		* 通过查询参数获取总条数
	    * @return
	*/ 
	@SuppressWarnings("rawtypes")
	public int selectCountstockByParam(Map paramMap); 
	/**
 	* 更新 
 	* @return 
 */ 
	public  int updatestock(Stock stock);
	/**
 	* 添加 
 	* @return
 */ 
	public  int addstock(Stock stock);
	/**
 	* 删除 
 	* @return 
 */ 
public  int deletestock(String id);

}

