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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}

}
