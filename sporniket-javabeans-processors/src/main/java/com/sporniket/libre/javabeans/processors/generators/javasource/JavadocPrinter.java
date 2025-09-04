package com.sporniket.libre.javabeans.processors.generators.javasource;

import static java.util.Arrays.asList;

import java.io.PrintStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * Print javadoc lines with required enclosing marks, and as pretty as possible.
 *
 * <p>
 * &copy; Copyright 2012-2023 David Sporn
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
public class JavadocPrinter
{
	private static final String CHAR_NEWLINE = "\n";

	private static final String MARKER_JAVADOC_BODY = " * ";

	private static final String MARKER_JAVADOC_FOOTER = " */\n";

	private static final String MARKER_JAVADOC_HEADER = "/**\n";

	private Consumer<String> createJavadocBodyLinePrinter(final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = s -> {
			out.print(indentation);
			out.print(MARKER_JAVADOC_BODY);
			out.print(s);
			out.print(CHAR_NEWLINE);
		};
		return printJavadocBodyLine;
	}

	public void printJavadoc(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
	}

	public void printJavadocForBuilderSetter(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		List.of("", "@param value the new value", "", "@returns the builder").forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
	}

	public void printJavadocForGetter(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		List.of("", "@returns the current value").forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
	}

	public void printJavadocForSetter(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		List.of("", "@param value the new value").forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
	}

}
