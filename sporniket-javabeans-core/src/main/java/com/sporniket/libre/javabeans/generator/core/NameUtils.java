package com.sporniket.libre.javabeans.generator.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utilities for manipulating names.
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
 * @since 13.01.01
 * 
 */
class NameUtils
{
	/**
	 * 
	 * @param name
	 *            list of Capitalized strings.
	 * @return
	 */
	public static String getNameAsClassName(List<String> name)
	{
		StringBuilder _accumulator = new StringBuilder();
		for (String _element : name)
		{
			_accumulator.append(_element);
		}
		return _accumulator.toString();
	}

	/**
	 * Transform a name to a suitable constant name.
	 * 
	 * @param name
	 * @return
	 */
	public static String getNameAsConstantName(List<String> name)
	{
		StringBuilder _accumulator = new StringBuilder();
		for (String _element : name)
		{
			if (0 == _accumulator.length())
			{
				_accumulator.append(_element.toUpperCase());
			}
			else
			{
				_accumulator.append("_").append(_element.toUpperCase());
			}
		}
		return _accumulator.toString();
	}

	public static String getNameAsPropertyName(List<String> name)
	{
		StringBuilder _accumulator = new StringBuilder();
		for (String _element : name)
		{
			if (0 == _accumulator.length())
			{
				// force no capitals on first element.
				_accumulator.append(_element.toLowerCase());
			}
			else
			{
				_accumulator.append(_element);
			}
		}
		return _accumulator.toString();
	}

	/**
	 * Tokenize a typical javabean property name or class name..
	 * 
	 * @param name
	 * @return a list of Components starting by a capitalized letter (if name is a property name, the capitalization is forced).
	 */
	public static List<String> tokenizeOnUppercase(String name)
	{
		List<String> _tempResult = new LinkedList<String>();

		StringBuilder _accumulator = new StringBuilder();

		for (Character _nextChar : name.toCharArray())
		{
			if (0 == _accumulator.length())
			{
				// catches the first char case, ensure capitalization
				_accumulator.append(Character.toUpperCase(_nextChar));
			}
			else
			{
				if (Character.isUpperCase(_nextChar))
				{
					_tempResult.add(_accumulator.toString());
					_accumulator = new StringBuilder();
				}
				_accumulator.append(_nextChar);
			}
		}
		// don't forget to flush the accumulator...
		_tempResult.add(_accumulator.toString());

		List<String> _result = new ArrayList<String>(_tempResult.size());
		_result.addAll(_tempResult);
		return _result;
	}
}
