package com.zysy.service.matteboard;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.zysy.dao.matteboard.IMatteboardMapper;
import com.zysy.dao.stock.IStockMapper;
import com.zysy.model.matteboard.Matteboard;
import com.zysy.model.stock.Stock;
public class MatteboardServiceImpl  implements IMatteboardService {

	@Autowired
	private IMatteboardMapper iMatteboardMapper;
	@Autowired
	private IStockMapper iStockMapper;
	/**
 * 通过id选取
 * @return
 */
	public Matteboard selectMatteboardById(String id){
		return iMatteboardMapper.selectmatteboardById(id);
	}

	/**
 * 通过查询参数获取信息
 * @return
 */ 
 @SuppressWarnings("rawtypes")
	public List<Matteboard> selectMatteboardByParam(Map paramMap){ 
		return iMatteboardMapper.selectmatteboardByParam(paramMap);
	}

	/**
	* 通过查询参数获取总条数
	 * @return
	 */ 
 @SuppressWarnings("rawtypes")
	public int selectCountMatteboardByParam(Map paramMap){ 
		return iMatteboardMapper.selectCountmatteboardByParam(paramMap);
	}

	/**
 * 更新 
 * @return 
 */ 
 @Transactional
	public  int updateMatteboard(Matteboard matteboard,Stock stock){
	 	int result=0;
	 	matteboard.setId(stock.getPid());
	 	result=iMatteboardMapper.updatematteboard(matteboard);
	 	iStockMapper.updatestock(stock);
		/*if(matteboard.getBelowgradeblock()>0){
			Stock temp=stock;
			temp.setNumber((matteboard.getBlocknumber()-matteboard.getBelowgradeblock())+"");
			temp.setState(Long.parseLong("0"));
			iStockMapper.updatestock(temp);
			temp.setNumber((matteboard.getBelowgradeblock())+"");
			temp.setState(Long.parseLong("2"));
			iStockMapper.updatestock(temp);
		}
		else{
			iStockMapper.updatestock(stock);
		}*/
		return result;
	}

	/**
 * 添加 
 * @return
 */ 
 @Transactional
	public  int addMatteboard(Matteboard matteboard,Stock stock){
	 
	    int result=0;
	    
	    result=iMatteboardMapper.addmatteboard(matteboard);
	    stock.setPid(matteboard.getId());
		if(matteboard.getBelowgradeblock()>0){
			Stock temp=stock;
			temp.setNumber((matteboard.getBlocknumber()-matteboard.getBelowgradeblock())+"");
			//temp.setState(Long.parseLong("0"));
			temp.setQualify("是");
			temp.setDamage("否");
			iStockMapper.addstock(temp);
			temp.setNumber((matteboard.getBelowgradeblock())+"");
			//temp.setState(Long.parseLong("2"));
			temp.setQualify("否");
			temp.setDamage("是");
			iStockMapper.addstock(temp);
		}
		else{
			iStockMapper.addstock(stock);
		}
	 
		return result;
	}

	/**
 * 删除 
 * @return 
 */ 
 @Transactional
	public  int deleteMatteboard(String id){
		return iMatteboardMapper.deletematteboard(id);
	}

}

