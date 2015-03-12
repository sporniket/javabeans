/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.libre.io.FileGenerator;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;

/**
 * Processing of a package of javabeans
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; core</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @version 13.01.01
 * @since 13.01.01
 * 
 */
public class PackageProcessor implements FileGenerator
{

	/**
	 * Registry to find out if an extended classe needs property change management code.
	 * 
	 * Will be filled by {@link BeanSetProcessor}.
	 */
	private Set<String> myGeneratedClassesRegistry = new TreeSet<String>();

	/**
	 * Directory where the processor will be working
	 */
	private File myWorkingDirectory;

	/**
	 * Java class file generator.
	 */
	private BeanGeneratorBase myBeanGenerator;

	/**
	 * Package-info file generator.
	 */
	private PackageGenerator myPackageGenerator;

	/**
	 * @throws IOException if there is a problem to deal with.
	 * 
	 */
	public PackageProcessor() throws IOException
	{
		myBeanGenerator = new BeanGeneratorBase();
		myPackageGenerator = new PackageGeneratorBase();
	}

	/**
	 * @param set set to process.
	 * @param pack package to process.
	 * @throws FileNotFoundException if there is a problem to deal with.
	 */
	public void process(BeanSet set, Package pack) throws FileNotFoundException
	{
		// sanity check
		if (null == getWorkingDirectory())
		{
			throw new IllegalStateException("This processor has not been set up properly : no working Directory specified");
		}

		// ok
		File _outputPackageDirectory = process__computeOutputDirectory(pack);
		process__generatePackageInfo(_outputPackageDirectory, pack, set);
		process__generateBeans(_outputPackageDirectory, pack, set);
	}

	/**
	 * @param pack
	 * @return
	 */
	private File process__computeOutputDirectory(Package pack)
	{
		String _outputPackDirRelativePath = pack.getName().replace('.', File.separatorChar);
		File _outputPackageDirectory = new File(myWorkingDirectory, _outputPackDirRelativePath);
		if (!_outputPackageDirectory.exists())
		{
			_outputPackageDirectory.mkdirs();
		}
		else if (!_outputPackageDirectory.isDirectory())
		{
			throw new IllegalStateException(_outputPackageDirectory.getAbsolutePath() + " is not a directory.");
		}
		return _outputPackageDirectory;
	}

	/**
	 * @param outputPackageDirectory
	 * @param pack
	 * @param set
	 * @throws FileNotFoundException
	 */
	private void process__generatePackageInfo(File outputPackageDirectory, Package pack, BeanSet set)
			throws FileNotFoundException
	{
		String _fileName = "package-info.java";
		File _output = new File(outputPackageDirectory, _fileName);
		PrintWriter _out = new PrintWriter(_output);
		myPackageGenerator.outputPackageInfo(_out, pack, set);
		_out.flush();
		_out.close();
	}

	/**
	 * @param outputPackageDirectory
	 * @param pack
	 * @param set
	 * @throws FileNotFoundException
	 */
	private void process__generateBeans(File outputPackageDirectory, Package pack, BeanSet set) throws FileNotFoundException
	{
		Bean[] _beans = pack.getBean();
		for (Bean _bean : _beans)
		{
			String _fileName = _bean.getName() + ".java";
			File _output = new File(outputPackageDirectory, _fileName);
			PrintWriter _out = new PrintWriter(_output);
			myBeanGenerator.outputBeanJavaCode(_out, _bean, pack, set);
			_out.flush();
			_out.close();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.io.FileGenerator#getWorkingDirectory()
	 */
	public File getWorkingDirectory()
	{
		// TODO Auto-generated method stub
		return myWorkingDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.io.FileGenerator#setWorkingDirectory(java.io.File)
	 */
	public void setWorkingDirectory(File workingDirectory) throws IllegalArgumentException
	{
		if (null != workingDirectory)
		{
			if (!workingDirectory.exists())
			{
				throw new IllegalArgumentException("working directory does not exists :" + workingDirectory.getAbsolutePath());
			}
			if (!workingDirectory.isDirectory())
			{
				throw new IllegalArgumentException("path MUST be a directory :" + workingDirectory.getAbsolutePath());
			}
			if (!workingDirectory.canWrite())
			{
				throw new IllegalArgumentException("path MUST be writable :" + workingDirectory.getAbsolutePath());
			}
		}
		myWorkingDirectory = workingDirectory;
	}

	/**
	 * @return the generatedClassesRegistry
	 */
	public Set<String> getGeneratedClassesRegistry()
	{
		return myGeneratedClassesRegistry;
	}

	/**
	 * @param generatedClassesRegistry
	 *            the generatedClassesRegistry to set
	 */
	public void setGeneratedClassesRegistry(Set<String> generatedClassesRegistry)
	{
		myGeneratedClassesRegistry = generatedClassesRegistry;
		myBeanGenerator.setGeneratedClassesRegistry(generatedClassesRegistry);
	}

}
