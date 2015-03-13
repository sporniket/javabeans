/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.sporniket.libre.io.TextLoader;
import com.sporniket.libre.javabeans.generator.core.PropertyGeneratorTypeJava;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.PropertyAnnotation;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Suite for testing property generation for property with <code>type="java:..."</code>.
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
 * <i>The Sporniket Javabeans Library &#8211; core</i> is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
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
 * 
 */
public class TestPropertyGeneratorTypeJava extends TestPropertyGenerator
{
	/**
	 * Name of the result file for generating a basic property.
	 */
	private static String FILE_NAME__BASIC = "result_property_basic.txt";

	/**
	 * Name of the result file for generating a boundable property.
	 */
	private static String FILE_NAME__BOUNDABLE = "result_property_boundable.txt";

	/**
	 * Name of the result file for generating a vetoable property.
	 */
	private static String FILE_NAME__VETOABLE = "result_property_vetoable.txt";

	/**
	 * Generator to test.
	 */
	private PropertyGeneratorTypeJava myGenerator;

	/**
	 * Map type of property to the reference code to compare with the generated code.
	 */
	private Map<PropertyMode, String> myResultRegistry = new HashMap<PropertyMode, String>();

	/**
	 * @param mode
	 */
	private void doTestMode(PropertyMode mode)
	{
		Property _testProp = doTestMode__createDummyProperty(mode);
		doTestMode__checkPropertyGeneration(_testProp, myDummyBean, null, null, myResultRegistry.get(mode));
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
	private Property doTestMode__createDummyProperty(PropertyMode mode)
	{
		Property _testProp = new Property();
		_testProp.setName("usefullProperty");
		_testProp.setType("java:java.util.Date");
		_testProp.setMode(mode);

		PropertyAnnotation _testAnnotation = new PropertyAnnotation();
		_testAnnotation.setSummary("An interestingly usefull property");
		_testAnnotation.addDescription("<p>First paragraph of first description</p>\n<p>Second paragraph of first description</p>");
		_testAnnotation.addDescription("\nsecond description with no html tags.");
		_testAnnotation.addSee("First see for property");
		_testAnnotation.addSee("Second see for property");
		_testProp.setAnnotation(_testAnnotation);

		return _testProp;
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

		myGenerator = new PropertyGeneratorTypeJava();

		myResultRegistry.put(PropertyMode.BASIC, TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BASIC)));

		myResultRegistry.put(PropertyMode.BOUNDABLE,
				TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BOUNDABLE)));

		myResultRegistry.put(PropertyMode.VETOABLE,
				TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__VETOABLE)));

	}

	/**
	 * Test the <code>basic</code> mode.
	 */
	public void testBasicMode()
	{
		doTestMode(PropertyMode.BASIC);
	}

	/**
	 * Test the <code>basic</code> mode.
	 */
	public void testBoundableMode()
	{
		doTestMode(PropertyMode.BOUNDABLE);
	}

	/**
	 * Test the <code>vetoable</code> mode.
	 */
	public void testVetoableMode()
	{
		doTestMode(PropertyMode.VETOABLE);
	}
}
