package com.mpe.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;


public class FileProcessor {



	public String compileCFile(String fileName)
	{
		String compileFileCommand = "gcc " + fileName;

		String resultString= "";
		try
		{
			System.out.println("Compiling C File");

			Process processCompile = Runtime.getRuntime().exec(compileFileCommand);

			BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
			String errorCompile = brCompileError.readLine();
			if (errorCompile != null)
				System.out.println("Error Compiler = " + errorCompile);

			resultString += errorCompile +"\n";

			BufferedReader brCompileRun = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
			String outputCompile = brCompileRun.readLine();
			if (outputCompile != null)
				System.out.println("Output Compiler = " + outputCompile);

			resultString += outputCompile +"\n";

		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}

	public String runCFile(String fileName) throws IOException
	{
		String resultString="";
		String runFileCommand = "./a.out";
		if(checkForInputsForC()){
			resultString="Please Download Compiled File and then run ";
			return resultString;
		}
		try
		{

			System.out.println("Running C File "+runFileCommand);

			Process processRun = Runtime.getRuntime().exec(runFileCommand);

			BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
			String errorRun = brRun.readLine();
			if (errorRun != null)
				System.out.println("Error Run = " + errorRun);

			BufferedReader brResult = new BufferedReader(new InputStreamReader(processRun.getInputStream()));
			String outputRun = brResult.readLine();
			if (outputRun != null)
				System.out.println("Output Run = " + outputRun);

			resultString=outputRun;
		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}

	public String compileCPPFile(String fileName)
	{
		String resultString="";
		String compileFileCommand = "g++ " + fileName;
		try
		{

			System.out.println("Compiling CPP File");

			Process processCompile = Runtime.getRuntime().exec(compileFileCommand);

			BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
			String errorCompile = brCompileError.readLine();
			if (errorCompile != null)
				System.out.println("Error Compiler = " + errorCompile);

			resultString += errorCompile +"\n";

			BufferedReader brCompileRun = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
			String outputCompile = brCompileRun.readLine();
			if (outputCompile != null)
				System.out.println("Output Compiler = " + outputCompile);

			resultString += outputCompile +"\n";

		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}

	public String runCPPFile(String fileName) throws IOException
	{
		String resultString="";
		String runFileCommand = "./a.out";

		if(checkForInputsForCPP()){
			resultString="Please Download Comiled File";
			return resultString;
		}
		try
		{
			System.out.println("Running CPP File");

			Process processRun = Runtime.getRuntime().exec(runFileCommand);

			BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
			String errorRun = brRun.readLine();
			if (errorRun != null){
				System.out.println("Error Run = " + errorRun);
				resultString=errorRun;
			}

			BufferedReader brResult = new BufferedReader(new InputStreamReader(processRun.getInputStream()));
			String outputRun = brResult.readLine();
			if (outputRun != null){
				System.out.println("Output Run = " + outputRun);
				resultString=outputRun;
			}


		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}

	public String compileJavaFile(String fileName)
	{
		String resultString="";
		fileName="JavaProgram.java";
		String compileFileCommand = "javac " + fileName;
		try
		{
			System.out.println("Executing Java File");

			Process compileProcess = Runtime.getRuntime().exec(compileFileCommand);

			String line = "";
			BufferedReader bri = new BufferedReader(new InputStreamReader(compileProcess.getInputStream()));
			BufferedReader bre = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
			while ((line = bri.readLine()) != null)
			{
				System.out.println(line);
				resultString=resultString+line+"\n";
			}
			bri.close();
			while ((line = bre.readLine()) != null)
			{
				System.out.println("Error  "+line);
				resultString=resultString+line+"\n";
			}
			bre.close();
			compileProcess.waitFor();
			System.out.println("Done.");
		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}

	public String runJavaFile(String fileName) throws IOException
	{
		String resultString="";
		String runFileCommand = "java " + "JavaProgram";
		System.out.println("Running command ::: " +runFileCommand);
		if(checkForInputs()){
			resultString="Please Download class file";
			return resultString;
		}
		try
		{
			System.out.println("runFileCommand : " + runFileCommand);
			System.out.println("Running Java File");

			Process runProcess = Runtime.getRuntime().exec(runFileCommand);
			BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
			String line = reader.readLine();
			System.out.println("line = " + line);
			while (line != null)
			{
				resultString=resultString+line;
				System.out.println(line);
				line = reader.readLine();


			}

		} catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Exception ");
			System.out.println(e.getMessage());
		}
		return resultString;
	}
	private boolean checkForInputs() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File("JavaProgram.java")));
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.indexOf("System.in")>=0 || trimmedLine.indexOf("Scanner")>0){
				System.out.println("Inputes Found");
				reader.close();
				return true;
			}

		}

		reader.close();
		return false;


	}

	private boolean checkForInputsForC() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File("CProgram.c")));
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.indexOf("scanf")>=0 || trimmedLine.indexOf("getchar")>0){
				System.out.println("Inputes Found");
				reader.close();
				return true;
			}

		}

		reader.close();
		return false;
		

	}
	
	private boolean checkForInputsForCPP() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File("CPPProgram.cpp")));
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.indexOf("scanf")>=0 || trimmedLine.indexOf("cin")>=0 || trimmedLine.indexOf("getchar")>0){
				System.out.println("Inputes Found");
				reader.close();
				return true;
			}

		}

		reader.close();
		return false;


	}
	
	public String getFileContents(String name) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File(name)));
		String currentLine;
		String content="";
		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			
			trimmedLine=StringUtils.replace(trimmedLine, "<", "&lt");
			trimmedLine=StringUtils.replace(trimmedLine, ">", "&gt");
			content=content+"<br/>"+trimmedLine;

		}

		reader.close();
		return content;


	}
}
