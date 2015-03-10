/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.sporniket.libre.io.TextLoader;
import com.sporniket.libre.javabeans.generator.core.PropertyGeneratorTypeEnum;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Suite for testing property generation for property with <code>type="enum:..."</code>.
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
 * core</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * @version 13.01.01
 * 
 */
public class TestPropertyGeneratorTypeEnum extends TestPropertyGenerator
{
	/**
	 * Name of the result file for generating an anonymous enum property.
	 */
	private static String FILE_NAME__ENUM_ANONYMOUS = "result_property_enum_anonymous.txt";

	/**
	 * Name of the result file for generating a named enum property.
	 */
	private static String FILE_NAME__ENUM_NAMED = "result_property_enum_named.txt";

	/**
	 * Value of the "type" attribute for an anonymous enum property.
	 */
	private static String TYPE_VALUE__ENUM_ANONYMOUS = "enum::TRUE,FALSE,CANNOT_SAY";

	/**
	 * Value of the "type" attribute for a named enum property.
	 */
	private static String TYPE_VALUE__ENUM_NAMED = "enum:NamedValues:YES,NO,MAYBE";

	/**
	 * Generator to test.
	 */
	private PropertyGeneratorTypeEnum myGenerator;

	/**
	 * Map type of property to the reference code to compare with the generated code.
	 */
	private Map<String, String> myResultRegistry = new HashMap<String, String>();

	/**
	 * @param mode
	 */
	private void doTestMode(String typeValue)
	{
		Property _testProp = doTestMode__createDummyProperty(typeValue);
		doTestMode__checkPropertyGeneration(_testProp, myDummyBean, null, null, myResultRegistry.get(typeValue));
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
	private Property doTestMode__createDummyProperty(String typeValue)
	{
		Property _testProp = new Property();
		_testProp.setName("usefullProperty");
		_testProp.setType(typeValue);
		_testProp.setMode(PropertyMode.BASIC);
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

		myGenerator = new PropertyGeneratorTypeEnum();

		myResultRegistry.put(TYPE_VALUE__ENUM_NAMED,
				TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__ENUM_NAMED)));

		myResultRegistry.put(TYPE_VALUE__ENUM_ANONYMOUS,
				TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__ENUM_ANONYMOUS)));
	}

	/**
	 * Test the <code>basic</code> mode.
	 */
	public void testAnonymousEnumProperty()
	{
		doTestMode(TYPE_VALUE__ENUM_ANONYMOUS);
	}

	/**
	 * Test the <code>basic</code> mode.
	 */
	public void testNamedEnumProperty()
	{
		doTestMode(TYPE_VALUE__ENUM_NAMED);
	}

}
