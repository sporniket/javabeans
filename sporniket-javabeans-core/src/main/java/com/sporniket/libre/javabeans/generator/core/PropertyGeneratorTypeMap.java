package com.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.io.MessageFormatLoader;
import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;
import com.sporniket.studio.schema.model.set.javabean.types.PropertyMode;

/**
 * Implementation for property having type="enum:...".
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
 * 
 */
public class PropertyGeneratorTypeMap implements PropertyGenerator
{
	/**
	 * Template of the name of the template file for generating the enum.
	 * 
	 * <dl>
	 * <dt>0</dt>
	 * <dd>"notNull" or "isolated"</dd>
	 * <dt>1</dt>
	 * <dd>"basic", "boundable" or "vetoable"</dd>
	 * </dl>
	 */
	private static final String FILE_NAME_TEMPLATE__MAP = "template_property_map_{0}_{1}.txt";

	/**
	 * For a map property, the interface is always "Map".
	 */
	private static final String INTERFACE_NAME__MAP = "Map";

	/**
	 * Template for computing the name of the template file.
	 */
	private MessageFormat myFileNameTemplateMap = new MessageFormat(FILE_NAME_TEMPLATE__MAP);

	/**
	 * Registry pairing a template file name and the template (in other words, it's a cache).
	 */
	private Map<String, MessageFormat> myTemplateRegistry = new HashMap<String, MessageFormat>();

	/**
	 * Compute template name.
	 * 
	 * @param collectionMode
	 * @param mode
	 * @return
	 */
	private String getTemplateName(String collectionMode, PropertyMode mode)
	{
		Object[] _params =
		{
				collectionMode, mode.value()
		};
		return myFileNameTemplateMap.format(_params);
	}

	/**
	 * Return the specified template from the cache or from loading it if it is not in the cache yet.
	 * 
	 * @param templateName
	 * @return
	 * @throws IOException
	 */
	private MessageFormat getTemplate(String templateName) throws IOException
	{
		MessageFormat _result = null;
		if (!myTemplateRegistry.containsKey(templateName))
		{
			_result = MessageFormatLoader.load(getClass().getResourceAsStream(templateName));
			myTemplateRegistry.put(templateName, _result);
		}
		else
		{
			_result = myTemplateRegistry.get(templateName);
		}
		return _result;
	}

	public void outputPropertyJavaCode(PrintWriter out, PropertyType propertyType, Property property, Bean bean, Package pack,
			BeanSet set)
	{
		// prepare template parameters
		PropertyTypeMap _propertyType = (PropertyTypeMap) propertyType;
		
		String _templateName = getTemplateName(_propertyType.getMode(), property.getMode());
		
		try
		{
			MessageFormat _template = getTemplate(_templateName);

			Name _name = Name.createFromJavabeanPropertyName(property.getName());
			String _sees = JavadocUtils.generateSee(property);
			String _description = (null != property.getAnnotation()) ? JavadocUtils.concatenateStringArray(property.getAnnotation()
					.getDescription()) : "";
			String _summary = (null != property.getAnnotation()) ? property.getAnnotation().getSummary() : "";
			Object[] _params =
			{
					_name.getUncapitalized(),
					_name.getCapitalized(),
					_name.getAllCaps(),
					INTERFACE_NAME__MAP,
					_description,
					_sees,
					_summary,
					_propertyType.getImplementation(),
					_propertyType.getParametrizationKey(),
					_propertyType.getParametrizationValue(),
					_propertyType.getInstanciationParameters(),
					bean.getName()
			};
			out.println(_template.format(_params));
		}
		catch (IOException _exception)
		{
			throw new IllegalStateException("Error loading template " + _templateName);
		}


	}

}
