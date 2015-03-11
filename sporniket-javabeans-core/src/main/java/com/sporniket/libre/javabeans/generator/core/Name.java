/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.util.List;

/**
 * Stores the few variant of the names required to generate code.
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
 * 
 */
class Name
{
	/**
	 * Instanciate a {@link Name} from a javabean style property name.
	 * 
	 * @param propertyName
	 * @return
	 */
	public static Name createFromJavabeanPropertyName(String propertyName)
	{
		Name _result = new Name();
		List<String> _components = NameUtils.tokenizeOnUppercase(propertyName);
		_result.setUncapitalized(propertyName);
		_result.setCapitalized(NameUtils.getNameAsClassName(_components));
		_result.setAllCaps(NameUtils.getNameAsConstantName(_components));
		return _result;
	}

	/**
	 * Name with all letter capitalized and "_" separator.
	 */
	private String myAllCaps;

	/**
	 * Nname with the first letter capitalized to generate my/get/set{myCapitalizedName}.
	 */
	private String myCapitalized;

	/**
	 * Name following javabean naming convention for a property.
	 */
	private String myUncapitalized;

	/**
	 * @return the allCaps
	 */
	public String getAllCaps()
	{
		return myAllCaps;
	}

	/**
	 * @return the capitalized
	 */
	public String getCapitalized()
	{
		return myCapitalized;
	}

	/**
	 * @return the uncapitalized
	 */
	public String getUncapitalized()
	{
		return myUncapitalized;
	}

	/**
	 * @param allCaps
	 *            the allCaps to set
	 */
	public void setAllCaps(String allCaps)
	{
		myAllCaps = allCaps;
	}

	/**
	 * @param capitalized
	 *            the capitalized to set
	 */
	public void setCapitalized(String capitalized)
	{
		myCapitalized = capitalized;
	}

	/**
	 * @param uncapitalized
	 *            the uncapitalized to set
	 */
	public void setUncapitalized(String uncapitalized)
	{
		myUncapitalized = uncapitalized;
	}

}
