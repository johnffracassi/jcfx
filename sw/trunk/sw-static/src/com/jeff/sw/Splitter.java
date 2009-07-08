package com.jeff.sw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Task;

public class Splitter extends Task
{
	File workDir;
	File outputDir;
	File inputFile;
	File templateDir;
	String matchPattern = "<!--file:(.*)-->(.*)";
	Properties prop = new Properties();
	
	public Splitter()
	{
		prop.setProperty("teamname", "Steamboat Willies");
		prop.setProperty("sponsorLogo", "http://");
	}
	
	public void execute() 
	{
		try
		{
			split();
			merge();
		}
		catch(Exception ex)
		{
			log(ex, 1);
		}
	}
	
	public void setWorkDir(String value)
	{
		workDir = new File(value);
		log("set work dir to " + workDir);
	}
	
	public void setOutputDir(String value)
	{
		outputDir = new File(value);
		log("set output dir to " + outputDir);
	}
	
	public void setInputFile(String value)
	{
		inputFile = new File(value);
		log("set input file to " + inputFile);
	}
	
	public void setTemplateDir(String value)
	{
		templateDir = new File(value);
		log("set template dir to " + templateDir);
	}
	
	public void merge() throws Exception
	{
		File templateFile = new File(templateDir, "template.html");

		if(!workDir.exists())
		{
			workDir.mkdirs();
		}

		String[] template = compileTemplate(templateFile);
		for(File file : workDir.listFiles())
		{
			System.out.println("merging " + file);
			
			String part = readFile(file);
			
			FileWriter out = new FileWriter(outputDir + "/" + file.getName());
			out.write(template[0]);
			out.write(part);
			out.write(template[1]);
			out.close();
		}
	}
	
	private String[] compileTemplate(File file) throws Exception
	{
		String template = readFile(file);
		
		FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				System.out.println("  include? " + dir + "/" + name);
	            return name.toLowerCase().endsWith(".inc");
            }
		};
		
		for(File incs : file.getParentFile().listFiles(ff))
		{
			System.out.println("  substituting " + incs);
			template = template.replaceAll("\\$\\{" + incs.getName() + "\\}", readFile(incs));
		}
		
		String[] splits = template.split("\\$\\{content\\}");
		
		return splits;
	}
	
	public void split() throws Exception
	{
		Pattern pattern = Pattern.compile(matchPattern);
		
		FileInputStream fstream = new FileInputStream(inputFile);
		DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    
	    log("splitting " + inputFile);
	    
	    String currentFilename = null;
	    String strLine = null;
	    BufferedWriter out = null;
		while ((strLine = br.readLine()) != null)   
		{
			Matcher matcher = pattern.matcher(strLine.trim());
			if(matcher.matches())
			{
				if(out != null)
				{
					out.close();
				}
				
				currentFilename = matcher.group(1).trim(); 
				File outfile = new File(workDir, currentFilename);
				out = new BufferedWriter(new FileWriter(outfile));
				log("  writing file " + outfile);
				
				if(matcher.groupCount() == 2)
				{
					out.write(matcher.group(2));
					out.write('\n');
				}
			}
			else if(out != null)
			{
				out.write(strLine);
				out.write('\n');
			}
		}
		
		if(out != null)
		{
			out.close();
		}
	}
	
    public static String readFile(File file) throws IOException 
    {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) 
        {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) 
        {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        
        return new String(bytes);
    }
}
