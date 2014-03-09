package com.mpe.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mpe.form.FileUploadForm;
import com.mpe.service.FileProcessor;

@Controller
public class MainController {
	
	@Resource
	private FileProcessor fileProcessor;

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
				System.out.println(writeFile(fileUploadForm.getXmlFile(), "JavaProgram","java"));
				String compileOutput="";
				compileOutput=fileProcessor.compileJavaFile("");
				String runOutput="";
				runOutput=fileProcessor.runJavaFile("/mpe/JavaProgram.java");
				mv.addObject("fileType","JAVA");
				mv.addObject("runOutput",runOutput);
				mv.addObject("compileOutput",compileOutput);
				mv.addObject("download","JavaProgram.class");
			}else if(splitedName[splitedName.length-1].equals("c")){
				System.out.println("*********** Its A C File");
				System.out.println(writeFile(fileUploadForm.getXmlFile(), "CProgram","c"));
				String compileOutput=fileProcessor.compileCFile("CProgram.c");
				String runOutput=fileProcessor.runCFile("CProgram.c");
				
				mv.addObject("fileType","C");
				mv.addObject("runOutput",runOutput);
				mv.addObject("compileOutput",compileOutput);
				mv.addObject("download","a.out");
			}else if(splitedName[splitedName.length-1].equals("cpp")){
				System.out.println("*********** Its A C++ File");
				System.out.println(writeFile(fileUploadForm.getXmlFile(), "CPPProgram","cpp"));
				String compileOutput=fileProcessor.compileCPPFile("CPPProgram.cpp");
				String runOutput=fileProcessor.runCPPFile("CPPProgram.c");
				
				mv.addObject("fileType","CPP");
				mv.addObject("runOutput",runOutput);
				mv.addObject("compileOutput",compileOutput);
				mv.addObject("download","a.out");
			}else  {
				mv.addObject("error", "File Not Supported");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", "File Not Supported");
		}	
		return mv;
	}
	
	private String writeFile(MultipartFile file,String name,String ext) throws IOException{
		 byte[] bytes = file.getBytes();
         BufferedOutputStream stream =
                 new BufferedOutputStream(new FileOutputStream(new File(name + "."+ext)));
         stream.write(bytes);
         stream.close();
         return "You successfully uploaded " + name + " into " + name + "-uploaded !";
	}
	
	@RequestMapping("/download-file")
	public void getFile(
	    @RequestParam String type,@RequestParam String name, 
	    HttpServletResponse response) {
		int BUFFER_SIZE = 4096;

	    try {
	      // get your file as InputStream
	    	InputStream is = new FileInputStream(new File(type)); ;
	    	// get output stream of the response
	        OutputStream outStream = response.getOutputStream();
	 
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
	        response.setHeader("Content-Disposition", "attachment; filename=\"dummyname " + name + "\"");

	        
	        // write bytes read from the input stream into the output stream
	        while ((bytesRead = is.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	 
	        is.close();
	        outStream.close();
	 
	    } catch (IOException ex) {
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}

}
