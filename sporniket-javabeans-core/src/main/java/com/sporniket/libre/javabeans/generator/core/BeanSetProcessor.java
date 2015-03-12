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
import com.sporniket.studio.schema.model.set.javabean.GroupAnnotation;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.SetAnnotation;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Process a bean set.
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
public class BeanSetProcessor implements FileGenerator
{

	/**
	 * Registry to find out if an extended classe needs property change management code.
	 */
	private Set<String> myGeneratedClassesRegistry = new TreeSet<String>();

	/**
	 * Directory where the processor will be working
	 */
	private File myWorkingDirectory;

	private PackageProcessor myPackageProcessor;

	/**
	 * @throws IOException if there is a problem to deal with.
	 * 
	 */
	public BeanSetProcessor() throws IOException
	{
		myPackageProcessor = new PackageProcessor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.io.FileGenerator#getWorkingDirectory()
	 */
	public File getWorkingDirectory()
	{
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
		myPackageProcessor.setWorkingDirectory(workingDirectory);
	}

	/**
	 * @param set set to process.
	 * @throws FileNotFoundException if there is a problem to deal with.
	 */
	public void process(BeanSet set) throws FileNotFoundException
	{
		// sanity check
		if (null == getWorkingDirectory())
		{
			throw new IllegalStateException("This processor has not been set up properly : no working Directory specified");
		}

		registerGeneratedClasses(set);

		// ok
		for (Package _package : set.getPackage())
		{
			myPackageProcessor.process(set, _package);
		}
	}

	/**
	 * @param set
	 */
	private void registerGeneratedClasses(BeanSet set)
	{
		myGeneratedClassesRegistry.clear();
		for (Package _package : set.getPackage())
		{
			String _classPrefix = _package.getName() + ".";
			for (Bean _bean : _package.getBean())
			{
				myGeneratedClassesRegistry.add(_classPrefix + _bean.getName());
			}
		}
		myPackageProcessor.setGeneratedClassesRegistry(myGeneratedClassesRegistry);
	}

	public static void main(String[] args)
	{
		try
		{
			BeanSetProcessor _generator = new BeanSetProcessor();
			// do stuff
			Property _creationDate = new Property();
			_creationDate.setMode(PropertyMode.BASIC);
			_creationDate.setName("creationDate");
			_creationDate.setType("java:java.util.Date");

			Property _editDate = new Property();
			_editDate.setMode(PropertyMode.BOUNDABLE);
			_editDate.setName("editDate");
			_editDate.setType("java:java.util.Date");

			Property _deleteDate = new Property();
			_deleteDate.setMode(PropertyMode.VETOABLE);
			_deleteDate.setName("deleteDate");
			_deleteDate.setType("java:java.util.Date");

			Property _namedFlag = new Property();
			_namedFlag.setMode(PropertyMode.BOUNDABLE);
			_namedFlag.setName("testNamedEnumFlag");
			_namedFlag.setType("enum:NamedValues:YES,NO,MAYBE");

			Property _anonymousFlag = new Property();
			_anonymousFlag.setMode(PropertyMode.VETOABLE);
			_anonymousFlag.setName("testAnonymousEnumFlag");
			_anonymousFlag.setType("enum::TRUE,FALSE,CANNOT_SAY");

			Property[] _properties =
			{
					_creationDate, _editDate, _deleteDate, _namedFlag, _anonymousFlag
			};

			Bean _testBean = new Bean();
			_testBean.setName("MyUsefullBean");
			_testBean.setProperty(_properties);
			_testBean.setAnnotation(new GroupAnnotation());
			_testBean.getAnnotation().setSummary("A usefull Bean that provide usefull fields");
			_testBean.getAnnotation().addSee("String");
			_testBean.getAnnotation().addSee("Integer");

			Package _testPackage = new Package();
			_testPackage.setName("test.sporniket.javabeans.generator");
			_testPackage.addBean(_testBean);

			BeanSet _testBeanSet = new BeanSet();
			_testBeanSet.addPackage(_testPackage);
			_testBeanSet.setAnnotation(new SetAnnotation());
			_testBeanSet.getAnnotation().setVersion("1.0.5");
			_testBeanSet.getAnnotation().addSee("Boolean");
			_testBeanSet.getAnnotation().addSee("Float");

			PrintWriter _out = new PrintWriter(System.out);
			_generator.setWorkingDirectory(new File("C:\\Temp"));
			_generator.process(_testBeanSet);
			_out.flush();

		}
		catch (Exception _exception)
		{
			_exception.printStackTrace();
		}
	}

}
