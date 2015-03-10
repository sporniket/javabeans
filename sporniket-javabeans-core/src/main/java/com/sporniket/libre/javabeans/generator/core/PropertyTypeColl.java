/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import com.sporniket.libre.lang.string.StringTools;

/**
 * Extended information for a property type starting with "coll:..."
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
public class PropertyTypeColl extends PropertyType
{
	private String myImplementation;

	private String myInstanciationParameters;

	private String myInterface;

	private String myMode;

	private String myParametrization;


	/**
	 * @return the implementation
	 */
	public String getImplementation()
	{
		return myImplementation;
	}

	/**
	 * @return the instanciationParameters
	 */
	public String getInstanciationParameters()
	{
		return myInstanciationParameters;
	}

	/**
	 * @return the interface
	 */
	public String getInterface()
	{
		return myInterface;
	}

	/**
	 * @return the mode
	 */
	public String getMode()
	{
		return myMode;
	}

	/**
	 * @return the parametrization
	 */
	public String getParametrization()
	{
		return myParametrization;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.javabeans.generator.core.PropertyType#setDescription(java.lang.String)
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
		String[] _tokens = description.split(MARKER__SEPARATOR);
		if (4 != _tokens.length && 5 != _tokens.length)
		{
			throw new IllegalArgumentException(
					"description is surely incorrect for enum, should be 'coll:Interface:ImplementationType:ItemType:(notNull|isolated):Instanciation parameters', got : '"
							+ description + "'.");
		}

		setInterface(_tokens[0]);
		setImplementation(_tokens[1]);
		setParametrization(_tokens[2]);
		setMode(_tokens[3]);
		setInstanciationParameters((4 == _tokens.length) ? "" : _tokens[4]);
	}

	/**
	 * @param implementation
	 *            the implementation to set
	 */
	public void setImplementation(String implementation)
	{
		myImplementation = implementation;
	}

	/**
	 * @param instanciationParameters
	 *            the instanciationParameters to set
	 */
	public void setInstanciationParameters(String instanciationParameters)
	{
		myInstanciationParameters = instanciationParameters;
	}

	/**
	 * @param interface1
	 *            the interface to set
	 */
	public void setInterface(String interface1)
	{
		myInterface = interface1;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode)
	{
		myMode = mode;
	}

	/**
	 * @param parametrization
	 *            the parametrization to set
	 */
	public void setParametrization(String parametrization)
	{
		myParametrization = parametrization;
	}

	/**
	 * 
	 */
	public PropertyTypeColl()
	{
		setPrefix(Prefix.COLL);
	}

}
