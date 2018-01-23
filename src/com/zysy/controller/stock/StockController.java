package com.zysy.controller.stock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zysy.service.stock.IStockService;
import com.zysy.model.stock.Stock;
@Controller
public class StockController {
	@Autowired
	private IStockService iStockService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Logger logger = Logger.getLogger("zysyLogger");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addStock")
	@ResponseBody
	public Map add(Stock stock){
		Map resultMap=new HashMap();
		try {
			iStockService.addStock(stock);
			resultMap.put("status", "0");
			resultMap.put("msg", stock.getId());
			logger.info("新建成功，主键："+stock.getId());
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "新建失败！");
			logger.info("新建失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteStock")
	@ResponseBody
	public Map delete(Stock stock){
		Map resultMap=new HashMap();
		try {
			if(stock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultDelete=iStockService.deleteStock(stock.getId()+"");
				resultMap.put("status", "0");
				resultMap.put("msg", "删除成功！");
				logger.info("删除成功，主键："+stock.getId());
			}
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "删除失败！");
			logger.info("删除失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectStock")
	@ResponseBody
	public Map select(Stock stock){
		Map resultMap=new HashMap();
		try {
			if(stock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				Stock resultSelect=iStockService.selectStockById(stock.getId()+"");
				resultMap.put("status", "0");
				resultMap.put("msg", resultSelect);
			}
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/updateStock")
	@ResponseBody
	public Map update(Stock stock){
		Map resultMap=new HashMap();
		try {
			if(stock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultUpdate=iStockService.updateStock(stock);
				resultMap.put("status", "0");
				resultMap.put("msg", "更新成功！");
				logger.info("更新成功，主键："+stock.getId());
			}
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "更新失败！");
			logger.info("更新失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listStock")
	@ResponseBody
	public Map list(HttpServletRequest request, HttpServletResponse response,Stock stock)
		throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			String page=request.getParameter("page");
			String size=request.getParameter("size");
			if(page!=null&&size!=null){
				Map paramMap=new HashMap();
				paramMap.put("fromPage",(Integer.parseInt(page)-1)*Integer.parseInt(size));
				paramMap.put("toPage",Integer.parseInt(size)); 
				paramMap.put("id",stock.getId());
				paramMap.put("material",stock.getMaterial());
				paramMap.put("stocktype",stock.getStocktype());
				paramMap.put("workshop",stock.getWorkshop());
				paramMap.put("unit",stock.getUnit());
				paramMap.put("size",stock.getSize());
				paramMap.put("height",stock.getHeight());
				paramMap.put("number",stock.getNumber());
				paramMap.put("comment",stock.getComment());
				paramMap.put("damage",stock.getDamage());
				paramMap.put("state",stock.getState());
				String c_dtFrom=request.getParameter("c_dtFrom");
				String c_dtTo=request.getParameter("c_dtTo");
				if(c_dtFrom!=null&&!c_dtFrom.equals(""))
				paramMap.put("c_dtFrom", sdf.parse(c_dtFrom));
				if(c_dtTo!=null&&!c_dtTo.equals(""))
				paramMap.put("c_dtTo", sdf.parse(c_dtTo));
				String u_dtFrom=request.getParameter("u_dtFrom");
				String u_dtTo=request.getParameter("u_dtTo");
				if(u_dtFrom!=null&&!u_dtFrom.equals(""))
				paramMap.put("u_dtFrom", sdf.parse(u_dtFrom));
				if(u_dtTo!=null&&!u_dtTo.equals(""))
				paramMap.put("u_dtTo", sdf.parse(u_dtTo));
				List<Stock> list=iStockService.selectStockByParam(paramMap);
				int totalnumber=iStockService.selectCountStockByParam(paramMap);
				Map tempMap=new HashMap();
				resultMap.put("status", "0");
				tempMap.put("num", totalnumber);
				tempMap.put("data", list);
				resultMap.put("msg", tempMap);
			}
			else{
				resultMap.put("status", "-1");
				resultMap.put("msg", "分页参数不能为空！");
			}
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
}
