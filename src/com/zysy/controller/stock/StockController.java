package com.zysy.controller.stock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zysy.service.stock.IStockService;
import com.zysy.utils.ExcelUtil;
import com.zysy.model.stock.Stock;
import com.zysy.model.stoneblock.Stoneblock;
@Controller
public class StockController {
	@Autowired
	private IStockService iStockService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
	@RequestMapping("/addMulStock")
	@ResponseBody
	public Map addMul(@RequestBody List<Stock> stockList){
		Map resultMap=new HashMap();
		try {
			String ids = "";
		    for(Stock stock:stockList){
		    	
		    	if(stock.getProcess()!=null&&stock.getProcess().equals("调厚")){
		    		if(stock.getMsize()!=null&&stock.getHeight()!=null&&stock.getNumber()!=null){
		    			String[] heights=stock.getHeight().split("-");
		    			if(heights.length==2){
		    				Map paramMap= new HashMap();
			    			paramMap.put("fromPage",0);
			    			paramMap.put("toPage",1);
			    			paramMap.put("code",stock.getCode()); 
							paramMap.put("msize",stock.getMsize()); 
							paramMap.put("height",heights[0]);
							List<Stock> list=iStockService.selectStockByParam(paramMap);
							if(list.size()>0){
								Stock tempStock=list.get(0);
								tempStock.setNumber((Integer.parseInt(tempStock.getNumber())-Integer.parseInt(stock.getNumber())+""));
								iStockService.updateStock(tempStock);
								stock.setHeight(heights[1]);
								iStockService.addStock(stock);
							}
							
		    			}
		    			
		    		}
		    	}
		    	else{
		    		iStockService.addStock(stock);
		    	}
		    	
		    	
		    	ids+=stock.getId()+",";
		    } 
			resultMap.put("status", "0");
			resultMap.put("msg", ids);
			logger.info("新建成功，主键："+ids);
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
				paramMap.put("msize",stock.getMsize());
				String heightFrom=request.getParameter("heightFrom");
				String heightTo=request.getParameter("heightTo");
				paramMap.put("heightFrom",heightFrom);
				paramMap.put("heightTo",heightTo);
				paramMap.put("height",stock.getHeight());
				String numberFrom=request.getParameter("numberFrom");
				String numberTo=request.getParameter("numberTo");
				paramMap.put("numberFrom",numberFrom);
				paramMap.put("numberTo",numberTo);
				paramMap.put("number",stock.getNumber());
				paramMap.put("comment",stock.getComment());
				paramMap.put("qualify",stock.getQualify());
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
				paramMap.put("c_id",stock.getC_id());
				   
				 
				  
				 

				paramMap.put("pid",stock.getPid());
				paramMap.put("process",stock.getProcess());
				paramMap.put("packaging",stock.getPackaging());
				paramMap.put("workgroup",stock.getWorkgroup());
				paramMap.put("ordercode",stock.getOrdercode());
				paramMap.put("outtype",stock.getOuttype());
				paramMap.put("code",stock.getCode());
				String m_dtFrom=request.getParameter("m_dtFrom");
				String m_dtTo=request.getParameter("m_dtTo");
				if(m_dtFrom!=null&&!m_dtFrom.equals(""))
				paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
				if(m_dtTo!=null&&!m_dtTo.equals(""))
				paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
				paramMap.put("auditor",stock.getAuditor());
				paramMap.put("worker",stock.getWorker());
				String spriceFrom=request.getParameter("spriceFrom");
				String spriceTo=request.getParameter("spriceTo");
				paramMap.put("spriceFrom",spriceFrom);
				paramMap.put("spriceTo",spriceTo);
				paramMap.put("sprice",stock.getSprice());
				String ssumFrom=request.getParameter("ssumFrom");
				String ssumTo=request.getParameter("ssumTo");
				paramMap.put("ssumFrom",ssumFrom);
				paramMap.put("ssumTo",ssumTo);
				paramMap.put("ssum",stock.getSsum());
				
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/reportStock")
	@ResponseBody
	public Map reportStock(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectReportStock(paramMap);
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/reportStockInOut")
	@ResponseBody
	public Map reportStockInOut(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectStockInOut(paramMap);
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/reportWorkshopInOut")
	@ResponseBody
	public Map reportWorkshopInOut(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectWorkshopInOut(paramMap);
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/reportYield")
	@ResponseBody
	public Map reportYield(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectYield(paramMap);
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/reportDamage")
	@ResponseBody
	public Map reportDamage(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectDamage(paramMap);
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/distinctType")
	@ResponseBody
	public Map distinctType(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
			List<Stock> list=iStockService.selectDistinctstockById();
			resultMap.put("status", "0");
			resultMap.put("msg", list);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportReportYield")
	public @ResponseBody void exportReportYield(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		try {
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectYield(paramMap);
			 
			List<String[]> exportList = new ArrayList<>();
			for(int index=0;index<list.size();index++){
				Stock st = list.get(index);
				String[] strings = {(index+1)+"", st.getOuttype(),st.getHeight(),st.getUnit(),
						st.getSum_in(),st.getSum_out(),st.getSum_number()};
				exportList.add(strings);
			}
			ServletOutputStream out=response.getOutputStream();
			String fileName = "产量日报"+sdf.format(new Date());
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
			String[] titles = { "序号","类型", "厚度", "单位", "合格平方数", "不合格平方数", "计划外入库数量"}; 
		 
			ExcelUtil.export(titles, out, exportList);
			
		} catch (Exception e) {
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		 
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportReportWorkshopInOut")
	
	public @ResponseBody void exportReportWorkshopInOut(HttpServletRequest request, HttpServletResponse response,Stock stock)
			throws ServletException, IOException {
		try {
			
			Map paramMap=new HashMap();
			paramMap.put("id",stock.getId());
			paramMap.put("material",stock.getMaterial());
			paramMap.put("stocktype",stock.getStocktype());
			paramMap.put("workshop",stock.getWorkshop());
			paramMap.put("unit",stock.getUnit());
			paramMap.put("msize",stock.getMsize());
			paramMap.put("height",stock.getHeight());
			paramMap.put("number",stock.getNumber());
			paramMap.put("comment",stock.getComment());
			paramMap.put("qualify",stock.getQualify());
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
			paramMap.put("c_id",stock.getC_id());
			
			paramMap.put("pid",stock.getPid());
			paramMap.put("process",stock.getProcess());
			paramMap.put("packaging",stock.getPackaging());
			paramMap.put("workgroup",stock.getWorkgroup());
			paramMap.put("ordercode",stock.getOrdercode());
			paramMap.put("outtype",stock.getOuttype());
			paramMap.put("code",stock.getCode());
			String m_dtFrom=request.getParameter("m_dtFrom");
			String m_dtTo=request.getParameter("m_dtTo");
			if(m_dtFrom!=null&&!m_dtFrom.equals(""))
			paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
			if(m_dtTo!=null&&!m_dtTo.equals(""))
			paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
			paramMap.put("auditor",stock.getAuditor());
			paramMap.put("worker",stock.getWorker());
			String spriceFrom=request.getParameter("spriceFrom");
			String spriceTo=request.getParameter("spriceTo");
			paramMap.put("spriceFrom",spriceFrom);
			paramMap.put("spriceTo",spriceTo);
			paramMap.put("sprice",stock.getSprice());
			String ssumFrom=request.getParameter("ssumFrom");
			String ssumTo=request.getParameter("ssumTo");
			paramMap.put("ssumFrom",ssumFrom);
			paramMap.put("ssumTo",ssumTo);
			paramMap.put("ssum",stock.getSsum());
			
			String stateIn=request.getParameter("statein");
			paramMap.put("statein",stateIn);
			List<Stock> list=iStockService.selectWorkshopInOut(paramMap);


			List<String[]> exportList = new ArrayList<>();
			for(int index=0;index<list.size();index++){
				Stock st = list.get(index);
				System.out.println(st.getSprice());
				 if(st.getSprice()==null){
					 st.setSprice("0");
				 }
				 if(st.getSsum()==null){
					 st.setSsum("0");
				 }
				String[] strings = {(index+1)+"", st.getOuttype(),st.getUnit(),
						st.getSum_in(),st.getSum_out(),st.getSprice(),
						String.format("%.2f",Float.parseFloat(st.getSum_in())-Float.parseFloat(st.getSum_out())-Float.parseFloat(st.getSprice())),
						st.getSsum(),String.format("%.2f",Float.parseFloat(st.getSum_in())-Float.parseFloat(st.getSsum()))};
				exportList.add(strings);
			}
			ServletOutputStream out=response.getOutputStream();
			String fileName = "车间进出配比"+sdf.format(new Date());
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
			String[] titles = { "序号","类型", "单位", "领料数量", "出库数量", "破损数量", "差", "计划外入库数", "出库合计"}; 
		  
			ExcelUtil.export(titles, out, exportList);
			
		} catch (Exception e) {
			 
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		 
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportListStock")
	@ResponseBody
	public Map exportList(HttpServletRequest request, HttpServletResponse response,Stock stock)
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
				paramMap.put("msize",stock.getMsize());
				String heightFrom=request.getParameter("heightFrom");
				String heightTo=request.getParameter("heightTo");
				paramMap.put("heightFrom",heightFrom);
				paramMap.put("heightTo",heightTo);
				paramMap.put("height",stock.getHeight());
				String numberFrom=request.getParameter("numberFrom");
				String numberTo=request.getParameter("numberTo");
				paramMap.put("numberFrom",numberFrom);
				paramMap.put("numberTo",numberTo);
				paramMap.put("number",stock.getNumber());
				paramMap.put("comment",stock.getComment());
				paramMap.put("qualify",stock.getQualify());
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
				paramMap.put("c_id",stock.getC_id());
				   
				 
				  
				 

				paramMap.put("pid",stock.getPid());
				paramMap.put("process",stock.getProcess());
				paramMap.put("packaging",stock.getPackaging());
				paramMap.put("workgroup",stock.getWorkgroup());
				paramMap.put("ordercode",stock.getOrdercode());
				paramMap.put("outtype",stock.getOuttype());
				paramMap.put("code",stock.getCode());
				String m_dtFrom=request.getParameter("m_dtFrom");
				String m_dtTo=request.getParameter("m_dtTo");
				if(m_dtFrom!=null&&!m_dtFrom.equals(""))
				paramMap.put("m_dtFrom", sdf.parse(m_dtFrom+" 00:00:00"));
				if(m_dtTo!=null&&!m_dtTo.equals(""))
				paramMap.put("m_dtTo", sdf.parse(m_dtTo+" 23:59:59"));
				paramMap.put("auditor",stock.getAuditor());
				paramMap.put("worker",stock.getWorker());
				String spriceFrom=request.getParameter("spriceFrom");
				String spriceTo=request.getParameter("spriceTo");
				paramMap.put("spriceFrom",spriceFrom);
				paramMap.put("spriceTo",spriceTo);
				paramMap.put("sprice",stock.getSprice());
				String ssumFrom=request.getParameter("ssumFrom");
				String ssumTo=request.getParameter("ssumTo");
				paramMap.put("ssumFrom",ssumFrom);
				paramMap.put("ssumTo",ssumTo);
				paramMap.put("ssum",stock.getSsum());
				
				List<Stock> list=iStockService.selectStockByParam(paramMap);
				 
				float  sum_1=0,sum_2=0,sum_3=0,sum_4=0,sum_5=0,sum_6=0;
				List<String[]> exportList = new ArrayList<>();
				for(int index=0;index<list.size();index++){
					Stock st = list.get(index);
					String length=st.getMsize().split("\\*")[0],width=st.getMsize().split("\\*")[1];
					sum_1+=Float.parseFloat(st.getNumber());
	                sum_2+=Float.parseFloat(length)*Float.parseFloat(width)*Float.parseFloat(st.getNumber())/1000000;
					if(Integer.parseInt(length)%300>0){
	                    length=(Integer.parseInt(length)-Integer.parseInt(length)%300+300)+"";
	                }
	                if(Integer.parseInt(width)%300>0){
	                    width=(Integer.parseInt(width)-Integer.parseInt(width)%300+300)+"";
	                }
	                sum_3+=Float.parseFloat(length)*Float.parseFloat(width)*Float.parseFloat(st.getNumber())/1000000;
					
	                if(st.getSprice()!=null)
	                sum_4+=Float.parseFloat(st.getSprice());
	                if(st.getSsum()!=null)
	                sum_5+=Float.parseFloat(st.getSsum());
	                if(st.getYanmi()!=null)
	                sum_6+=Float.parseFloat(st.getYanmi());
					if(stock.getState().equals("1")){
						if(stock.getOuttype().equals("切机")){
							String[] strings = {(index+1)+"", sdf.format(st.getM_dt()),st.getCode(),st.getProcess(),
									st.getMsize(),st.getHeight(),st.getNumber(),
									String.format("%.2f",Float.parseFloat(st.getMsize().split("\\*")[0])*Float.parseFloat(st.getMsize().split("\\*")[1])*Float.parseFloat(st.getNumber())/1000000),
									String.format("%.2f",Float.parseFloat(length)*Float.parseFloat(width)*Float.parseFloat(st.getNumber())/1000000),
									st.getComment(),st.getWorker(),st.getAuditor(),st.getYanmi(),st.getSprice(),st.getSsum()};
							exportList.add(strings);
						}
						else if(stock.getOuttype().equals("外加工")){
							String[] strings = {(index+1)+"", sdf.format(st.getM_dt()),st.getCode(),st.getProcess(),
									st.getMsize(),st.getHeight(),st.getNumber(),
									String.format("%.2f",Float.parseFloat(st.getMsize().split("\\*")[0])*Float.parseFloat(st.getMsize().split("\\*")[1])*Float.parseFloat(st.getNumber())/1000000),
									st.getYanmi(),
									st.getComment(),st.getWorker(),st.getAuditor(),st.getSprice(),st.getSsum()};
							exportList.add(strings);
						}
						else{
							String[] strings = {(index+1)+"", sdf.format(st.getM_dt()),st.getCode(),st.getProcess(),
									st.getMsize(),st.getHeight(),st.getNumber(),
									String.format("%.2f",Float.parseFloat(st.getMsize().split("\\*")[0])*Float.parseFloat(st.getMsize().split("\\*")[1])*Float.parseFloat(st.getNumber())/1000000),
									String.format("%.2f",Float.parseFloat(length)*Float.parseFloat(width)*Float.parseFloat(st.getNumber())/1000000),
									st.getComment(),st.getWorker(),st.getAuditor(),st.getSprice(),st.getSsum()};
							exportList.add(strings);
						}
					}
					else{
						
						String[] strings = {(index+1)+"", sdf.format(st.getM_dt()),st.getCode(),st.getOuttype(),
								st.getMaterial_name(),st.getMsize(),st.getHeight(),st.getNumber(),
								String.format("%.2f",Float.parseFloat(st.getMsize().split("\\*")[0])*Float.parseFloat(st.getMsize().split("\\*")[1])*Float.parseFloat(st.getNumber())/1000000),
								st.getComment(),st.getWorker(),st.getAuditor()};
						exportList.add(strings);
					}
					
				}
				if(stock.getState().equals("1")){
					if(stock.getOuttype().equals("切机")){
						String[] strings = {"合计", "", "", "", "","", String.format("%.0f", sum_1), String.format("%.2f", sum_2),String.format("%.2f", sum_3),
								"","","", String.format("%.2f", sum_6),String.format("%.2f", sum_4), String.format("%.2f", sum_5)};
						exportList.add(strings);
					}
					else if(stock.getOuttype().equals("外加工")){
						String[] strings = {"合计", "", "", "", "","", String.format("%.0f", sum_1), String.format("%.2f", sum_2),String.format("%.2f", sum_6),
								"","","", String.format("%.2f", sum_4), String.format("%.2f", sum_5)};
						exportList.add(strings);
					}
					else{
						String[] strings = {"合计", "", "", "", "","", String.format("%.0f", sum_1), String.format("%.2f", sum_2),String.format("%.2f", sum_3),
								"","","", String.format("%.2f", sum_4), String.format("%.2f", sum_5)};
						exportList.add(strings);
					}
				}
				else{ 
					String[] strings = {"合计", "","", "", "", "","", String.format("%.0f", sum_1), String.format("%.2f", sum_2),"","",""};
					exportList.add(strings);
				}
				
				
				
				ServletOutputStream out=response.getOutputStream();
				String fileName = sdf.format(new Date());
				if(stock.getState().equals("1")){
					if(stock.getOuttype().equals("磨机")){
						fileName = "磨机加工明细表"+fileName;
					}
					else if(stock.getOuttype().equals("火烧")){
						fileName = "火烧加工明细表"+fileName;
					}
					else if(stock.getOuttype().equals("切机")){
						fileName = "切机加工明细表"+fileName;
					}
					else if(stock.getOuttype().equals("外加工")){
						fileName = "外加工明细表"+fileName;
					}
				}
				else{
					fileName = "领料明细表"+fileName;
				}
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
				if(stock.getState().equals("1")){
					if(stock.getOuttype().equals("切机")){
						String[] titles = { "序号","日期", "单据号", "加工方式", "规格", "厚度", 
								"块数", "平方数", "平方数(毛尺)", "备注", "加工人", "验收人","延米", "工价", "工资"}; 
						ExcelUtil.export(titles, out, exportList);
					}
					else if(stock.getOuttype().equals("外加工")){
						String[] titles = { "序号","日期", "单据号", "加工方式", "规格", "厚度", 
								"块数", "平方数", "延米", "备注", "加工人", "验收人", "工价", "工资"}; 
						ExcelUtil.export(titles, out, exportList);
					}
					else{
						String[] titles = { "序号","日期", "单据号", "加工方式", "规格", "厚度", 
								"块数", "平方数", "平方数(毛尺)", "备注", "加工人", "验收人", "工价", "工资"}; 
						ExcelUtil.export(titles, out, exportList);
					}
				}
				else{
					String[] titles = { "序号","日期", "单据号", "领料车间","材料名称", "材料规格", "厚度", 
							"块数", "平方数",  "备注", "加工人", "验收人"}; 
					ExcelUtil.export(titles, out, exportList);
					 
				}
				
				 
				
               
				
				
			}
			else{ 
				logger.info("分页参数不能为空！");
			}
		} catch (Exception e) {
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
}
