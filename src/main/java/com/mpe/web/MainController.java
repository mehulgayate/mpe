package com.mpe.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.mpe.form.FileUploadForm;

@Controller
public class MainController {

	@RequestMapping("/")
	public ModelAndView showHome(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("new/index");
		return mv;
	}
	@RequestMapping("/upload-new")
	public ModelAndView showUploadNew(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("new/upload");
		return mv;
	}
	@RequestMapping("/upload-xml")
	public ModelAndView uploadFile(@ModelAttribute(FileUploadForm.key) FileUploadForm fileUploadForm)throws Exception{
		ModelAndView mv=new ModelAndView("new/upload-result");
		try {
			String[] splitedName=fileUploadForm.getXmlFile().getOriginalFilename().split("\\.");
			if(splitedName[splitedName.length-1].equals("java")){
				System.out.println("*********** Its A Java File");
			}else if(splitedName[splitedName.length-1].equals("c")){
				System.out.println("*********** Its A C File");
			}else if(splitedName[splitedName.length-1].equals("cpp")){
				System.out.println("*********** Its A C++ File");
			}else  {
				mv.addObject("error", "File Not Supported");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", "File Not Supported");
		}	
		return mv;
	}

}
