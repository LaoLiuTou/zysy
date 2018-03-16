package com.zysy.controller.matteboard;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zysy.service.matteboard.IMatteboardService;
import com.zysy.utils.ExcelUtil;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportMatteboard") 
	public @ResponseBody void export(HttpServletRequest request, HttpServletResponse response,Matteboard matteboard)
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


				SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
				List<String[]> exportList = new ArrayList<>();
				float sum_1=0,sum_2=0,sum_3=0,sum_4=0,sum_5=0,sum_6=0,sum_7=0,
		                sum_8=0,sum_9=0,sum_10=0,sum_11=0,sum_12=0,sum_13=0;
				for(int index=0;index<list.size();index++){
					Matteboard mb = list.get(index);
					float real_cube = 0;
					if(Float.parseFloat(mb.getSb_spec().split("\\*")[2])>1.25&&(mb.getLayer().equals("上")||mb.getLayer().equals("下"))){
						real_cube=Float.parseFloat(mb.getSb_cube())/2;
	                }
					
					String[] strings = {(index+1)+"", mb.getSb_code(), mb.getSb_spec().split("\\*")[0],
							mb.getSb_spec().split("\\*")[1],mb.getSb_spec().split("\\*")[2],mb.getSb_cube(),
							mb.getLayer(),real_cube+"",dateSdf.format(mb.getM_dt()),mb.getCode(),mb.getMsize(),
							mb.getHeight(),mb.getBlocknumber()+"",mb.getSquare(),
							mb.getBlocknumber()-mb.getBelowgradeblock()+"",
							String.format("%.2f", Float.parseFloat(mb.getSquare())-Float.parseFloat(mb.getBelowgradesquare())),
							mb.getBelowgradeblock()+"",mb.getBelowgradesquare(),mb.getPrice(),mb.getSum()};
				     
					
					if(mb.getSb_spec()!=null){
						sum_1+=Float.parseFloat(mb.getSb_spec().split("\\*")[0]);
						sum_2+=Float.parseFloat(mb.getSb_spec().split("\\*")[1]);
						sum_3+=Float.parseFloat(mb.getSb_spec().split("\\*")[2]);
					} 
					if(mb.getSb_cube()!=null)
						sum_4+=Float.parseFloat(mb.getSb_cube());
					sum_5+=real_cube; 
					if(mb.getBlocknumber()!=null)
						sum_6+=mb.getBlocknumber();
					if(mb.getSquare()!=null)
						sum_7+=Float.parseFloat(mb.getSquare());
					if(mb.getBelowgradeblock()!=null)
						sum_8+=mb.getBlocknumber()-mb.getBelowgradeblock();
					if(mb.getBelowgradesquare()!=null)
						sum_9+=Float.parseFloat(mb.getSquare())-Float.parseFloat(mb.getBelowgradesquare());
					if(mb.getBelowgradeblock()!=null)
						sum_10+=mb.getBelowgradeblock();
					if(mb.getBelowgradesquare()!=null)
						sum_11+=Float.parseFloat(mb.getBelowgradesquare());
					if(mb.getPrice()!=null)
						sum_12+=Float.parseFloat(mb.getPrice());
					if(mb.getSum()!=null)
						sum_13+=Float.parseFloat(mb.getSum());
					    
					exportList.add(strings);
				}
				//String.format("%.2f", f)
				String[] strings = {"合计", "", sum_1+"", sum_2+"", sum_3+"",String.format("%.3f", sum_4), "",
						String.format("%.3f", sum_5),"","", "","", sum_6+"",String.format("%.2f", sum_7),
						sum_8+"", String.format("%.2f", sum_9),sum_10+"", String.format("%.2f", sum_11),
						sum_12+"",sum_13+"" };
				exportList.add(strings);
			
				ServletOutputStream out=response.getOutputStream();
				String fileName = "大锯检验单流水表"+dateSdf.format(new Date());
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
				String[] titles1 = { "序号","领用荒料", "日期", "单据号", "产品规格", "厚度", "产品总量",
						"合格数量","不合格数量" , "工价", "金额"}; 
				
				CellRangeAddress[] range1={new CellRangeAddress(0,1,0,0),new CellRangeAddress(0,0,1,7),new CellRangeAddress(0,1,8,8),
						new CellRangeAddress(0,1,9,9),new CellRangeAddress(0,1,10,10),new CellRangeAddress(0,1,11,11),
						new CellRangeAddress(0,0,12,13),new CellRangeAddress(0,0,14,15),new CellRangeAddress(0,0,16,17),
						new CellRangeAddress(0,1,18,18),new CellRangeAddress(0,1,19,19)};
				String[] titles2 = { "石料编号","长", "宽", "高", "立方数", "料层", "实际立方数", "块数", 
						"平方数" , "块数", "平方数", "块数", "平方数"}; 
				CellRangeAddress[] range2={new CellRangeAddress(1,1,1,1),new CellRangeAddress(1,1,2,2),
						new CellRangeAddress(1,1,3,3),new CellRangeAddress(1,1,4,4),new CellRangeAddress(1,1,5,5),
						new CellRangeAddress(1,1,6,6),new CellRangeAddress(1,1,7,7),new CellRangeAddress(1,1,12,12),
						new CellRangeAddress(1,1,13,13),new CellRangeAddress(1,1,14,14),new CellRangeAddress(1,1,15,15),
						new CellRangeAddress(1,1,16,16),new CellRangeAddress(1,1,17,17)};
				ExcelUtil.exportRange(titles1,range1,titles2,range2, out, exportList);
			}
			else{
				logger.info("分页参数不能为空！");
			}
		} catch (Exception e) {
			 
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportReportMatteboard")
	public @ResponseBody void exportReport(HttpServletRequest request, HttpServletResponse response,Matteboard matteboard)
		throws ServletException, IOException {
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
				 
				SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
				List<String[]> exportList = new ArrayList<>();
				float sum_1=0,sum_2=0,sum_3=0,sum_4=0,sum_5=0,sum_6=0;
				for(int index=0;index<list.size();index++){
					
					/*sum_1=(Number(sum_1)+Number(subData[o].sum_blocknumber)).toFixed(2);
	                sum_2=(Number(sum_2)+Number(subData[o].sum_square)).toFixed(2);
	                sum_3=(Number(sum_3)+Number(subData[o].sum_belowgradeblock)).toFixed(2);
	                sum_4=(Number(sum_4)+Number(subData[o].sum_belowgradesquare)).toFixed(2);
	                sum_5=(Number(sum_5)+Number(subData[o].sum_gradeblock)).toFixed(2);
	                sum_6=(Number(sum_6)+Number(subData[o].sum_gradesquare)).toFixed(2);*/
	                 
					Matteboard mb = list.get(index);
					String[] strings = {(index+1)+"", dateSdf.format(mb.getM_dt()),mb.getHeight(),mb.getSum_blocknumber(),
							mb.getSum_square(),mb.getSum_belowgradeblock(),mb.getSum_belowgradesquare(),
							mb.getSum_gradeblock(),mb.getSum_gradesquare()};
					 
					if(mb.getSum_blocknumber()!=null)
						sum_1+=Float.parseFloat(mb.getSum_blocknumber());
					if(mb.getSum_square()!=null)
						sum_2+=Float.parseFloat(mb.getSum_square());
					if(mb.getSum_belowgradeblock()!=null)
						sum_3+=Float.parseFloat(mb.getSum_belowgradeblock());
					if(mb.getSum_belowgradesquare()!=null)
						sum_4+=Float.parseFloat(mb.getSum_belowgradesquare());
					if(mb.getSum_gradeblock()!=null)
						sum_5+=Float.parseFloat(mb.getSum_gradeblock());
					if(mb.getSum_gradesquare()!=null)
						sum_6+=Float.parseFloat(mb.getSum_gradesquare());
					 
					exportList.add(strings);
				}
				//String.format("%.2f", f)
				 
				String[] strings = {"合计", "","", sum_1+"", String.format("%.2f", sum_2), sum_3+"",
						String.format("%.2f", sum_4), sum_5+"",String.format("%.2f", sum_5) };
				exportList.add(strings);
				 
				ServletOutputStream out=response.getOutputStream();
				String fileName = "大锯产量统计"+dateSdf.format(new Date());
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
				String[] titles = { "序号","日期", "厚度", "总块料", "平方", "不合格", "不合格平方", "合格块料", "合格平方"}; 
				 
			 
                
				ExcelUtil.export(titles, out, exportList);
				
		} catch (Exception e) { 
			logger.info("查询失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		 
	}
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
}
