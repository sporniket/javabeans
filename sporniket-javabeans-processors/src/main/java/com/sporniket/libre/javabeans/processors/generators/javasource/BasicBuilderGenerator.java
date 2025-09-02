package com.sporniket.libre.javabeans.processors.generators.javasource;

import static com.sporniket.libre.javabeans.models.javacode.Comparators.IMPORT_SPECS_COMPARATOR_NATURAL;
import static com.sporniket.libre.javabeans.processors.generators.javasource.Utils.NEXT_INDENTATION;
import static com.sporniket.libre.javabeans.processors.generators.javasource.UtilsJavadoc.printJavadocForBuilderSetter;
import static com.sporniket.strings.StringPredicates.IS_NOT_EMPTY;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.sporniket.libre.javabeans.models.javacode.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.javacode.FieldSpecs;
import com.sporniket.libre.javabeans.models.javacode.ImportSpecs;

/**
 * Basic builder generator.
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
public class BasicBuilderGenerator extends BasicGenerator implements BuilderGenerator
{

	@Override
	public void outputClassBegin(final PrintStream out)
	{
		final String[] _javadocLines = getClassSpecs().getJavadocLines();
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			UtilsJavadoc.printJavadoc(_javadocLines, "", out);
		}
		getClassSpecs().getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnBuilder)//
				.forEach(a -> outputAnnotation(a, "", out));
		final List<String> _classOpening = new ArrayList<>(20);
		_classOpening.add("public class ");
		_classOpening.add(getClassSpecs().getClassName());
		_classOpening.add(getOptions().getBuilderSuffix());
		if (IS_NOT_EMPTY.test(getClassSpecs().getDeclaredTypeArguments()))
		{
			_classOpening.add(getClassSpecs().getDeclaredTypeArguments());
		}
		_classOpening.add("\n{\n");
		_classOpening.forEach(out::print);
		out.flush();
	}

	@Override
	public void outputConstructors(final PrintStream out)
	{
		final String _constructorName = getClassSpecs().getClassName() + getOptions().getBuilderSuffix();

		if (!getClassSpecs().isAbstractRequired())
		{
			// default constructor.
			out.printf("    /**\n     * Default constructor.\n     */\n    public %s() {bean = new %s%s() ;}\n\n", //
					_constructorName, //
					getClassSpecs().getClassName(), //
					getClassSpecs().getInvokedTypeArguments() //
			);
		}

		// constructor that delegates the bean instanciation.
		out.printf(
				"    /**\n     * Constructor that delegates the bean instanciation.\n     * @param newBean the instanciated bean to use.\n     */\n    public %s(%s%s newBean) {bean = newBean ;}\n\n", //
				_constructorName, //
				getClassSpecs().getClassName(), //
				getClassSpecs().getInvokedTypeArguments() //
		);

	}

	@Override
	public void outputFields(final PrintStream out)
	{
		if (IS_NOT_EMPTY.test(getClassSpecs().getInvokedTypeArguments()))
		{
			List.of( //
					"    private final ", //
					getClassSpecs().getClassName(), //
					getClassSpecs().getInvokedTypeArguments(), //
					" bean ;\n\n    public ", //
					getClassSpecs().getClassName(), //
					getClassSpecs().getInvokedTypeArguments(), //
					" done() {return bean ;}\n\n" //
			).forEach(out::print);
		}
		else
		{
			List.of( //
					"    private final ", //
					getClassSpecs().getClassName(), //
					" bean ;\n\n    public ", //
					getClassSpecs().getClassName(), //
					" done() {return bean ;}\n\n" //
			).forEach(out::print);

		}

		out.flush();
	}

	@Override
	public void outputImportStatements(final PrintStream out)
	{
		final TreeSet<ImportSpecs> _sortedImports = new TreeSet<ImportSpecs>(IMPORT_SPECS_COMPARATOR_NATURAL);
		_sortedImports.addAll(getClassSpecs().getImports());
		_sortedImports.stream().forEach(i -> outputImportSpecIfValid(i, out));

		out.println();
	}

	private void outputSetter(final FieldSpecs field, final PrintStream out)
	{
		final String[] _javadocLines = field.getJavadocLines();
		// setter
		if (null != _javadocLines && 0 < _javadocLines.length)
		{
			printJavadocForBuilderSetter(_javadocLines, NEXT_INDENTATION, out);
		}
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnBuilder)//
				.filter(AnnotationSpecs::isOnSetter)//
				.forEach(a -> out.printf("    @%s\n", a.getType()));
		out.printf("    public %s%s%s with%s(%s%s value) {bean.set%s(value); return this;}\n", //
				getClassSpecs().getClassName(), //
				getOptions().getBuilderSuffix(), //
				getClassSpecs().getInvokedTypeArguments(), //
				field.getNameForAccessor(), //
				field.getTypeInvocation(), //
				field.getArrayMarker(), //
				field.getNameForAccessor()//
		);
	}

	@Override
	public void outputSetters(final PrintStream out)
	{
		getClassSpecs().getFields().stream().forEach(f -> outputSetter(f, out));
	}
}
