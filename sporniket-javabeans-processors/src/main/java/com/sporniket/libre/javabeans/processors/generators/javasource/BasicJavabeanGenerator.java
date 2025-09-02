package com.sporniket.libre.javabeans.processors.generators.javasource;

import static com.sporniket.libre.javabeans.models.javacode.Comparators.IMPORT_SPECS_COMPARATOR_NATURAL;
import static com.sporniket.libre.javabeans.processors.generators.javasource.Utils.NEXT_INDENTATION;
import static com.sporniket.strings.StringPredicates.IS_EMPTY;
import static java.lang.String.join;
import static java.util.Arrays.asList;

import java.io.PrintStream;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;

import com.sporniket.libre.javabeans.models.javacode.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.javacode.FieldSpecs;
import com.sporniket.libre.javabeans.models.javacode.ImportSpecs;

/**
 * Basic generator of javabeans from pojos.
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
 * @version 23.07.00
 * @since 17.09.00
 */
public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	private static final String CHAR_NEWLINE = "\n";

	private static final String MARKER_JAVADOC_BODY = " * ";

	private static final String MARKER_JAVADOC_HEADER = "/**\n";

	private static final String MARKER_JAVADOC_FOOTER = " */\n";

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

	private void outputJavadocForGetter(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		List.of("", "@returns the current value").forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
		out.flush();
	}

	private void outputJavadocForSetter(final String[] javadocLines, final String indentation, final PrintStream out)
	{
		final Consumer<String> printJavadocBodyLine = createJavadocBodyLinePrinter(indentation, out);
		out.print(indentation);
		out.print(MARKER_JAVADOC_HEADER);
		asList(javadocLines).forEach(printJavadocBodyLine);
		List.of("", "@param value the new value").forEach(printJavadocBodyLine);
		out.print(indentation);
		out.print(MARKER_JAVADOC_FOOTER);
		out.flush();
	}

	private void outputAccessor(final FieldSpecs field, final PrintStream out)
	{
		final String[] _javadocLines = field.getJavadocLines();
		final boolean _hasJavadoc = null != _javadocLines && 0 < _javadocLines.length;
		// getter
		if (_hasJavadoc)
		{
			outputJavadocForGetter(_javadocLines, NEXT_INDENTATION, out);
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnGetter)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION, out));
		List.of( //
				NEXT_INDENTATION, //
				"public ", field.getTypeInvocation(), field.getArrayMarker(), //
				" ", (field.isBooleanGetter()) ? "is" : "get", field.getNameForAccessor(), //
				"() {return ", field.getFieldPrefix(), field.getNameForField(), //
				" ;}\n" //
		).forEach(out::print);

		// setter
		if (_hasJavadoc)
		{
			outputJavadocForSetter(_javadocLines, NEXT_INDENTATION, out);
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnSetter)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION, out));
		List.of( //
				NEXT_INDENTATION, //
				"public void set", field.getNameForAccessor(), //
				"(", field.getTypeInvocation(), field.getArrayMarker(), //
				" value) {", field.getFieldPrefix(), field.getNameForField(), //
				" = value;}\n" //
		).forEach(out::print);

		out.println();
	}

	@Override
	public void outputAccessors(final PrintStream out)
	{
		getClassSpecs().getFields().stream().filter(FieldSpecs::isDirectlyRequired).forEach(f -> outputAccessor(f, out));
	}

	@Override
	public void outputClassBegin(final PrintStream out)
	{
		// last preparations
		final String _abstractMarker = getClassSpecs().isAbstractRequired() ? " abstract" : "";
		final String _extendsMarker = IS_EMPTY.test(getClassSpecs().getSuperClassName()) ? "" : "\n        extends ";
		final String _implementsMarker = IS_EMPTY.test(getClassSpecs().getInterfaceList()) ? "" : "\n      implements ";

		final String[] _javadocLines = getClassSpecs().getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			out.printf("/**%s\n*/\n", join("\n", _javadocLines));
		}
		final Consumer<? super AnnotationSpecs> _outputAnnotation = a -> outputAnnotation(a, "", out);
		getClassSpecs().getAnnotations().stream()//
				.forEach(_outputAnnotation);
		out.printf("public%s class %s%s %s%s%s%s\n{\n\n", //
				_abstractMarker, getClassSpecs().getClassName(), getClassSpecs().getDeclaredTypeArguments()//
				, _extendsMarker, getClassSpecs().getSuperClassName()//
				, _implementsMarker, getClassSpecs().getInterfaceList());
	}

	private void outputField(final FieldSpecs field, final PrintStream out)
	{
		final String[] _javadocLines = field.getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			out.printf("/**%s\n*/\n", join("\n", _javadocLines));
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnField)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION, out));
		out.printf("    private %s%s %s%s ;\n\n", field.getTypeInvocation(), field.getArrayMarker(),
				getOptions().getBeanFieldPrefix(), field.getNameForField());
	}

	@Override
	public void outputFields(final PrintStream out)
	{
		getClassSpecs().getFields().stream()//
				.filter(FieldSpecs::isDirectlyRequired)//
				.forEach(_field -> outputField(_field, out));

		out.println();
	}

	@Override
	public void outputImportStatements(final PrintStream out)
	{
		final TreeSet<ImportSpecs> _sortedImports = new TreeSet<ImportSpecs>(IMPORT_SPECS_COMPARATOR_NATURAL);
		_sortedImports.addAll(getClassSpecs().getImports());
		_sortedImports.stream().filter(ImportSpecs::isDirectlyRequired).forEach(i -> outputImportSpecIfValid(i, out));

		out.println();
	}

}
