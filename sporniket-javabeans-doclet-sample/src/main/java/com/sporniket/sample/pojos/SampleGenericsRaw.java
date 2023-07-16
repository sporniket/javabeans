package com.sporniket.sample.pojos;

import java.util.Map;

/**
 * Sample generic pojo extending {@link SampleBasicRaw}.
 * 
 * <p>
 * &copy; Copyright 2012-2023 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
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
 * @version 19.03.00
 * @since 17.09.01
 * 
 * @param <T>
 *            whatever.
 * @param <R>
 *            MUST extends {@link Number}.
 * 
 */
class SampleGenericsRaw<T, R extends Number> extends SampleBasicRaw
{
	Map<T, R> registry;

	String[] tags;

}
