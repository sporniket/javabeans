package com.sporniket.libre.javabeans.doclet;

import com.sporniket.libre.lang.string.StringTools;

/**
 * Utility class for Field names.
 *
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 19.02.00
 * @since 17.09.00
 */
public final class UtilsFieldname
{
	/**
	 * Capitalize the first letter of the field name.
	 *
	 * @param fieldName
	 *            the field name to transform.
	 * @return the transformed field name, e.g. <code>fooBar</code> will give <code>FooBar</code>.
	 */
	public static String computeFieldAccessorSuffix(String fieldName)
	{
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * Remove the prefix and uncapitalize the field name if applicable.
	 *
	 * @param fieldName
	 *            the field name to transform.
	 * @param prefix
	 *            the prefix to remove.
	 * @return the transformed field name, e.g. <code>myFooBar</code> without <code>my</code> will give <code>fooBar</code>.
	 * @since 17.12.00
	 */
	public static String removePrefix(String fieldName, String prefix)
	{
		if (StringTools.isEmptyString(prefix) || (fieldName.length() <= prefix.length()) || !fieldName.startsWith(prefix))
		{
			return fieldName;
		}
		return fieldName.substring(prefix.length(), prefix.length() + 1).toLowerCase() + fieldName.substring(prefix.length() + 1);
	}

}
