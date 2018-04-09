package com.zysy.controller.stoneblock;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.zysy.model.stock.Stock;
import com.zysy.model.stoneblock.Stoneblock;
import com.zysy.service.stoneblock.IStoneblockService;
import com.zysy.utils.ExcelUtil;
@Controller
public class StoneblockController {
	@Autowired
	private IStoneblockService iStoneblockService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Logger logger = Logger.getLogger("zysyLogger");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addStoneblock")
	@ResponseBody
	public Map add(Stoneblock stoneblock,Stock stock){
		Map resultMap=new HashMap();
		try {
			 stock.setMsize(stoneblock.getLength()+"*"+stoneblock.getWidth());
			 stock.setUnit("立方米");
			 stock.setNumber(stoneblock.getBlocknumber()+"");
			 stock.setCode(stoneblock.getCode());
			iStoneblockService.addStoneblock(stoneblock,stock);
			resultMap.put("status", "0");
			resultMap.put("msg", stoneblock.getId());
			logger.info("新建成功，主键："+stoneblock.getId());
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "新建失败！");
			logger.info("新建失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addMulStoneblock")
	@ResponseBody
	public Map addMul(@RequestBody List<Stoneblock> sbList){
		Map resultMap=new HashMap();
		try {
			String ids = "";
		    for(Stoneblock sb:sbList){
		    	Stock stock = new Stock();
		    	 stock.setMsize(sb.getLength()+"*"+sb.getWidth());
				 stock.setUnit("立方米");
				 if(sb.getBlocknumber()==null){
					stock.setNumber(1+"");
				}
				else{
					stock.setNumber(sb.getBlocknumber()+"");
				}
				  
				 stock.setC_id(sb.getC_id());
				 stock.setComment(sb.getComment());
				 stock.setDamage("否");
				 stock.setHeight(sb.getHeight());
				 stock.setMaterial("1");
				 stock.setStocktype(sb.getStocktype());
				 stock.setWorkshop(sb.getWorkshop());
				 stock.setNumber("1");
				 stock.setQualify("是");
				 stock.setState(Long.parseLong("0"));
				 stock.setCode(sb.getCode());

		    	iStoneblockService.addStoneblock(sb,stock);
		    	ids+=sb.getId()+",";
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
	@RequestMapping("/deleteStoneblock")
	@ResponseBody
	public Map delete(Stoneblock stoneblock){
		Map resultMap=new HashMap();
		try {
			if(stoneblock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultDelete=iStoneblockService.deleteStoneblock(stoneblock.getId()+"");
				resultMap.put("status", "0");
				resultMap.put("msg", "删除成功！");
				logger.info("删除成功，主键："+stoneblock.getId());
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
	@RequestMapping("/selectStoneblock")
	@ResponseBody
	public Map select(Stoneblock stoneblock){
		Map resultMap=new HashMap();
		try {
			if(stoneblock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				Stoneblock resultSelect=iStoneblockService.selectStoneblockById(stoneblock.getId()+"");
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
	@RequestMapping("/updateStoneblock")
	@ResponseBody
	public Map update(Stoneblock stoneblock,Stock stock){
		Map resultMap=new HashMap();
		try {
			if(stoneblock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				stock.setMsize(stoneblock.getLength()+"*"+stoneblock.getWidth());
				stock.setUnit("立方米");
				if(stoneblock.getBlocknumber()==null){
					stock.setNumber(1+"");
				}
				else{
					stock.setNumber(stoneblock.getBlocknumber()+"");
				}
				
				int resultUpdate=iStoneblockService.updateStoneblock(stoneblock,stock);
				resultMap.put("status", "0");
				resultMap.put("msg", "更新成功！");
				logger.info("更新成功，主键："+stoneblock.getId());
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
	@RequestMapping("/singleupdateStoneblock")
	@ResponseBody
	public Map singleupdate(Stoneblock stoneblock){
		Map resultMap=new HashMap();
		try {
			
				int resultUpdate=iStoneblockService.updateStoneblock(stoneblock);
				resultMap.put("status", "0");
				resultMap.put("msg", "更新成功！");
				logger.info("更新成功，主键："+stoneblock.getId());
			
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "更新失败！");
			logger.info("更新失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listStoneblock")
	@ResponseBody
	public Map list(HttpServletRequest request, HttpServletResponse response,Stoneblock stoneblock)
		throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			String page=request.getParameter("page");
			String size=request.getParameter("size");
			if(page!=null&&size!=null){
				Map paramMap=new HashMap();
				paramMap.put("fromPage",(Integer.parseInt(page)-1)*Integer.parseInt(size));
				paramMap.put("toPage",Integer.parseInt(size)); 
				paramMap.put("id",stoneblock.getId());
				paramMap.put("code",stoneblock.getCode());
				String s_dtFrom=request.getParameter("s_dtFrom");
				String s_dtTo=request.getParameter("s_dtTo");
				if(s_dtFrom!=null&&!s_dtFrom.equals(""))
				paramMap.put("s_dtFrom", sdf.parse(s_dtFrom+" 00:00:00"));
				if(s_dtTo!=null&&!s_dtTo.equals(""))
				paramMap.put("s_dtTo", sdf.parse(s_dtTo+" 23:59:59"));
				if(stoneblock.getS_dt()!=null&&!stoneblock.getS_dt().equals(""))
				paramMap.put("s_dt",stoneblock.getS_dt());
				paramMap.put("source",stoneblock.getSource());
				paramMap.put("place",stoneblock.getPlace());
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
				paramMap.put("number",stoneblock.getNumber());
				String lengthFrom=request.getParameter("lengthFrom");
				String lengthTo=request.getParameter("lengthTo");
				paramMap.put("lengthFrom",lengthFrom);
				paramMap.put("lengthTo",lengthTo);
				paramMap.put("length",stoneblock.getLength());
				String widthFrom=request.getParameter("widthFrom");
				String widthTo=request.getParameter("widthTo");
				paramMap.put("widthFrom",widthFrom);
				paramMap.put("widthTo",widthTo);
				paramMap.put("width",stoneblock.getWidth());
				String heightFrom=request.getParameter("heightFrom");
				String heightTo=request.getParameter("heightTo");
				paramMap.put("heightFrom",heightFrom);
				paramMap.put("heightTo",heightTo);
				paramMap.put("height",stoneblock.getHeight());
				String cubeFrom=request.getParameter("cubeFrom");
				String cubeTo=request.getParameter("cubeTo");
				paramMap.put("cubeFrom",cubeFrom);
				paramMap.put("cubeTo",cubeTo);
				paramMap.put("cube",stoneblock.getCube());
				String priceFrom=request.getParameter("priceFrom");
				String priceTo=request.getParameter("priceTo");
				paramMap.put("priceFrom",priceFrom);
				paramMap.put("priceTo",priceTo);
				paramMap.put("price",stoneblock.getPrice());
				String sumFrom=request.getParameter("sumFrom");
				String sumTo=request.getParameter("sumTo");
				paramMap.put("sumFrom",sumFrom);
				paramMap.put("sumTo",sumTo);
				paramMap.put("sum",stoneblock.getSum());
				paramMap.put("platenumber",stoneblock.getPlatenumber());
				paramMap.put("accountdiff",stoneblock.getAccountdiff());
				paramMap.put("auditor",stoneblock.getAuditor());
				paramMap.put("editor",stoneblock.getEditor());
				paramMap.put("comment",stoneblock.getComment());
				paramMap.put("state",stoneblock.getState());
				paramMap.put("blocknumber",stoneblock.getBlocknumber());
				paramMap.put("c_id",stoneblock.getC_id());
				
				String rz_dtFrom=request.getParameter("rz_dtFrom");
				String rz_dtTo=request.getParameter("rz_dtTo");
				if(rz_dtFrom!=null&&!rz_dtFrom.equals(""))
				paramMap.put("rz_dtFrom", sdf.parse(rz_dtFrom+" 00:00:00"));
				if(rz_dtTo!=null&&!rz_dtTo.equals(""))
				paramMap.put("rz_dtTo", sdf.parse(rz_dtTo+" 23:59:59"));
				
				String yf_dtFrom=request.getParameter("yf_dtFrom");
				String yf_dtTo=request.getParameter("yf_dtTo");
				if(yf_dtFrom!=null&&!yf_dtFrom.equals(""))
				paramMap.put("yf_dtFrom", sdf.parse(yf_dtFrom+" 00:00:00"));
				if(yf_dtTo!=null&&!yf_dtTo.equals(""))
				paramMap.put("yf_dtTo", sdf.parse(yf_dtTo+" 23:59:59"));
				
				List<Stoneblock> list=iStoneblockService.selectStoneblockByParam(paramMap);
				int totalnumber=iStoneblockService.selectCountStoneblockByParam(paramMap);
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
	@RequestMapping("/exportStoneblock")
	public @ResponseBody void export(HttpServletRequest request, HttpServletResponse response,Stoneblock stoneblock)
			throws ServletException, IOException {
		try {
			String page=request.getParameter("page");
			String size=request.getParameter("size");
			if(page!=null&&size!=null){
				Map paramMap=new HashMap();
				paramMap.put("fromPage",(Integer.parseInt(page)-1)*Integer.parseInt(size));
				paramMap.put("toPage",Integer.parseInt(size)); 
				paramMap.put("id",stoneblock.getId());
				paramMap.put("code",stoneblock.getCode());
				String s_dtFrom=request.getParameter("s_dtFrom");
				String s_dtTo=request.getParameter("s_dtTo");
				if(s_dtFrom!=null&&!s_dtFrom.equals(""))
					paramMap.put("s_dtFrom", sdf.parse(s_dtFrom+" 00:00:00"));
				if(s_dtTo!=null&&!s_dtTo.equals(""))
					paramMap.put("s_dtTo", sdf.parse(s_dtTo+" 23:59:59"));
				if(stoneblock.getS_dt()!=null&&!stoneblock.getS_dt().equals(""))
					paramMap.put("s_dt",stoneblock.getS_dt());
				paramMap.put("source",stoneblock.getSource());
				paramMap.put("place",stoneblock.getPlace());
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
				paramMap.put("number",stoneblock.getNumber());
				String lengthFrom=request.getParameter("lengthFrom");
				String lengthTo=request.getParameter("lengthTo");
				paramMap.put("lengthFrom",lengthFrom);
				paramMap.put("lengthTo",lengthTo);
				paramMap.put("length",stoneblock.getLength());
				String widthFrom=request.getParameter("widthFrom");
				String widthTo=request.getParameter("widthTo");
				paramMap.put("widthFrom",widthFrom);
				paramMap.put("widthTo",widthTo);
				paramMap.put("width",stoneblock.getWidth());
				String heightFrom=request.getParameter("heightFrom");
				String heightTo=request.getParameter("heightTo");
				paramMap.put("heightFrom",heightFrom);
				paramMap.put("heightTo",heightTo);
				paramMap.put("height",stoneblock.getHeight());
				String cubeFrom=request.getParameter("cubeFrom");
				String cubeTo=request.getParameter("cubeTo");
				paramMap.put("cubeFrom",cubeFrom);
				paramMap.put("cubeTo",cubeTo);
				paramMap.put("cube",stoneblock.getCube());
				String priceFrom=request.getParameter("priceFrom");
				String priceTo=request.getParameter("priceTo");
				paramMap.put("priceFrom",priceFrom);
				paramMap.put("priceTo",priceTo);
				paramMap.put("price",stoneblock.getPrice());
				String sumFrom=request.getParameter("sumFrom");
				String sumTo=request.getParameter("sumTo");
				paramMap.put("sumFrom",sumFrom);
				paramMap.put("sumTo",sumTo);
				paramMap.put("sum",stoneblock.getSum());
				paramMap.put("platenumber",stoneblock.getPlatenumber());
				paramMap.put("accountdiff",stoneblock.getAccountdiff());
				paramMap.put("auditor",stoneblock.getAuditor());
				paramMap.put("editor",stoneblock.getEditor());
				paramMap.put("comment",stoneblock.getComment());
				paramMap.put("state",stoneblock.getState());
				paramMap.put("blocknumber",stoneblock.getBlocknumber());
				paramMap.put("c_id",stoneblock.getC_id());
				
				String rz_dtFrom=request.getParameter("rz_dtFrom");
				String rz_dtTo=request.getParameter("rz_dtTo");
				if(rz_dtFrom!=null&&!rz_dtFrom.equals(""))
					paramMap.put("rz_dtFrom", sdf.parse(rz_dtFrom+" 00:00:00"));
				if(rz_dtTo!=null&&!rz_dtTo.equals(""))
					paramMap.put("rz_dtTo", sdf.parse(rz_dtTo+" 23:59:59"));
				
				String yf_dtFrom=request.getParameter("yf_dtFrom");
				String yf_dtTo=request.getParameter("yf_dtTo");
				if(yf_dtFrom!=null&&!yf_dtFrom.equals(""))
					paramMap.put("yf_dtFrom", sdf.parse(yf_dtFrom+" 00:00:00"));
				if(yf_dtTo!=null&&!yf_dtTo.equals(""))
					paramMap.put("yf_dtTo", sdf.parse(yf_dtTo+" 23:59:59"));
				
				List<Stoneblock> list=iStoneblockService.selectStoneblockByParam(paramMap);
				 
				List<String[]> exportList = new ArrayList<>();
				float sum_1=0,sum_2=0,sum_3=0;
				for(int index=0;index<list.size();index++){
					Stoneblock sb = list.get(index);
					String[] strings = {(index+1)+"", sdf.format(sb.getS_dt()), sb.getCode(), sb.getSource(), sb.getPlace(), 
							sb.getColor(), sb.getNumber(), sb.getLength(),sb.getWidth(),sb.getHeight(), 
							sb.getCube(), sb.getPrice(), sb.getSum(), sb.getAccountdiff(), sb.getPlatenumber(),
							"未入账", "未结算",sb.getComment()};
					if(sb.getRz_dt()!=null){
						strings[14]=sdf.format(sb.getRz_dt());
					}
					if(sb.getYf_dt()!=null){
						strings[15]=sdf.format(sb.getYf_dt());
					}
					if(sb.getCube()!=null)
						sum_1+=Float.parseFloat(sb.getCube());
					if(sb.getPrice()!=null)
						sum_2+=Float.parseFloat(sb.getPrice());
					if(sb.getSum()!=null)
						sum_3+=Float.parseFloat(sb.getSum());
					exportList.add(strings);
				}
				//String.format("%.2f", f)
				String[] strings = {"合计", "", "", "", "","", "", "","","", 
						String.format("%.3f", sum_1), String.format("%.2f", sum_2), String.format("%.2f", sum_3),
						"", "","", "", ""};
				exportList.add(strings);
				 
				ServletOutputStream out=response.getOutputStream();
				String fileName = "荒料入库明细"+sdf.format(new Date());
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
				String[] titles = { "序号","日期", "单号", "来源", "入库地点", "颜色", "料号", "长", 
						"宽" , "高", "立方", "单价", "金额", "账差", "运辅车辆", "入账时间", "运费结算时间","备注"}; 
				 
				ExcelUtil.export(titles, out, exportList);
				//ExcelUtil.exportRange(titles, out, exportList);
			}
			else{
				logger.info("分页参数不能为空！");
			}
		} catch (Exception e) { 
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		//return null;
	}
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
}
