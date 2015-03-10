/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import junit.framework.TestCase;

import com.sporniket.libre.io.TextLoader;
import com.sporniket.libre.javabeans.generator.core.BeanGeneratorBase;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;

/**
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
public class TestBeanGeneratorBase extends TestCase
{
	/**
	 * Name of the result file for generating a named enum property.
	 */
	private static String FILE_NAME__BEAN = "result_bean.txt";

	private static String FILE_NAME__BEAN_EXTENDS = "result_bean_extends.txt";

	private static String FILE_NAME__BEAN_EXTENDS_LOCAL = "result_bean_extends_local.txt";

	private String myExpectedResult;

	private String myExpectedResultExtends;

	private String myExpectedResultExtendsLocal;

	private BeanGeneratorBase myGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		myGenerator = new BeanGeneratorBase();

		myExpectedResult = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN));
		myExpectedResultExtends = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN_EXTENDS));
		myExpectedResultExtendsLocal = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN_EXTENDS_LOCAL));
	}

	public void testBeanGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		
		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResult);
	}

	public void testBeanExtendsGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setExtends("MotherClass");
		
		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultExtends);
	}

	public void testBeanExtendsLocalGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setExtends("MotherClass");
		myGenerator.getGeneratedClassesRegistry().add(_package.getName() + "." + _bean.getExtends());

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultExtendsLocal);
	}

}
