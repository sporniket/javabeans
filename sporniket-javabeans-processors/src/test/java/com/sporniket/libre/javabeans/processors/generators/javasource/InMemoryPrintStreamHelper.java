package com.sporniket.libre.javabeans.processors.generators.javasource;

import static java.util.stream.Collectors.toList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
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
class InMemoryPrintStreamHelper
{
	private final ByteArrayOutputStream myByteArrayOutputStream = new ByteArrayOutputStream();

	private final Charset myCharset = StandardCharsets.UTF_8;

	private final PrintStream myPrintStream;

	public InMemoryPrintStreamHelper()
	{
		try
		{
			myPrintStream = new PrintStream(myByteArrayOutputStream, true, myCharset.name());
		}
		catch (final UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<String> getLines()
	{
		return Stream.of(getValue().split("\n")) //
				.map(String::stripTrailing).collect(toList());
	}

	public PrintStream getPrintStream()
	{
		return myPrintStream;
	}

	private String getValue()
	{
		return new String(myByteArrayOutputStream.toByteArray(), myCharset);
	}

}