/**
 * 
 */
package com.sporniket.libre.javabeans.generator.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sporniket.libre.javabeans.generator.core.BeanSetProcessor;
import com.sporniket.libre.lang.ExceptionTools;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;

/**
 * Command-line interface for the javabean generator.
 * 
 * <pre>
 * usage : JavabeanGenerator FILE ... OUTPUT_DIR
 * 		FILE : path to a file, if the path contains spaces, enclose the whole path with double quotes.
 * 		OUTPUT_DIR : path to the base folder wher the package tree will be created.
 * </pre>
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; cli</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; cli</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; cli</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * cli</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @version 13.01.01
 * 
 */
public class JavabeanGenerator
{
	private static final int ERROR_CODE__ERROR_ON_OUTPUT_DIR = 3;

	private static final int ERROR_CODE__ERROR_PROCESSING_FILES = 4;

	private static final int ERROR_CODE__NOT_ENOUGH_PARAMETERS = 1;

	private static final int ERROR_CODE__PROCESSOR_COULD_NOT_BE_CREATED = 2;

	private static final String HORIZONTAL_RULE = "############################################################\n";

	private static void displayUsage()
	{
		System.out.println("usage : java " + JavabeanGenerator.class.getName() + " file1 file2 ... outputDir");
		System.out.println("\tfile1 file2 ...");
		System.out
				.println("\t\tlist of space separated file path pointing to XML files validating the javabean set XML Schema.");
		System.out.println("\toutputDir");
		System.out.println("\t\tpath of the output dir, the package directory tree will be created from this.");
		System.out.println();
		System.out.println("Should a path containing some space, enclose the path inside double quotes (\").");
		System.out.println();
		System.out.println("Sample :");
		System.out.println("java " + JavabeanGenerator.class.getName() + " \"C:\\Program Files\\foo.xml\" C:\\temp\\foodir");
		System.out.println();
	}

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			// Not enough args...
			displayUsage();
			System.exit(ERROR_CODE__NOT_ENOUGH_PARAMETERS);
		}

		// Ok, let the work begin.
		BeanSetProcessor _processor = null;
		try
		{
			_processor = new BeanSetProcessor();
		}
		catch (IOException _exception1)
		{
			_exception1.printStackTrace();
			System.exit(ERROR_CODE__PROCESSOR_COULD_NOT_BE_CREATED);
		}

		// Retrieve output directory
		File _outputDir = new File(args[args.length - 1]);
		try
		{
			_processor.setWorkingDirectory(_outputDir);
		}
		catch (IllegalArgumentException _exception1)
		{
			_exception1.printStackTrace();
			System.exit(ERROR_CODE__ERROR_ON_OUTPUT_DIR);
		}

		// Storage for error reports.
		int _fileCount = args.length - 1;
		List<String> _reports = new ArrayList<String>(_fileCount);

		// ok, proceed...
		processFiles(args, _processor, _reports);

		if (!_reports.isEmpty())
		{
			for (String _report : _reports)
			{
				System.err.println(_report);
			}
			System.exit(ERROR_CODE__ERROR_PROCESSING_FILES);
		}

		// DONE
	}

	/**
	 * @param args
	 * @param processor
	 * @param reports
	 */
	private static void processFiles(String[] args, BeanSetProcessor processor, List<String> reports)
	{
		for (int _i = 0; _i < args.length - 1; _i++)
		{
			try
			{
				InputStream _xml = new FileInputStream(new File(args[_i]));
				BeanSet _testSet = BeanSet.unmarshalBeanSet(new InputStreamReader(_xml));
				processor.process(_testSet);
			}
			catch (Exception _exception)
			{
				StringBuilder _messageBuffer = new StringBuilder(HORIZONTAL_RULE);
				_messageBuffer.append("Error when processing\n").append(args[_i]).append("\n");
				_messageBuffer.append(ExceptionTools.getStackTrace(_exception)).append("\n");
				reports.add(_messageBuffer.toString());
			}
		}
	}

}
