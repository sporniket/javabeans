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
public class TestBeanGeneratorBase extends TestCase
{
	/**
	 * Name of the result file for generating a named enum property.
	 */
	private static String FILE_NAME__BEAN = "result_bean.txt";

	private static String FILE_NAME__BEAN__ABSTRACT = "result_bean_abstract.txt";

	private static String FILE_NAME__BEAN__NO_ANNOTATION = "result_bean_no_annotation.txt";

	private static String FILE_NAME__BEAN__PRIVATE = "result_bean_private.txt";

	private static String FILE_NAME__BEAN__PRIVATE_ABSTRACT = "result_bean_private_abstract.txt";

	private static String FILE_NAME__BEAN_EXTENDS = "result_bean_extends.txt";

	private static String FILE_NAME__BEAN_EXTENDS_LOCAL = "result_bean_extends_local.txt";

	private String myExpectedResult;

	private String myExpectedResultAbstract;

	private String myExpectedResultExtends;

	private String myExpectedResultExtendsLocal;

	private String myExpectedResultNoAnnotation;

	private String myExpectedResultPrivate;

	private String myExpectedResultPrivateAbstract;

	private BeanGeneratorBase myGenerator;

	public void testAbstractBeanGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setAbstract(true);

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultAbstract);
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

	public void testBeanGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResult);
	}

	public void testBeanGenerationNoAnnotation()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setAnnotation(null);

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultNoAnnotation);
	}

	public void testPrivateAbstractBeanGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setPrivate(true);
		_bean.setAbstract(true);

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultPrivateAbstract);
	}

	public void testPrivateBeanGeneration()
	{
		BeanSet _set = Utils.createDummyBeanSet();
		Package _package = _set.getPackage(0);
		Bean _bean = _package.getBean(0);
		_bean.setPrivate(true);

		Utils.checkBeanGenerator(myGenerator, _bean, _package, _set, myExpectedResultPrivate);
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

		myGenerator = new BeanGeneratorBase();

		myExpectedResult = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN));
		myExpectedResultNoAnnotation = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN__NO_ANNOTATION));
		myExpectedResultPrivate = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN__PRIVATE));
		myExpectedResultAbstract = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN__ABSTRACT));
		myExpectedResultPrivateAbstract = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN__PRIVATE_ABSTRACT));
		myExpectedResultExtends = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN_EXTENDS));
		myExpectedResultExtendsLocal = TextLoader.getInstance().load(getClass().getResourceAsStream(FILE_NAME__BEAN_EXTENDS_LOCAL));
	}

}
