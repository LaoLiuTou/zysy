package com.zysy.service.stocktype;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.stocktype.IStocktypeMapper;
import com.zysy.model.stocktype.Stocktype;
public class StocktypeServiceImpl  implements IStocktypeService {

	@Autowired
	private IStocktypeMapper iStocktypeMapper;
	/**
 * 通过id选取
 * @return
 */
	public Stocktype selectStocktypeById(String id){
		return iStocktypeMapper.selectstocktypeById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stocktype> selectStocktypeByParam(Map paramMap){ 
		return iStocktypeMapper.selectstocktypeByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStocktypeByParam(Map paramMap){ 
		return iStocktypeMapper.selectCountstocktypeByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateStocktype(Stocktype stocktype){
		return iStocktypeMapper.updatestocktype(stocktype);
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addStocktype(Stocktype stocktype){
		return iStocktypeMapper.addstocktype(stocktype);
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteStocktype(String id){
		return iStocktypeMapper.deletestocktype(id);
	}

}

