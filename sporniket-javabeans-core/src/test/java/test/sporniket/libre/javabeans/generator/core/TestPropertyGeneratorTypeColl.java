/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.sporniket.libre.io.TextLoader;
import com.sporniket.libre.javabeans.generator.core.PropertyGeneratorTypeColl;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Suite for testing property generation for property with <code>type="coll:..."</code>.
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
 */
public class TestPropertyGeneratorTypeColl extends TestPropertyGenerator
{
	/**
	 * Name of the result file for generating a named enum property.
	 */
	private static String FILE_NAME_TEMPLATE__COLL = "result_property_coll_{0}_{1}.txt";

	/**
	 * Template for computing the name of the result file.
	 */
	private MessageFormat myFileNameTemplateColl = new MessageFormat(FILE_NAME_TEMPLATE__COLL);

	/**
	 * Generator to test.
	 */
	private PropertyGeneratorTypeColl myGenerator;

	/**
	 * Registry pairing a template file name and the template (in other words, it's a cache).
	 */
	private Map<String, String> myResultRegistry = new HashMap<String, String>();

	/**
	 * @param mode
	 * @throws IOException
	 */
	private void doTestMode(String typeValue, String collectionMode, PropertyMode propertyMode) throws IOException
	{
		Property _testProp = doTestMode__createDummyProperty(typeValue, propertyMode);
		doTestMode__checkPropertyGeneration(_testProp, myDummyBean, null, null, getResult(getResultName(collectionMode, propertyMode)));
	}

	/**
	 * Generate the property in memory and compare with a reference.
	 * 
	 * @param property
	 * @param expectedResult
	 */
	private void doTestMode__checkPropertyGeneration(Property property, Bean bean, Package pack, BeanSet set, String expectedResult)
	{
		try
		{
			Utils.checkPropertyGenerator(myGenerator, property, bean, pack, set, expectedResult);
		}
		catch (IllegalStateException _exception)
		{
			fail(_exception.getMessage());
		}
	}

	/**
	 * @param mode
	 * @return a test property with the specified mode.
	 */
	private Property doTestMode__createDummyProperty(String typeValue, PropertyMode propertyMode)
	{
		Property _testProp = new Property();
		_testProp.setName("usefullProperty");
		_testProp.setType(typeValue);
		_testProp.setMode(propertyMode);
		return _testProp;
	}

	/**
	 * Return the specified result from the cache or from loading it if it is not in the cache yet.
	 * 
	 * @param resultName
	 * @return
	 * @throws IOException
	 */
	private String getResult(String resultName) throws IOException
	{
		String _result = null;
		if (!myResultRegistry.containsKey(resultName))
		{
			_result = TextLoader.getInstance().load(getClass().getResourceAsStream(resultName));
			myResultRegistry.put(resultName, _result);
		}
		else
		{
			_result = myResultRegistry.get(resultName);
		}
		return _result;
	}

	/**
	 * Compute template name.
	 * 
	 * @param collectionMode
	 * @param mode
	 * @return
	 */
	private String getResultName(String collectionMode, PropertyMode mode)
	{
		Object[] _params =
		{
				collectionMode, mode.value()
		};
		return myFileNameTemplateColl.format(_params);
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

		myGenerator = new PropertyGeneratorTypeColl();

	}

	/**
	 * Test a not null <code>basic</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testNotNullBasicColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:notNull:0", "notNull", PropertyMode.BASIC);
	}

	/**
	 * Test a not null <code>basic</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testIsolatedBasicColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:isolated:10", "isolated", PropertyMode.BASIC);
	}

	/**
	 * Test a not null <code>boundable</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testNotNullBoundableColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:notNull:0", "notNull", PropertyMode.BOUNDABLE);
	}

	/**
	 * Test a not null <code>boundable</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testIsolatedBoundableColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:isolated:10", "isolated", PropertyMode.BOUNDABLE);
	}

	/**
	 * Test a not null <code>boundable</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testNotNullVetoableColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:notNull:0", "notNull", PropertyMode.VETOABLE);
	}

	/**
	 * Test a not null <code>boundable</code> collection.
	 * 
	 * @throws IOException
	 */
	public void testIsolatedVetoableColl() throws IOException
	{
		doTestMode("coll:List:ArrayList:String:isolated:10", "isolated", PropertyMode.VETOABLE);
	}

}
