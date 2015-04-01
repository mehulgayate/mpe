package com.mpe.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.mpe.entity.User;
import com.mpe.entity.UserFile;
import com.mpe.form.FileUploadForm;
import com.mpe.service.FileProcessor;
import com.mpe.service.UserForm;
import com.mpe.service.UserService;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

@Controller
public class MainController {

	@Resource
	private FileProcessor fileProcessor;
	
	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private UserService userService;

	@RequestMapping("/")
	public ModelAndView showHome(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("service-invoke/login");
		return mv;
	}
	@RequestMapping("/upload-new")
	public ModelAndView showUploadNew(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("new/upload");
		return mv;
	}
	@RequestMapping("/upload-xml")
	public ModelAndView uploadFile(@ModelAttribute(FileUploadForm.key) FileUploadForm fileUploadForm, HttpSession session)throws Exception{
		User user = (User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		ModelAndView mv=new ModelAndView("new/upload-result");
		try {
			UserFile userFile =new UserFile();
			userFile.setFileName(fileUploadForm.getXmlFile().getOriginalFilename());
			userFile.setUser(user);
			userFile.setUploadedDate(new Date());
			dataStoreManager.save(userFile);
			
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
				mv.addObject("code",fileProcessor.getFileContents("JavaProgram.java"));
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
				mv.addObject("code",fileProcessor.getFileContents("CProgram.c"));
				mv.addObject("download","a.out");
			}else if(splitedName[splitedName.length-1].equals("cpp")){
				System.out.println("*********** Its A C++ File");
				System.out.println(writeFile(fileUploadForm.getXmlFile(), "CPPProgram","cpp"));
				String compileOutput=fileProcessor.compileCPPFile("CPPProgram.cpp");
				String runOutput=fileProcessor.runCPPFile("CPPProgram.c");
				mv.addObject("code",fileProcessor.getFileContents("CPPProgram.cpp"));

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
	
	
	
	private String writeEditedFile(String code,String name,String ext) throws IOException{
		byte[] bytes = code.getBytes();
		BufferedOutputStream stream =
				new BufferedOutputStream(new FileOutputStream(new File(name + "."+ext)));
		stream.write(bytes);
		stream.close();
		return "You successfully wrtiiten " + name + " into " + name + "-uploaded !";
	}

	@RequestMapping("/download-file")
	public void getFile(
			@RequestParam String type, 
			HttpServletResponse response) {
		int BUFFER_SIZE = 4096;

		try {
			// get your file as InputStream
			InputStream is = new FileInputStream(new File(type)); ;
			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			response.setHeader("Content-Disposition", "attachment; filename=\" " + type + "\"");


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

	@RequestMapping("/login")
	public ModelAndView login(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("login");
		User userl=(User) httpSession.getAttribute("user");		
		return mv;
	}

	@RequestMapping("/authenticate")
	public ModelAndView authenticate(HttpSession httpSession,HttpServletRequest request){
		ModelAndView mv=new ModelAndView("new/index");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		User user=userService.validate(email, password);
		if(user==null){
			mv=new ModelAndView("service-invoke/login");
			mv.addObject("error", "email /  password is wrong");
		}else{
			httpSession.setAttribute("user", user);
		}
		return mv;
	}

	@RequestMapping("/register")
	public ModelAndView register(){
		ModelAndView mv=new ModelAndView("signup/signup");

		return mv;
	}

	@RequestMapping("/register/add")
	public ModelAndView registerNewUser(HttpServletRequest request,@ModelAttribute(UserForm.key) UserForm userForm){
		ModelAndView mv=new ModelAndView("signup/complete");
		userService.addUser(userForm);
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session){
		ModelAndView mv=new ModelAndView("redirect:/login");
		session.invalidate();
		return mv;
	}
	
	@RequestMapping("/user-files")
	public ModelAndView userFiles(HttpSession session){
		ModelAndView mv=new ModelAndView("new/user-files");
		User user = (User) session.getAttribute("user");
		mv.addObject("userFiles", dataStoreManager.listUserFiles(user));
		return mv;
	}
	
	
	@RequestMapping("/edit-file")
	public ModelAndView edirFile(@RequestParam String code,@RequestParam String type, HttpSession session)throws Exception{
		User user = (User) session.getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/login");
		}
		ModelAndView mv=new ModelAndView("new/upload-result");
		try {			
		
			if(type.equals("JAVA")){
				System.out.println("*********** Its A Java File");
				System.out.println(writeEditedFile(code, "JavaProgram","java"));
				String compileOutput="";
				compileOutput=fileProcessor.compileJavaFile("");
				String runOutput="";
				runOutput=fileProcessor.runJavaFile("/mpe/JavaProgram.java");
				mv.addObject("fileType","JAVA");
				mv.addObject("runOutput",runOutput);
				mv.addObject("code",fileProcessor.getFileContents("JavaProgram.java"));
				mv.addObject("compileOutput",compileOutput);
				mv.addObject("download","JavaProgram.class");
			}else if(type.equals("C")){
				System.out.println("*********** Its A C File");
				System.out.println(writeEditedFile(code, "CProgram","c"));
				String compileOutput=fileProcessor.compileCFile("CProgram.c");
				String runOutput=fileProcessor.runCFile("CProgram.c");

				mv.addObject("fileType","C");
				mv.addObject("runOutput",runOutput);
				mv.addObject("compileOutput",compileOutput);
				mv.addObject("code",fileProcessor.getFileContents("CProgram.c"));
				mv.addObject("download","a.out");
			}else if(type.equals("CPP")){
				System.out.println("*********** Its A C++ File");
				System.out.println(writeEditedFile(code, "CPPProgram","cpp"));
				String compileOutput=fileProcessor.compileCPPFile("CPPProgram.cpp");
				String runOutput=fileProcessor.runCPPFile("CPPProgram.c");
				mv.addObject("code",fileProcessor.getFileContents("CPPProgram.cpp"));

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

}
