package com.zysy.service.stock;
import java.util.List;
import java.util.Map;
import com.zysy.model.stock.Stock;
public interface IStockService {
	/**
 * 通过id选取
 * @return
 */
	public Stock selectStockById(String id);
	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stock> selectStockByParam(Map paramMap); 
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
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStockByParam(Map paramMap); 
	/**
 * 更新 
 * @return 
 */ 
	public  int updateStock(Stock stock);
	/**
 * 添加 
 * @return
 */ 
	public  int addStock(Stock stock);
	/**
 * 删除 
 * @return 
 */ 
	public  int deleteStock(String id);

}

