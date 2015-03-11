/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.text.MessageFormat;

import com.sporniket.studio.schema.model.set.javabean.Bean;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;
import com.sporniket.studio.schema.model.set.javabean.Property;

/**
 * @author DSPORN
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
 */
public class JavadocUtils
{
	/**
	 * Base template for unindented code.
	 */
	private static final String TEMPLATE__AUTHOR = " * @author {0}\n";

	/**
	 * Base template for unindented code.
	 */
	private static final String TEMPLATE__SEE = " * @see {0}\n";

	/**
	 * Base template for indented code.
	 */
	private static final String TEMPLATE__SEE__INDENTED = "\t * @see {0}\n";

	public static String generateAuthor(BeanSet set)
	{
		StringBuilder _result = new StringBuilder();
		if (null != set && null != set.getAnnotation() && null != set.getAnnotation().getAuthor())
		{
			generateJavadocDirectives(TEMPLATE__AUTHOR, set.getAnnotation().getAuthor(), _result);
		}

		return _result.toString();
	}

	public static String generateDescription(Property property)
	{
		return (null != property.getAnnotation()) ? concatenateStringArray(property.getAnnotation().getDescription())
				: "";
	}

	public static String generateDescription(Bean bean)
	{
		return (null != bean.getAnnotation()) ? concatenateStringArray(bean.getAnnotation().getDescription()) : "";
	}

	public static String generateDescription(Package bean)
	{
		return (null != bean.getAnnotation()) ? concatenateStringArray(bean.getAnnotation()
				.getDescription()) : "";
	}

	public static String generateLicence(BeanSet set)
	{
		return (null != set.getAnnotation()) ? concatenateStringArray(set.getAnnotation().getLicencenotice()) : "";
	}

	/**
	 * Concatenate all the description item.
	 * 
	 * @param strings the strings to concatenate.
	 * @return the concatenated strings
	 */
	public static String concatenateStringArray(String[] strings)
	{
		StringBuilder _result = new StringBuilder();
		for (String _description : strings)
		{
			_result.append(_description).append("\n");
		}
		return _result.toString();
	}

	/**
	 * Generate a concatenation of javadoc directives.
	 * 
	 * @param template
	 *            template to use.
	 * @param sees
	 *            list of <em>see</em> directives parameters.
	 * @param _result
	 *            a concatenation of <em>see</em> directives.
	 */
	private static void generateJavadocDirectives(String template, String[] sees, StringBuilder _result)
	{
		MessageFormat _seeTemplate = new MessageFormat(template);
		for (String _see : sees)
		{
			Object[] _params =
			{
				_see
			};
			_result.append(_seeTemplate.format(_params));
		}
	}

	public static String generateSee(Bean bean)
	{
		StringBuilder _result = new StringBuilder();
		if (null != bean && null != bean.getAnnotation() && null != bean.getAnnotation().getSee())
		{
			generateJavadocDirectives(TEMPLATE__SEE, bean.getAnnotation().getSee(), _result);
		}

		return _result.toString();
	}

	public static String generateSee(Package pack, BeanSet set)
	{
		StringBuilder _result = new StringBuilder();
		if (null != pack && null != pack.getAnnotation() && null != pack.getAnnotation().getSee())
		{
			generateJavadocDirectives(TEMPLATE__SEE, pack.getAnnotation().getSee(), _result);
		}
		if (null != set && null != set.getAnnotation() && null != set.getAnnotation().getSee())
		{
			generateJavadocDirectives(TEMPLATE__SEE, set.getAnnotation().getSee(), _result);
		}

		return _result.toString();
	}

	/**
	 * Aggregate the "see" elements
	 * 
	 * @param property the property for which "see" elements are generated.
	 * @return the javadoc directives, or empty.
	 */
	public static String generateSee(Property property)
	{
		StringBuilder _result = new StringBuilder();
		if (null != property && null != property.getAnnotation() && null != property.getAnnotation().getSee())
		{
			generateJavadocDirectives(TEMPLATE__SEE__INDENTED, property.getAnnotation().getSee(), _result);
		}

		return _result.toString();
	}
}
