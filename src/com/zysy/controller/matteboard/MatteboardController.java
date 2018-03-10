package com.zysy.controller.matteboard;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zysy.service.matteboard.IMatteboardService;
import com.zysy.model.matteboard.Matteboard;
import com.zysy.model.stock.Stock;
import com.zysy.model.stoneblock.Stoneblock;
@Controller
public class MatteboardController {
	@Autowired
	private IMatteboardService iMatteboardService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Logger logger = Logger.getLogger("zysyLogger");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addMatteboard")
	@ResponseBody
	public Map add(Matteboard matteboard,Stock stock){
		Map resultMap=new HashMap();
		try {
			stock.setNumber(matteboard.getBlocknumber()+"");
			stock.setMsize(matteboard.getMsize());
			stock.setUnit("平方米");
			stock.setCode(matteboard.getCode());
			iMatteboardService.addMatteboard(matteboard,stock);
			resultMap.put("status", "0");
			resultMap.put("msg", matteboard.getId());
			logger.info("新建成功，主键："+matteboard.getId());
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "新建失败！");
			logger.info("新建失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addMulMatteboard")
	@ResponseBody
	public Map addMul(@RequestBody List<Matteboard> mbList){
		Map resultMap=new HashMap();
		try {
			String ids = "";
		    for(Matteboard mb:mbList){
		    	Stock stock = new Stock();
		    	 stock.setMsize(mb.getMsize());
				 stock.setUnit("平方米");
				 stock.setNumber(mb.getBlocknumber()+"");
				 stock.setC_id(mb.getC_id());
				 stock.setComment(mb.getComment());
				 stock.setDamage("否");
				 stock.setHeight(mb.getHeight());
				 stock.setMaterial("2");
				 stock.setStocktype(mb.getStocktype());
				 stock.setWorkshop(mb.getWorkshop());
				 stock.setNumber("1");
				 stock.setQualify("是");
				 stock.setState(Long.parseLong("0"));
				 stock.setCode(mb.getCode());
				 iMatteboardService.addMatteboard(mb,stock);
		    	ids+=mb.getId()+",";
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
	@RequestMapping("/deleteMatteboard")
	@ResponseBody
	public Map delete(Matteboard matteboard){
		Map resultMap=new HashMap();
		try {
			if(matteboard.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultDelete=iMatteboardService.deleteMatteboard(matteboard.getId()+"");
				resultMap.put("status", "0");
				resultMap.put("msg", "删除成功！");
				logger.info("删除成功，主键："+matteboard.getId());
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
	@RequestMapping("/selectMatteboard")
	@ResponseBody
	public Map select(Matteboard matteboard){
		Map resultMap=new HashMap();
		try {
			if(matteboard.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				Matteboard resultSelect=iMatteboardService.selectMatteboardById(matteboard.getId()+"");
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
	@RequestMapping("/updateMatteboard")
	@ResponseBody
	public Map update(Matteboard matteboard,Stock stock){
		Map resultMap=new HashMap();
		try {
			if(matteboard.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				stock.setNumber(matteboard.getBlocknumber()+"");
				stock.setMsize(matteboard.getMsize());
				stock.setUnit("平方米");
				int resultUpdate=iMatteboardService.updateMatteboard(matteboard,stock);
				resultMap.put("status", "0");
				resultMap.put("msg", "更新成功！");
				logger.info("更新成功，主键："+matteboard.getId());
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
	@RequestMapping("/reportMatteboard")
	@ResponseBody
	public Map report(HttpServletRequest request, HttpServletResponse response,Matteboard matteboard)
		throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			
				Map paramMap=new HashMap();
				
				paramMap.put("id",matteboard.getId());
				paramMap.put("sb_id",matteboard.getSb_id());
				paramMap.put("sb_code",matteboard.getSb_code());
				paramMap.put("sb_spec",matteboard.getSb_spec());
				paramMap.put("sb_cube",matteboard.getSb_cube());
				paramMap.put("code",matteboard.getCode());
				paramMap.put("auditor",matteboard.getAuditor());
				if(matteboard.getM_dt()!=null&&!matteboard.getM_dt().equals(""))
				paramMap.put("m_dt",matteboard.getM_dt());
				paramMap.put("workgroup",matteboard.getWorkgroup());
				paramMap.put("layer",matteboard.getLayer());
				paramMap.put("msize",matteboard.getMsize());
				paramMap.put("height",matteboard.getHeight());
				paramMap.put("blocknumber",matteboard.getBlocknumber());
				paramMap.put("square",matteboard.getSquare());
				paramMap.put("belowgradeblock",matteboard.getBelowgradeblock());
				paramMap.put("belowgradesquare",matteboard.getBelowgradesquare());
				paramMap.put("comment",matteboard.getComment());
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
				paramMap.put("state",matteboard.getState());
				paramMap.put("c_id",matteboard.getC_id());
				List<Matteboard> list=iMatteboardService.selectReportMatteboardByParam(paramMap);
				//int totalnumber=iMatteboardService.selectCountMatteboardByParam(paramMap);
				Map tempMap=new HashMap();
				resultMap.put("status", "0");
				//tempMap.put("num", totalnumber);
				tempMap.put("data", list);
				resultMap.put("msg", tempMap);
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "查询失败！");
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listMatteboard")
	@ResponseBody
	public Map list(HttpServletRequest request, HttpServletResponse response,Matteboard matteboard)
			throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			String page=request.getParameter("page");
			String size=request.getParameter("size");
			if(page!=null&&size!=null){
				Map paramMap=new HashMap();
				paramMap.put("fromPage",(Integer.parseInt(page)-1)*Integer.parseInt(size));
				paramMap.put("toPage",Integer.parseInt(size)); 
				paramMap.put("id",matteboard.getId());
				paramMap.put("sb_id",matteboard.getSb_id());
				paramMap.put("sb_code",matteboard.getSb_code());
				paramMap.put("sb_spec",matteboard.getSb_spec());
				paramMap.put("sb_cube",matteboard.getSb_cube());
				paramMap.put("code",matteboard.getCode());
				paramMap.put("auditor",matteboard.getAuditor());
				String m_dtFrom=request.getParameter("m_dtFrom");
				String m_dtTo=request.getParameter("m_dtTo");
				paramMap.put("m_dtFrom",m_dtFrom);
				paramMap.put("m_dtTo",m_dtTo);
				if(matteboard.getM_dt()!=null&&!matteboard.getM_dt().equals(""))
				paramMap.put("m_dt",matteboard.getM_dt());
				paramMap.put("workgroup",matteboard.getWorkgroup());
				paramMap.put("layer",matteboard.getLayer());
				paramMap.put("msize",matteboard.getMsize());
				String heightFrom=request.getParameter("heightFrom");
				String heightTo=request.getParameter("heightTo");
				paramMap.put("heightFrom",heightFrom);
				paramMap.put("heightTo",heightTo);
				paramMap.put("height",matteboard.getHeight());
				String blocknumberFrom=request.getParameter("blocknumberFrom");
				String blocknumberTo=request.getParameter("blocknumberTo");
				paramMap.put("blocknumberFrom",blocknumberFrom);
				paramMap.put("blocknumberTo",blocknumberTo);
				paramMap.put("blocknumber",matteboard.getBlocknumber());
				String squareFrom=request.getParameter("squareFrom");
				String squareTo=request.getParameter("squareTo");
				paramMap.put("squareFrom",squareFrom);
				paramMap.put("squareTo",squareTo);
				paramMap.put("square",matteboard.getSquare());
				paramMap.put("belowgradeblock",matteboard.getBelowgradeblock());
				paramMap.put("belowgradesquare",matteboard.getBelowgradesquare());
				paramMap.put("comment",matteboard.getComment());
				String priceFrom=request.getParameter("priceFrom");
				String priceTo=request.getParameter("priceTo");
				paramMap.put("priceFrom",priceFrom);
				paramMap.put("priceTo",priceTo);
				paramMap.put("price",matteboard.getPrice());
				String sumFrom=request.getParameter("sumFrom");
				String sumTo=request.getParameter("sumTo");
				paramMap.put("sumFrom",sumFrom);
				paramMap.put("sumTo",sumTo);
				paramMap.put("sum",matteboard.getSum());
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
				paramMap.put("state",matteboard.getState());
				paramMap.put("c_id",matteboard.getC_id());
				List<Matteboard> list=iMatteboardService.selectMatteboardByParam(paramMap);
				int totalnumber=iMatteboardService.selectCountMatteboardByParam(paramMap);
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
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
}
