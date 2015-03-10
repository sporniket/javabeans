/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.studio.schema.model.set.javabean.Property;

/**
 * Base class for processing the <code>type</code> attribute of a property.
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
public class PropertyType
{
	public static enum Prefix
	{
		COLL("coll"),
		ENUM("enum"),
		JAVA("java"),
		MAP("map");
		public static Prefix parseString(String value)
		{
			return Prefix.valueOf(value.toUpperCase());
		}

		private String myName;

		/**
		 * @param name
		 */
		private Prefix(String name)
		{
			myName = name;
		}

		/**
		 * @return the name
		 */
		public String getName()
		{
			return myName;
		}
	}
	public static class Utils
	{
		/**
		 * @param type
		 * @param target
		 */
		private static void doExtractBasicInfo(String type, PropertyType target)
		{
			int _markerPos = type.indexOf(MARKER__SEPARATOR);
			if (-1 == _markerPos)
			{
				throw new IllegalArgumentException("type is surely incorrect, should be '(java|enum|coll):...', got : '" + type
						+ "'.");
			}

			String _prefix = type.substring(0, _markerPos);
			String _description = type.substring(_markerPos + 1);
			target.setPrefix(Prefix.parseString(_prefix));
			target.setDescription(_description);
		}

		/**
		 * Find the prefix and the description from the type.
		 * 
		 * @param type
		 * @param target
		 */
		public static void extractBasicInfo(String type, PropertyType target)
		{
			// sanity check
			if (null == target)
			{
				throw new IllegalArgumentException("target must be not null");
			}
			if (StringTools.isEmptyString(type))
			{
				String message = (null == type) ? "type must be not null" : "type must be not empty";
				throw new IllegalArgumentException(message);
			}

			// ok
			doExtractBasicInfo(type, target);
		}

		/**
		 * Build a specific PropertyType from the value of the type attribute.
		 * 
		 * @param type
		 *            the value of the type attribute
		 * @return
		 * @see Property#getType()
		 */
		public static PropertyType instanciate(String type)
		{
			PropertyType _result = null;
			PropertyType _temp = new PropertyType();
			extractBasicInfo(type, _temp);
			switch (_temp.getPrefix())
			{
				case JAVA:
					_result = _temp;
					break;
				case ENUM:
				{
					PropertyTypeEnum _enum = new PropertyTypeEnum();
					_enum.setDescription(_temp.getDescription());
					_result = _enum;
				}
					break;
				case COLL:
				{
					PropertyTypeColl _coll = new PropertyTypeColl();
					_coll.setDescription(_temp.getDescription());
					_result = _coll;
				}
					break;
				case MAP:
				{
					PropertyTypeMap _coll = new PropertyTypeMap();
					_coll.setDescription(_temp.getDescription());
					_result = _coll;
				}
					break;
				// case COLL:
				// break;
				default:
					throw new IllegalStateException("Unsupported prefix:" + _temp.getPrefix());
			}
			return _result;
		}
	}

	public static final String MARKER__SEPARATOR = ":";

	/**
	 * Part "after" the prefix.
	 */
	private String myDescription;

	private Prefix myPrefix;

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return myDescription;
	}

	/**
	 * @return the prefix
	 */
	public Prefix getPrefix()
	{
		return myPrefix;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		myDescription = description;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(Prefix prefix)
	{
		myPrefix = prefix;
	}
}
