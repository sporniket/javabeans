package com.sporniket.libre.javabeans.doclet;

import java.text.MessageFormat;

/**
 * Command line arguments supported by the javabeans doclets.
 *
 * <p>
 * &copy; Copyright 2012-2019 David Sporn
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
 * @version 19.04.00
 * @since 17.09.00
 */
public class DocletOptions
{
	private static final String FORMAT_TO_STRING = "beanFieldPrefix={0}\nbuilderSuffix={1}\nd={2}\npojoSuffix={3}";

	/**
	 * Prefix used for the fields of the javabeans.
	 */
	String beanFieldPrefix = "my";

	/**
	 * Suffix of a generated Builder for a Javabean.
	 */
	String builderSuffix = "_Builder";

	/**
	 * Store the <code>-d</code> option value (target directory).
	 */
	String d;

	/**
	 * Suffix of a Pojo that should be expanded into a Javabean.
	 */
	String pojoSuffix = "Raw";

	public String getBeanFieldPrefix()
	{
		return beanFieldPrefix;
	}

	public String getBuilderSuffix()
	{
		return builderSuffix;
	}

	public String getD()
	{
		return d;
	}

	public String getPojoSuffix()
	{
		return pojoSuffix;
	}

	@Override
	public String toString()
	{
		return MessageFormat.format(FORMAT_TO_STRING, getBeanFieldPrefix(), getBuilderSuffix(), getD(), getPojoSuffix());
	}

}