package com.zysy.service.stock;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.stock.IStockMapper;
import com.zysy.model.stock.Stock;
public class StockServiceImpl  implements IStockService {

	@Autowired
	private IStockMapper iStockMapper;
	/**
 * 通过id选取
 * @return
 */
	public Stock selectStockById(String id){
		return iStockMapper.selectstockById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stock> selectStockByParam(Map paramMap){ 
		return iStockMapper.selectstockByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStockByParam(Map paramMap){ 
		return iStockMapper.selectCountstockByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateStock(Stock stock){
		return iStockMapper.updatestock(stock);
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addStock(Stock stock){
		return iStockMapper.addstock(stock);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteStock(String id){
		return iStockMapper.deletestock(id);
	}

}

