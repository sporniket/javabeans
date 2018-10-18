package com.sporniket.libre.javabeans.doclet.basic;

import com.sporniket.libre.javabeans.doclet.BuilderGenerator;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;

/**
 * Basic builder generator.
 *
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
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
 * @version 18.10.01
 * @since 17.09.00
 */
public class BasicBuilderGenerator extends BasicGenerator implements BuilderGenerator
{

	@Override
	public void outputClassBegin()
	{
		getClassSpecs().getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnBuilder)//
				.forEach(a -> getOut().printf("@%s\n", a.getType()));
		getOut().printf("public class %s%s%s {\n", //
				getClassSpecs().getClassName(), //
				getOptions().getBuilderSuffix(), //
				getClassSpecs().getDeclaredTypeArguments()//
		);
	}

	@Override
	public void outputConstructors()
	{
		String _constructorName = getClassSpecs().getClassName() + getOptions().getBuilderSuffix();

		// default constructor.
		getOut().printf("    /**Default constructor. \n     */\n    public %s() {bean = new %s%s() ;}\n\n", //
				_constructorName, //
				getClassSpecs().getClassName(), //
				getClassSpecs().getInvokedTypeArguments() //
		);

		// constructor that delegates the bean instanciation.
		getOut().printf(
				"    /**Constructor that delegates the bean instanciation. \n     * @param newBean the instanciated bean to use.\n     */\n    public %s(%s%s newBean) {bean = newBean ;}\n\n", //
				_constructorName, //
				getClassSpecs().getClassName(), //
				getClassSpecs().getInvokedTypeArguments() //
		);

	}

	@Override
	public void outputFields()
	{
		// bean instance
		getOut().printf("    private final %s%s bean ;\n\n", //
				getClassSpecs().getClassName(), //
				getClassSpecs().getInvokedTypeArguments()//
		);

		// bean getter
		getOut().printf("    public %s%s done() {return bean ;}\n\n", //
				getClassSpecs().getClassName(), //
				getClassSpecs().getInvokedTypeArguments()//
		);
	}

	@Override
	public void outputImportStatements()
	{
		getClassSpecs().getImports().stream().forEach(i -> outputImportSpecIfValid(i));

		getOut().println();
	}

	private void outputSetter(final FieldSpecs field)
	{
		// setter
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnBuilder)//
				.filter(AnnotationSpecs::isOnSetter)//
				.forEach(a -> getOut().printf("    @%s\n", a.getType()));
		getOut().printf("    public %s%s%s with%s(%s value) {bean.set%s(value); return this;}\n", //
				getClassSpecs().getClassName(), //
				getOptions().getBuilderSuffix(), //
				getClassSpecs().getInvokedTypeArguments(), //
				field.getNameForAccessor(), //
				field.getTypeInvocation(), //
				field.getNameForAccessor()//
		);
	}

	@Override
	public void outputSetters()
	{
		getClassSpecs().getFields().stream().forEach(f -> outputSetter(f));
	}
}
