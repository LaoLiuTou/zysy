package com.zysy.service.stock;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.stock.IStockMapper;
import com.zysy.dao.stoneblock.IStoneblockMapper;
import com.zysy.model.stock.Stock;
public class StockServiceImpl  implements IStockService {
	@Autowired
	private IStoneblockMapper iStoneblockMapper;
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
  *库存
  * @return
  */ 
 @SuppressWarnings("rawtypes")
 public List<Stock> selectReportStock(Map paramMap){ 
	 return iStockMapper.selectReportStock(paramMap);
 }
 /**
  *进出库
  * @return
  */ 
 @SuppressWarnings("rawtypes")
 public List<Stock> selectStockInOut(Map paramMap){ 
	 return iStockMapper.selectStockInOut(paramMap);
 }
 /**
  *车间进出
  * @return
  */ 
 @SuppressWarnings("rawtypes")
 public List<Stock> selectWorkshopInOut(Map paramMap){ 
	 return iStockMapper.selectWorkshopInOut(paramMap);
 }
 /**
  *日报
  * @return
  */ 
 @SuppressWarnings("rawtypes")
 public List<Stock> selectYield(Map paramMap){ 
	 return iStockMapper.selectYield(paramMap);
 }
 /**
  *破损
  * @return
  */ 
 @SuppressWarnings("rawtypes")
 public List<Stock> selectDamage(Map paramMap){ 
	 return iStockMapper.selectDamage(paramMap);
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
	 	if((stock.getState()+"").equals("1")){
			stock.setNumber("-"+stock.getNumber());
		}
		return iStockMapper.addstock(stock);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteStock(String id){
	 	Stock stock=iStockMapper.selectstockById(id);
		int result= iStockMapper.deletestock(id);
		if(stock.getMaterial().equals("1")){
			iStoneblockMapper.deletestoneblock(stock.getPid()+"");
		}
		else if(stock.getMaterial().equals("2")){
			
		}
		else{
			
		}
		return result;
	}

}

