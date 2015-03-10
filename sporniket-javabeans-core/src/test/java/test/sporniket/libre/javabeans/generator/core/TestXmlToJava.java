package test.sporniket.libre.javabeans.generator.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.sporniket.libre.javabeans.generator.core.BeanSetProcessor;
import com.sporniket.libre.lang.SystemProperties;
import com.sporniket.libre.lang.string.QuickDiff;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;

/**
 * Load an Xml an generate Java classes.
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr />
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
 * core</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * @version 13.01.01
 * 
 */
public class TestXmlToJava extends TestCase
{
	private static final String XML_FILE__SINGLE_BEAN = "singleBean.xml";

	private static final String XML_FILE__PROJECT_FOLDER = "test-javabean-generator";

	private BeanSetProcessor myProcessor;

	private String myTempPath;

	/**
	 * This test is about just being able to run the code without error.
	 */
	public void testCanRunSingleClassGeneration()
	{
		InputStream _xml = getClass().getResourceAsStream(XML_FILE__SINGLE_BEAN);
		try
		{
			BeanSet _testSet = BeanSet.unmarshalBeanSet(new InputStreamReader(_xml));
			myProcessor.process(_testSet);
		}
		catch (MarshalException _exception)
		{
			fail("error reading xml : " + _exception.getMessage());
		}
		catch (ValidationException _exception)
		{
			fail("error reading xml : " + _exception.getMessage());
		}
		catch (FileNotFoundException _exception)
		{
			fail("error while processing bean set " + _exception);
		}
	}

	/**
	 * This test is like {@link #testCanRunSingleClassGeneration()} but this time it checks that expected files are present.
	 */
	public void testSingleClassGenerationAndCheckFilePresence()
	{
		InputStream _xml = getClass().getResourceAsStream(XML_FILE__SINGLE_BEAN);
		try
		{
			BeanSet _testSet = BeanSet.unmarshalBeanSet(new InputStreamReader(_xml));
			myProcessor.process(_testSet);

			// scan generated files
			String _path = _testSet.getPackage(0).getName().replace('.', File.separatorChar);
			File _folder = new File(myProcessor.getWorkingDirectory(), _path);
			if (!_folder.exists())
			{
				fail("Generated folder not found : " + _folder.getAbsolutePath());
			}
			if (!_folder.isDirectory())
			{
				fail("Generated folder is not a folder : " + _folder.getAbsolutePath());
			}
			String[] _generatedFiles = sortList(_folder.list());
			String[] _expectedFiles =
			{
					"package-info.java", "MyUsefullBean.java"
			};
			_expectedFiles = sortList(_expectedFiles);
			String[] _diffReport = QuickDiff.reportDiff(_generatedFiles, _expectedFiles, true, true);
			if (0 != _diffReport.length)
			{
				String _message = buildErrorMessage("The generated files list is not as expected", _diffReport);
				fail(_message);
			}
		}
		catch (Exception _exception)
		{
			fail("error while processing " + _exception);
		}

	}

	/**
	 * @param list
	 * @return
	 */
	private String[] sortList(String[] list)
	{
		Set<String> _temp = new TreeSet<String>();
		for (String _item : list)
		{
			_temp.add(_item);
		}
		return _temp.toArray(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		myProcessor = new BeanSetProcessor();
		myTempPath = SystemProperties.Protected.getTempDirectory();
		File _temp = new File(myTempPath, XML_FILE__PROJECT_FOLDER);
		_temp.mkdirs();
		myProcessor.setWorkingDirectory(_temp);
	}

	/**
	 * @param errorMessage
	 * @param diffReport
	 * @return
	 */
	private String buildErrorMessage(String errorMessage, String[] diffReport)
	{
		StringBuilder _messageBuffer = new StringBuilder(errorMessage);
		for (String _line : diffReport)
		{
			_messageBuffer.append(_line).append("\n");
		}
		String _message = _messageBuffer.toString();
		return _message;
	}

}
