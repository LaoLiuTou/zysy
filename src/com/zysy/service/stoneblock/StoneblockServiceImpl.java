package com.zysy.service.stoneblock;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.zysy.dao.stock.IStockMapper;
import com.zysy.dao.stoneblock.IStoneblockMapper;
import com.zysy.model.stock.Stock;
import com.zysy.model.stoneblock.Stoneblock;
public class StoneblockServiceImpl  implements IStoneblockService {

	@Autowired
	private IStoneblockMapper iStoneblockMapper;
	@Autowired
	private IStockMapper iStockMapper;
	/**
 * 通过id选取
 * @return
 */
	public Stoneblock selectStoneblockById(String id){
		return iStoneblockMapper.selectstoneblockById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Stoneblock> selectStoneblockByParam(Map paramMap){ 
		return iStoneblockMapper.selectstoneblockByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountStoneblockByParam(Map paramMap){ 
		return iStoneblockMapper.selectCountstoneblockByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateStoneblock(Stoneblock stoneblock,Stock stock){
	    int result = 0;
	    stoneblock.setId(stock.getPid());
	 	result=iStoneblockMapper.updatestoneblock(stoneblock); 
	 	iStockMapper.updatestock(stock);
		return result; 
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addStoneblock(Stoneblock stoneblock,Stock stock){
	 	int result = 0;
	 	result=iStoneblockMapper.addstoneblock(stoneblock);
	 	stock.setPid(stoneblock.getId());
	 	iStockMapper.addstock(stock);
		return result;
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteStoneblock(String id){
		return iStoneblockMapper.deletestoneblock(id);
	}

}

