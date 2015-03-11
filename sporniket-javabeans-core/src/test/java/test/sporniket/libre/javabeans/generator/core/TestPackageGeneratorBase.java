/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import junit.framework.TestCase;

import com.sporniket.libre.io.TextLoader;
import com.sporniket.libre.javabeans.generator.core.PackageGeneratorBase;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;

/**
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
 */
public class TestPackageGeneratorBase extends TestCase
{
	/**
	 * Name of the result file for generating a named enum property.
	 */
	private static String FILE_NAME__PACKAGE = "result_package_info.txt";

	private String myExpectedResult;

	private PackageGeneratorBase myGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		myGenerator = new PackageGeneratorBase();

		myExpectedResult = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__PACKAGE));
	}

	public void testPackageInfoGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		
		Utils.checkPackageGenerator(myGenerator, _package, _set, myExpectedResult);
	}

}
