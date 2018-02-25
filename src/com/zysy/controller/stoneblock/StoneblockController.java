package com.zysy.controller.stoneblock;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zysy.service.stoneblock.IStoneblockService;
import com.zysy.model.stock.Stock;
import com.zysy.model.stoneblock.Stoneblock;
@Controller
public class StoneblockController {
	@Autowired
	private IStoneblockService iStoneblockService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	public Map update(Stoneblock stoneblock){
		Map resultMap=new HashMap();
		try {
			if(stoneblock.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultUpdate=iStoneblockService.updateStoneblock(stoneblock);
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
				paramMap.put("length",stoneblock.getLength());
				paramMap.put("width",stoneblock.getWidth());
				paramMap.put("height",stoneblock.getHeight());
				paramMap.put("cube",stoneblock.getCube());
				paramMap.put("price",stoneblock.getPrice());
				paramMap.put("sum",stoneblock.getSum());
				paramMap.put("platenumber",stoneblock.getPlatenumber());
				paramMap.put("accountdiff",stoneblock.getAccountdiff());
				paramMap.put("auditor",stoneblock.getAuditor());
				paramMap.put("editor",stoneblock.getEditor());
				paramMap.put("comment",stoneblock.getComment());
				paramMap.put("state",stoneblock.getState());
				paramMap.put("blocknumber",stoneblock.getBlocknumber());
				paramMap.put("c_id",stoneblock.getC_id());
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
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
}
