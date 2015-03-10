/**
 * 
 */
package com.sporniket.libre.javabeans.generator.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.sporniket.libre.javabeans.generator.core.BeanSetProcessor;
import com.sporniket.libre.lang.ExceptionTools;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;

/**
 * Mojo for generating javabeans from specific xml descriptions.
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr />
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; maven</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; maven</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; maven</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * maven</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * @version 13.01.01
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * 
 */
public class JavabeansGeneratorMojo extends AbstractMojo
{
	/**
	 * Directory containing the description files, by default it will be located next to the java source directory.
	 * 
	 * @parameter alias="inputDirectory" expression="${project.build.sourceDirectory}/../sporniket-javabeans"
	 */
	private File myInputDirectory;

	/**
	 * Output directory, where the generated code files will be created.
	 * 
	 * By default it will be in the build directory, in generated-sources/sporniket-javabeans.
	 * 
	 * @parameter alias="outputDirectory" expression="${project.build.directory}/generated-sources/sporniket-javabeans"
	 */
	private File myOutputDirectory;

	/**
	 * The Maven project to act upon.
	 * 
	 * The outputDirectory will be added to the list of directory containing source files.
	 * 
	 * @parameter alias="project" expression="${project}"
	 * @required
	 */

	private MavenProject myProject;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException
	{

		// Sanity check : do nothing if no input directory
		if (!getInputDirectory().exists())
		{
			getLog().info("Input directory (" + getInputDirectory().getAbsolutePath() + ") does not exist, skip code generation.");
			return;
		}

		if (!getInputDirectory().isDirectory())
		{
			throw new MojoExecutionException("The input directory path (" + getInputDirectory().getAbsolutePath()
					+ ") is not a directory.");
		}

		BeanSetProcessor _processor = null;
		try
		{
			_processor = new BeanSetProcessor();
		}
		catch (IOException _exception1)
		{
			throw new MojoExecutionException("Processor could not be created.", _exception1);
		}

		try
		{
			String _outputDirectoryAbsolutePath = getOutputDirectory().getAbsolutePath();
			if (!getOutputDirectory().exists())
			{
				getLog().info("Creating output directory (" + _outputDirectoryAbsolutePath + ") ...");
				getOutputDirectory().mkdirs();
			}
			getLog().info(
					"Use output directory (" + _outputDirectoryAbsolutePath + ") as the working directory of the processor...");
			_processor.setWorkingDirectory(getOutputDirectory());
			getLog().info(
					"Add output directory (" + _outputDirectoryAbsolutePath
							+ ") in the list of root directory containing source code...");
			getProject().addCompileSourceRoot(_outputDirectoryAbsolutePath);
		}
		catch (IllegalArgumentException _exception1)
		{
			throw new MojoExecutionException("Error using " + getOutputDirectory().getAbsolutePath() + " as working directory.",
					_exception1);
		}

		File[] _args = getInputDirectory().listFiles();
		// Storage for error reports.
		int _fileCount = _args.length;
		List<String> _reports = new ArrayList<String>(_fileCount);

		// ok, proceed...
		processFiles(_args, _processor, _reports);

		if (!_reports.isEmpty())
		{
			for (String _report : _reports)
			{
				getLog().error(_report);
			}
			throw new MojoFailureException("Error while processing some javabeans description files");
		}
	}

	/**
	 * @return the inputDirectory
	 */
	public File getInputDirectory()
	{
		return myInputDirectory;
	}

	/**
	 * @param inputDirectory
	 *            the inputDirectory to set
	 */
	public void setInputDirectory(File inputDirectory)
	{
		myInputDirectory = inputDirectory;
	}

	/**
	 * @return the outputDirectory
	 */
	public File getOutputDirectory()
	{
		return myOutputDirectory;
	}

	/**
	 * @param outputDirectory
	 *            the outputDirectory to set
	 */
	public void setOutputDirectory(File outputDirectory)
	{
		myOutputDirectory = outputDirectory;
	}

	private static final String HORIZONTAL_RULE = "############################################################\n";

	/**
	 * @param args
	 * @param processor
	 * @param reports
	 */
	private static void processFiles(File[] args, BeanSetProcessor processor, List<String> reports)
	{
		for (int _i = 0; _i < args.length; _i++)
		{
			try
			{
				InputStream _xml = new FileInputStream(args[_i]);
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

	/**
	 * @return the project
	 */
	public MavenProject getProject()
	{
		return myProject;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(MavenProject project)
	{
		myProject = project;
	}

}
