/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.libre.io.MessageFormatLoader;
import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;

/**
 * Simple implementation of the {@link BeanGenerator}.
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
 * @since 13.01.01
 * 
 */
public class BeanGeneratorBase implements BeanGenerator
{
	/**
	 * Template of the "extends" directive for beans having a super class.
	 */
	private static final String TEMPLATE_CODE__EXTENDS = "extends {0}";

	/**
	 * Name of the template file for the begining of the class (before generating properties).
	 */
	private static final String TEMPLATE_NAME__CLASS_BEGIN = "template_bean_class_begin.txt";

	/**
	 * Name of the template file for the begining of the class (before generating properties), for a classe extending another
	 * generated class.
	 */
	private static final String TEMPLATE_NAME__CLASS_BEGIN_EXTENDS_LOCAL = "template_bean_class_extends_local_begin.txt";

	/**
	 * Name of the template file for the begining of the class (after generating properties).
	 */
	private static final String TEMPLATE_NAME__CLASS_END = "template_bean_class_end.txt";

	/**
	 * Registry to find out if an extended classe needs property change management code.
	 * 
	 * Will be set by {@link PackageProcessor}.
	 */
	private Set<String> myGeneratedClassesRegistry = new TreeSet<String>();

	/**
	 * Registry of {@link PropertyGenerator}.
	 * 
	 * There is a {@link PropertyGenerator} for each type of property.
	 */
	private Map<String, PropertyGenerator> myPropertyGeneratorRegistry = new HashMap<String, PropertyGenerator>();

	/**
	 * Template to generate the part before the java code of the properties.
	 */
	private MessageFormat myTemplateClassBegin;

	/**
	 * Template to generate the part before the java code of the properties, for a classe extending another generated class.
	 */
	private MessageFormat myTemplateClassBeginExtendsLocal;

	/**
	 * Template to generate the part after the java code of the properties.
	 */
	private MessageFormat myTemplateClassEnd;

	/**
	 * Formatter when needing "extends xxx".
	 */
	private MessageFormat myTemplateCodeExtends = new MessageFormat(TEMPLATE_CODE__EXTENDS);

	/**
	 * @throws IOException
	 *             if there is a problem to deal with.
	 * 
	 */
	public BeanGeneratorBase() throws IOException
	{
		BeanGeneratorBase__loadTemplates();
		BeanGeneratorBase__initGenerators();
	}

	private void BeanGeneratorBase__initGenerators() throws IOException
	{
		myPropertyGeneratorRegistry.put(PropertyType.Prefix.JAVA.getName(), new PropertyGeneratorTypeJava());
		myPropertyGeneratorRegistry.put(PropertyType.Prefix.ENUM.getName(), new PropertyGeneratorTypeEnum());
		myPropertyGeneratorRegistry.put(PropertyType.Prefix.COLL.getName(), new PropertyGeneratorTypeColl());
		myPropertyGeneratorRegistry.put(PropertyType.Prefix.MAP.getName(), new PropertyGeneratorTypeMap());
	}

	private void BeanGeneratorBase__loadTemplates() throws IOException
	{
		myTemplateClassBegin = MessageFormatLoader.load(getClass().getResourceAsStream(TEMPLATE_NAME__CLASS_BEGIN));
		myTemplateClassBeginExtendsLocal = MessageFormatLoader.load(getClass().getResourceAsStream(
				TEMPLATE_NAME__CLASS_BEGIN_EXTENDS_LOCAL));
		myTemplateClassEnd = MessageFormatLoader.load(getClass().getResourceAsStream(TEMPLATE_NAME__CLASS_END));
	}

	/**
	 * Return "extends xxx" or an empty string.
	 * 
	 * @param value
	 *            class name to extends, may be empty.
	 * @return an empty string if value is empty.
	 */
	private String getExtendsCode(String value)
	{
		if (StringTools.isEmptyString(value))
		{
			return "";
		}
		else
		{
			Object[] _params =
			{
				value
			};
			return myTemplateCodeExtends.format(_params);
		}
	}

	/**
	 * @return the generatedClassesRegistry
	 */
	public Set<String> getGeneratedClassesRegistry()
	{
		return myGeneratedClassesRegistry;
	}

	public void outputBeanJavaCode(PrintWriter out, Bean bean, Package pack, BeanSet set)
	{
		outputBeanJavaCode__outputClassBegin(out, bean, pack, set);
		outputBeanJavaCode__outputProperties(out, bean, pack, set);
		outputBeanJavaCode__outputClassEnd(out, bean, pack, set);
	}

	public void outputBeanJavaCode__outputClassBegin(PrintWriter out, Bean bean, Package pack, BeanSet set)
	{
		Object[] _params =
		{
				pack.getName(),
				bean.getAnnotation().getSummary(),
				JavadocUtils.generateDescription(bean),
				JavadocUtils.generateLicence(set),
				set.getAnnotation().getVersion(),
				JavadocUtils.generateAuthor(set),
				JavadocUtils.generateSee(bean),
				bean.getName(),
				getExtendsCode(bean.getExtends()),
				bean.isAbstract() ? "abstract" : "",
				bean.isPrivate() ? "" : "public"
		};
		String _classFullyQualifiedName = pack.getName() + "." + bean.getExtends();
		MessageFormat _format = ((!StringTools.isEmptyString(bean.getExtends())) && (myGeneratedClassesRegistry
				.contains(_classFullyQualifiedName))) ? myTemplateClassBeginExtendsLocal : myTemplateClassBegin;
		out.println(_format.format(_params));

	}

	public void outputBeanJavaCode__outputClassEnd(PrintWriter out, Bean bean, Package pack, BeanSet set)
	{
		Object[] _params =
		{
			new Date()
		};
		out.println(myTemplateClassEnd.format(_params));
	}

	public void outputBeanJavaCode__outputProperties(PrintWriter out, Bean bean, Package pack, BeanSet set)
	{
		Property[] _properties = bean.getProperty();
		for (Property _property : _properties)
		{
			PropertyType _propertyType = PropertyType.Utils.instanciate(_property.getType());
			String _typeType = _propertyType.getPrefix().getName();
			if (!myPropertyGeneratorRegistry.containsKey(_typeType))
			{
				throw new IllegalArgumentException("No generator for property of type " + _typeType);
			}
			PropertyGenerator _generator = myPropertyGeneratorRegistry.get(_typeType);
			_generator.outputPropertyJavaCode(out, _propertyType, _property, bean, pack, set);
		}
	}

	/**
	 * @param generatedClassesRegistry
	 *            the generatedClassesRegistry to set
	 */
	public void setGeneratedClassesRegistry(Set<String> generatedClassesRegistry)
	{
		myGeneratedClassesRegistry = generatedClassesRegistry;
	}

}
