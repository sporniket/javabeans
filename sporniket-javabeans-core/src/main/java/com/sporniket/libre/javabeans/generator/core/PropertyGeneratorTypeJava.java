/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.io.MessageFormatLoader;
import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Implementation for property having type="java:...".
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
public class PropertyGeneratorTypeJava implements PropertyGenerator
{
	/**
	 * Name of the template file for generating a basic property.
	 */
	private static String TEMPLATE_NAME__BASIC = "template_property_basic{0}.txt";

	/**
	 * Name of the template file for generating a boundable property.
	 */
	private static String TEMPLATE_NAME__BOUNDABLE = "template_property_boundable{0}.txt";

	/**
	 * Name of the template file for generating a vetoable property.
	 */
	private static String TEMPLATE_NAME__VETOABLE = "template_property_vetoable{0}.txt";

	/**
	 * Registry of templates
	 */
	private Map<PropertyMode, MessageFormat> myTemplateRegistry = new HashMap<PropertyMode, MessageFormat>();

	/**
	 * @param templateSuffix
	 *            the suffix to add to the template file names.
	 * @throws IOException
	 *             if there is a problem to deal with.
	 */
	public PropertyGeneratorTypeJava(String templateSuffix) throws IOException
	{
		init__loadTemplates(templateSuffix);
	}

	/**
	 * Initialisation : fill templateRegistry.
	 * 
	 * @param templateSuffix
	 * 
	 * @throws IOException
	 *             if there is a problem to deal with.
	 */
	private void init__loadTemplates(String templateSuffix) throws IOException
	{
		Object[] _args =
		{
			templateSuffix
		};
		myTemplateRegistry.put(PropertyMode.BASIC, MessageFormatLoader.load(getClass().getResourceAsStream(MessageFormat.format(TEMPLATE_NAME__BASIC, _args))));
		myTemplateRegistry.put(PropertyMode.BOUNDABLE,
				MessageFormatLoader.load(getClass().getResourceAsStream(MessageFormat.format(TEMPLATE_NAME__BOUNDABLE, _args))));
		myTemplateRegistry.put(PropertyMode.VETOABLE,
				MessageFormatLoader.load(getClass().getResourceAsStream(MessageFormat.format(TEMPLATE_NAME__VETOABLE, _args))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.javabeans.generator.core.PropertyGenerator#outputPropertyJavaCode(java.io.PrintWriter,
	 * com.sporniket.javabeans.generator.core.PropertyType, com.sporniket.studio.schema.model.set.javabean.Property,
	 * com.sporniket.studio.schema.model.set.javabean.Bean, com.sporniket.studio.schema.model.set.javabean.Package,
	 * com.sporniket.studio.schema.model.set.javabean.BeanSet)
	 */
	public void outputPropertyJavaCode(PrintWriter out, PropertyType propertyType, Property property, Bean bean, Package pack,
			BeanSet set)
	{
		Name _name = Name.createFromJavabeanPropertyName(property.getName());
		String _type = propertyType.getDescription();
		String _sees = JavadocUtils.generateSee(property);
		String _description = (null != property.getAnnotation()) ? JavadocUtils.concatenateStringArray(property.getAnnotation()
				.getDescription()) : "";
		String _summary = (null != property.getAnnotation()) ? property.getAnnotation().getSummary() : "";
		String _initialExpression = (!StringTools.isEmptyString(property.getInitialExpression())) ? "= "
				+ property.getInitialExpression() : "";
		Object[] _params =
		{
				_name.getUncapitalized(),
				_name.getCapitalized(),
				_name.getAllCaps(),
				_type,
				_description,
				_sees,
				_summary,
				"",
				"",
				"",
				"",
				bean.getName(),
				_initialExpression
		};
		MessageFormat _template = myTemplateRegistry.get(property.getMode());
		out.println(_template.format(_params));

	}

}
