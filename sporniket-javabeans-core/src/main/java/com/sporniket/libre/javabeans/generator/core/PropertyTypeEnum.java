/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import com.sporniket.libre.lang.string.StringTools;

/**
 * Extended information for a property type starting with "enum:..."
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
public class PropertyTypeEnum extends PropertyType
{
	/**
	 * 
	 */
	public PropertyTypeEnum()
	{
		setPrefix(Prefix.ENUM);
	}

	/**
	 * The name of the java enum, if provided.
	 */
	private String myEnumJavaName;

	/**
	 * Comma separated values of the enum.
	 */
	private String myEnumValues;

	/**
	 * @return the enumJavaName
	 */
	public String getEnumJavaName()
	{
		return myEnumJavaName;
	}

	/**
	 * @return the enumValues
	 */
	public String getEnumValues()
	{
		return myEnumValues;
	}

	/**
	 * Predicate telling whether the enum has a specific name.
	 * 
	 * @return
	 */
	public boolean isAnonymous()
	{
		return StringTools.isEmptyString(getEnumJavaName());
	}

	/**
	 * @param enumJavaName
	 *            the enumJavaName to set
	 */
	public void setEnumJavaName(String enumJavaName)
	{
		myEnumJavaName = enumJavaName;
	}

	/**
	 * @param enumValues
	 *            the enumValues to set
	 */
	public void setEnumValues(String enumValues)
	{
		myEnumValues = enumValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.javabeans.generator.PropertyType#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description)
	{
		super.setDescription(description);

		// extract other infos
		// sanity check
		if (StringTools.isEmptyString(description))
		{
			String message = (null == description) ? "description must be not null" : "description must be not empty";
			throw new IllegalArgumentException(message);
		}

		// ok
		setDescription__extractInformations(description);
	}

	/**
	 * @param description
	 */
	private void setDescription__extractInformations(String description)
	{
		int _markerPos = description.indexOf(MARKER__SEPARATOR);
		if (-1 == _markerPos)
		{
			throw new IllegalArgumentException(
					"description is surely incorrect for enum, should be '(name)?:(comma separated values)', got : '" + description
							+ "'.");
		}

		String _enumJavaName = description.substring(0, _markerPos);
		String _enumValues = description.substring(_markerPos + 1);
		setEnumJavaName(_enumJavaName);
		setEnumValues(_enumValues);
	}

}
