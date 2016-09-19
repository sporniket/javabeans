package com.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.sporniket.libre.io.MessageFormatLoader;
import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;

/**
 * Implementation for property having type="enum:...".
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
 * @since 13.01.01
 * 
 */
public class PropertyGeneratorTypeEnum implements PropertyGenerator
{
	/**
	 * Name of the template file for generating the enum.
	 */
	private static String TEMPLATE_NAME__ENUM = "template_property_enum.txt";

	private final PropertyGenerator myNormalGenerator = new PropertyGeneratorTypeJava(GeneratorUtils.TEMPLATE_SUFFIX__GETTER_SETTER);;
	
	private final PropertyGenerator myNormalGeneratorFluent =new PropertyGeneratorTypeJava(GeneratorUtils.TEMPLATE_SUFFIX__FLUENT_SETTER);;
	
	private MessageFormat myTemplateEnum;

	public PropertyGeneratorTypeEnum() throws IOException
	{
		PropertyGeneratorTypeEnum__createGenerator();
		PropertyGeneratorTypeEnum__loadTemplate();
	}

	public void outputPropertyJavaCode(PrintWriter out, PropertyType propertyType, Property property, Bean bean, Package pack,
			BeanSet set)
	{
		// prepare template parameters
		String _enumJavaName = null;
		PropertyTypeEnum _propertyType = (PropertyTypeEnum) propertyType;
		if (_propertyType.isAnonymous())
		{
			Name _name = Name.createFromJavabeanPropertyName(property.getName());
			_enumJavaName = _name.getCapitalized();
		}
		else
		{
			_enumJavaName = _propertyType.getEnumJavaName();
		}

		// generate enum part
		Object[] _params =
		{
				_enumJavaName, _propertyType.getEnumValues(), property.getName()
		};
		out.println(myTemplateEnum.format(_params));

		// compute real initial expression to use with a regular generator
		String _initialExpression = (!StringTools.isEmptyString(property.getInitialExpression()))?_enumJavaName+"."+property.getInitialExpression():"" ;
		property.setInitialExpression(_initialExpression);
		
		// substitute type to use normal generator.
		String _oldType = property.getType();
		String _fakeType = PropertyType.Prefix.JAVA + PropertyType.MARKER__SEPARATOR + _enumJavaName;
		property.setType(_fakeType);
		outputPropertyJavaCode__outputRegularCode(out, _enumJavaName, property, bean, pack, set);
		property.setType(_oldType);

	}

	/**
	 * @param out output.
	 * @param enumJavaName
	 *            Java name of the enum.
	 * @param property property to generate.
	 * @param bean bean definition for reference.
	 * @param pack package definition for reference.
	 * @param set set for reference.
	 */
	protected void outputPropertyJavaCode__outputRegularCode(PrintWriter out, String enumJavaName, Property property, Bean bean,
			Package pack, BeanSet set)
	{
		PropertyType _fakePropertyType = PropertyType.Utils.instanciate(property.getType());
		myNormalGenerator.outputPropertyJavaCode(out, _fakePropertyType, property, bean, pack, set);
		myNormalGeneratorFluent.outputPropertyJavaCode(out, _fakePropertyType, property, bean, pack, set);
	}

	private void PropertyGeneratorTypeEnum__createGenerator() throws IOException
	{
		//nothing to do.
	}

	private void PropertyGeneratorTypeEnum__loadTemplate() throws IOException
	{
		myTemplateEnum = MessageFormatLoader.load(getClass().getResourceAsStream(TEMPLATE_NAME__ENUM));
	}
}
