package com.mpe.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class FileProcessor {
	
	String resultString;

	void compileCFile(String fileName)
    {
        String compileFileCommand = "gcc " + fileName;

        resultString = "";
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
    }

    void runCFile(String fileName)
    {
        String runFileCommand = "./" + fileName.split(".c")[0];

        try
        {

            System.out.println("Running C File");

            Process processRun = Runtime.getRuntime().exec(runFileCommand);

            BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
            String errorRun = brRun.readLine();
            if (errorRun != null)
                System.out.println("Error Run = " + errorRun);

            BufferedReader brResult = new BufferedReader(new InputStreamReader(processRun.getInputStream()));
            String outputRun = brResult.readLine();
            if (outputRun != null)
                System.out.println("Output Run = " + outputRun);

        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println("Exception ");
            System.out.println(e.getMessage());
        }
    }

    void compileCPPFile(String fileName)
    {
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
    }

    void runCPPFile(String fileName)
    {
        String runFileCommand = "./" + fileName.split(".cpp")[0];

        try
        {
            System.out.println("Running CPP File");

            Process processRun = Runtime.getRuntime().exec(runFileCommand);

            BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
            String errorRun = brRun.readLine();
            if (errorRun != null)
                System.out.println("Error Run = " + errorRun);

            BufferedReader brResult = new BufferedReader(new InputStreamReader(processRun.getInputStream()));
            String outputRun = brResult.readLine();
            if (outputRun != null)
                System.out.println("Output Run = " + outputRun);

        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println("Exception ");
            System.out.println(e.getMessage());
        }
    }
    
    void compileJavaFile(String fileName)
    {
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
            }
            bri.close();
            while ((line = bre.readLine()) != null)
            {
                System.out.println(line);
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
    }

    void runJavaFile(String fileName)
    {
        String runFileCommand = "java " + fileName.split(".java")[0];
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
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println("Exception ");
            System.out.println(e.getMessage());
        }
    }
}
