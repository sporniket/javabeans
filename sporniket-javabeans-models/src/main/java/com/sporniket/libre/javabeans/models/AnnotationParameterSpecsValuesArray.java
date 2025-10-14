package com.sporniket.libre.javabeans.models;

import java.util.List;

/** GENERATED CODE !
 * <p>
 * &copy; Copyright 2012-2025 David Sporn
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
 * @version 25.11.00
 * @since 25.11.00
 */
public class AnnotationParameterSpecsValuesArray
        extends AnnotationParameterSpecs
{

    private List<AnnotationParameterSpecsSingleValue> myValues ;

	/**
	 * Prefix added before the list : <code>prefix{...}postfix</code>.
	 *
	 * Added to accomodate parameters that are annotations with parameter themselves. For this specific case, the prefix would be
	 * something like <code>@the_annotation(</code>.
	 *
	 */
	private String myPrefix;

	/**
	 * Suffix added after the list : <code>prefix{...}postfix</code>.
	 *
	 * Added to accomodate parameters that are annotations with parameter themselves. For this specific case, the prefix would be
	 * something like <code>)</code>.
	 *
	 */
	private String myPostfix;


    public List<AnnotationParameterSpecsSingleValue> getValues() {return myValues ;}
    public void setValues(final List<AnnotationParameterSpecsSingleValue> value) {myValues = value;}

	public String getPrefix()
	{
		return myPrefix;
	}

	public void setPrefix(final String prefix)
	{
		myPrefix = prefix;
	}

	public String getPostfix()
	{
		return myPostfix;
	}

	public void setPostfix(final String postfix)
	{
		myPostfix = postfix;
	}

}

