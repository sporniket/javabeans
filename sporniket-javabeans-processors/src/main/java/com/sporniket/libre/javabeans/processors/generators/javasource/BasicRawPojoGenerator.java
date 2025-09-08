package com.sporniket.libre.javabeans.processors.generators.javasource;

import static com.sporniket.libre.javabeans.models.javacode.Comparators.IMPORT_SPECS_COMPARATOR_NATURAL;
import static com.sporniket.libre.javabeans.processors.generators.javasource.Utils.NEXT_INDENTATION;
import static com.sporniket.libre.javabeans.processors.generators.javasource.UtilsJavadoc.printJavadoc;
import static com.sporniket.strings.StringPredicates.IS_NOT_EMPTY;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.sporniket.libre.javabeans.models.javacode.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.javacode.FieldSpecs;
import com.sporniket.libre.javabeans.models.javacode.ImportSpecs;
import com.sporniket.libre.lang.string.StringTools;

/**
 * Basic generator for pojos from javabeans.
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
public class BasicRawPojoGenerator extends BasicGenerator implements JavabeanGenerator
{

	@Override
	public void outputAccessors(final PrintStream out)
	{
		// nothing to do
	}

	@Override
	public void outputClassBegin(final PrintStream out)
	{
		final String _classMarker = getClassSpecs().isAbstractRequired() ? "abstract class" : "class";
		final String _extendsMarker = StringTools.isEmptyString(getClassSpecs().getSuperClassName()) ? "" : "\n        extends ";
		final String _implementsMarker = StringTools.isEmptyString(getClassSpecs().getInterfaceList()) ? "" : "\n      implements ";

		final String[] _javadocLines = getClassSpecs().getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			printJavadoc(_javadocLines, "", out);
		}
		getClassSpecs().getAnnotations().stream()//
				.forEach(a -> outputAnnotation(a, "", out));
		final List<String> _classOpening = new ArrayList<>(20);
		_classOpening.add((getClassSpecs().isAbstractRequired()) ? "abstract class " : "class ");
		_classOpening.add(getClassSpecs().getClassName());
		if (hasTypeArguments())
		{
			_classOpening.add(getClassSpecs().getDeclaredTypeArguments());
		}
		if (IS_NOT_EMPTY.test(getClassSpecs().getSuperClassName()))
		{
			_classOpening.add("\n        extends ");
			_classOpening.add(getClassSpecs().getSuperClassName());
		}
		if (IS_NOT_EMPTY.test(getClassSpecs().getInterfaceList()))
		{
			_classOpening.add("\n        implements ");
			_classOpening.add(getClassSpecs().getInterfaceList());
		}
		_classOpening.add("\n{\n");
		_classOpening.forEach(out::print);
	}

	private void outputField(final FieldSpecs field, final PrintStream out)
	{
		final String[] _javadocLines = field.getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			printJavadoc(_javadocLines, NEXT_INDENTATION, out);
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnField)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION, out));
		List.of( //
				"    ", //
				field.getTypeInvocation(), //
				field.getArrayMarker(), //
				" ", //
				field.getFieldPrefix(), //
				field.getNameForField(), //
				" ;\n    \n" //
		).forEach(out::print);
	}

	@Override
	public void outputFields(final PrintStream out)
	{
		getClassSpecs().getFields().stream().filter(FieldSpecs::isDirectlyRequired).forEach(f -> outputField(f, out));
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
