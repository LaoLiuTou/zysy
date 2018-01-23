package com.zysy.controller.material;
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
import com.zysy.service.material.IMaterialService;
import com.zysy.model.material.Material;
@Controller
public class MaterialController {
	@Autowired
	private IMaterialService iMaterialService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Logger logger = Logger.getLogger("zysyLogger");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addMaterial")
	@ResponseBody
	public Map add(Material material){
		Map resultMap=new HashMap();
		try {
			iMaterialService.addMaterial(material);
			resultMap.put("status", "0");
			resultMap.put("msg", material.getId());
			logger.info("新建成功，主键："+material.getId());
		} catch (Exception e) {
			resultMap.put("status", "-1");
			resultMap.put("msg", "新建失败！");
			logger.info("新建失败！"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteMaterial")
	@ResponseBody
	public Map delete(Material material){
		Map resultMap=new HashMap();
		try {
			if(material.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultDelete=iMaterialService.deleteMaterial(material.getId()+"");
				resultMap.put("status", "0");
				resultMap.put("msg", "删除成功！");
				logger.info("删除成功，主键："+material.getId());
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
	@RequestMapping("/selectMaterial")
	@ResponseBody
	public Map select(Material material){
		Map resultMap=new HashMap();
		try {
			if(material.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				Material resultSelect=iMaterialService.selectMaterialById(material.getId()+"");
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
	@RequestMapping("/updateMaterial")
	@ResponseBody
	public Map update(Material material){
		Map resultMap=new HashMap();
		try {
			if(material.getId()==null){
				resultMap.put("status", "-1");
				resultMap.put("msg", "参数不能为空！");
			}
			else{
				int resultUpdate=iMaterialService.updateMaterial(material);
				resultMap.put("status", "0");
				resultMap.put("msg", "更新成功！");
				logger.info("更新成功，主键："+material.getId());
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
	@RequestMapping("/listMaterial")
	@ResponseBody
	public Map list(HttpServletRequest request, HttpServletResponse response,Material material)
		throws ServletException, IOException {
		Map resultMap=new HashMap();
		try {
			String page=request.getParameter("page");
			String size=request.getParameter("size");
			if(page!=null&&size!=null){
				Map paramMap=new HashMap();
				paramMap.put("fromPage",(Integer.parseInt(page)-1)*Integer.parseInt(size));
				paramMap.put("toPage",Integer.parseInt(size)); 
				paramMap.put("id",material.getId());
				paramMap.put("name",material.getName());
				paramMap.put("comment",material.getComment());
				paramMap.put("state",material.getState());
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
				List<Material> list=iMaterialService.selectMaterialByParam(paramMap);
				int totalnumber=iMaterialService.selectCountMaterialByParam(paramMap);
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
