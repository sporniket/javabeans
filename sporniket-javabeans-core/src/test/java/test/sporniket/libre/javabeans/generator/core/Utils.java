/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.sporniket.libre.javabeans.generator.core.BeanGenerator;
import com.sporniket.libre.javabeans.generator.core.PackageGenerator;
import com.sporniket.libre.javabeans.generator.core.PropertyGenerator;
import com.sporniket.libre.javabeans.generator.core.PropertyType;
import com.sporniket.libre.lang.string.QuickDiff;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.GroupAnnotation;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.SetAnnotation;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

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
 * 
 */
public class Utils
{
	public static BeanSet createDummyBeanSet()
	{
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

		Property _notNullBasicCollection = new Property();
		_notNullBasicCollection.setMode(PropertyMode.BASIC);
		_notNullBasicCollection.setName("notNullBasicCollection");
		_notNullBasicCollection.setType("coll:Set:TreeSet:String:notNull");

		Property[] _properties =
		{
				_creationDate, _editDate, _deleteDate, _notNullBasicCollection
		};

		Bean _testBean = new Bean();
		_testBean.setName("MyUsefullBean");
		_testBean.setProperty(_properties);
		_testBean.setAnnotation(new GroupAnnotation());
		_testBean.getAnnotation().setSummary("A usefull Bean that provide usefull fields");
		_testBean.getAnnotation().addDescription("It is underlined that this bean is very usefull.");
		_testBean.getAnnotation().addDescription("<p>On a sidenote, nevermind.");
		_testBean.getAnnotation().addSee("http://bean.example.com");

		Package _testPackage = new Package();
		_testPackage.setName("test.sporniket.javabeans.generator");
		_testPackage.addBean(_testBean);
		_testPackage.setAnnotation(new GroupAnnotation());
		_testPackage.getAnnotation().setSummary("A bunch of usefull beans");
		_testPackage.getAnnotation().addDescription("It is underlined that these beans are very usefull.");
		_testPackage.getAnnotation().addDescription("<p>On a sidenote, please see by yourself.");
		_testPackage.getAnnotation().addSee("http://package.example.com");

		BeanSet _testBeanSet = new BeanSet();
		_testBeanSet.addPackage(_testPackage);
		_testBeanSet.setAnnotation(new SetAnnotation());
		_testBeanSet.getAnnotation().setVersion("1.0.5");
		_testBeanSet.getAnnotation().addLicencenotice("<p>(c)2012 David SPORN");
		_testBeanSet.getAnnotation().addLicencenotice("<pre>GPL notice</pre>");
		_testBeanSet.getAnnotation().addAuthor("David SPORN");
		_testBeanSet.getAnnotation().addSee("http://global.example.com");

		return _testBeanSet;
	}

	/**
	 * Generate the property in memory and compare with a reference.
	 * 
	 * @param generator
	 * @param property
	 * @param bean
	 * @param pack
	 * @param set
	 * @param expectedResult
	 * @throws IllegalStateException
	 *             if the check has failed.
	 */
	public static void checkPropertyGenerator(PropertyGenerator generator, Property property, Bean bean, Package pack, BeanSet set,
			String expectedResult) throws IllegalStateException
	{
		StringWriter _buffer = new StringWriter(2048); // buffer size = 2Ko, must be enough for most cases.
		PrintWriter _out = new PrintWriter(_buffer);
		PropertyType _propertyType = PropertyType.Utils.instanciate(property.getType());
		generator.outputPropertyJavaCode(_out, _propertyType, property, bean, pack, set);
		String _result = _buffer.toString();
		String[] _resultLines = _result.split("\\n");
		String[] _expectedLines = expectedResult.split("\\n");
		String[] _report = QuickDiff.reportDiff(_resultLines, _expectedLines, true, true);
		if (0 != _report.length)
		{
			StringBuilder _reportText = new StringBuilder();
			for (String _line : _report)
			{
				_reportText.append(_line).append("\n");
			}
			String _message = "KO, there are differences : " + property.getName() + " // " + property.getMode() + " // "
					+ property.getType() + " \n\n\n=== DIFF ===\n" + _reportText.toString() + " \n\n\n=== RESULT ===\n" + _result;
			throw new IllegalStateException(_message);
		}
	}

	public static void checkBeanGenerator(BeanGenerator generator, Bean bean, Package pack, BeanSet set,
			String expectedResult) throws IllegalStateException
	{
		StringWriter _buffer = new StringWriter(10204); // buffer size = 10Ko, must be enough for most cases.
		PrintWriter _out = new PrintWriter(_buffer);
		generator.outputBeanJavaCode(_out, bean, pack, set);
		String _result = _buffer.toString();
		String[] _resultLines = _result.split("\\n");
		String[] _expectedLines = expectedResult.split("\\n");
		String[] _report = QuickDiff.reportDiff(_resultLines, _expectedLines, true, true);
		if (1 < _report.length)
		{
			StringBuilder _reportText = new StringBuilder();
			for (String _line : _report)
			{
				_reportText.append(_line).append("\n");
			}
			String _message = "KO, there are differences other than timestamp : " + " \n\n\n=== DIFF ===\n"
					+ _reportText.toString()
					+ " \n\n\n=== RESULT ===\n" + _result;
			throw new IllegalStateException(_message);
		}
	}

	public static void checkPackageGenerator(PackageGenerator generator, Package pack, BeanSet set, String expectedResult)
			throws IllegalStateException
	{
		StringWriter _buffer = new StringWriter(10204); // buffer size = 10Ko, must be enough for most cases.
		PrintWriter _out = new PrintWriter(_buffer);
		generator.outputPackageInfo(_out, pack, set);
		String _result = _buffer.toString();
		String[] _resultLines = _result.split("\\n");
		String[] _expectedLines = expectedResult.split("\\n");
		String[] _report = QuickDiff.reportDiff(_resultLines, _expectedLines, true, true);
		if (0 != _report.length)
		{
			StringBuilder _reportText = new StringBuilder();
			for (String _line : _report)
			{
				_reportText.append(_line).append("\n");
			}
			String _message = "KO, there are differences : " + " \n\n\n=== DIFF ===\n"
					+ _reportText.toString() + " \n\n\n=== RESULT ===\n" + _result;
			throw new IllegalStateException(_message);
		}
	}

	/**
	 * @return
	 */
	static BeanSet createSampleBeanSet()
	{
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
		_testPackage.setName("test.sporniket.javabeans.generator.core");
		_testPackage.addBean(_testBean);
	
		BeanSet _testBeanSet = new BeanSet();
		_testBeanSet.addPackage(_testPackage);
		_testBeanSet.setAnnotation(new SetAnnotation());
		_testBeanSet.getAnnotation().setVersion("1.0.5");
		_testBeanSet.getAnnotation().addSee("Boolean");
		_testBeanSet.getAnnotation().addSee("Float");
		return _testBeanSet;
	}
}
