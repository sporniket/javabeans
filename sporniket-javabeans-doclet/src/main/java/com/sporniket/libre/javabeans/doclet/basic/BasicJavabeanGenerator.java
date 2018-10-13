package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.basic.Utils.NEXT_INDENTATION;

import java.util.function.Consumer;

import com.sporniket.libre.javabeans.doclet.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.codespecs.AnnotationSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sporniket.libre.lang.string.StringTools;

/**
 * Basic generator of javabeans from pojos.
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
 * @version 18.10.00
 * @since 17.09.00
 */
public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	private void outputAccessor(FieldSpecs field)
	{
		// getter
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnGetter)//
				.forEach(a -> getOut().printf("    @%s\n", a.getType()));
		getOut().printf("    public %s %s%s() {return %s%s ;}\n", field.getTypeInvocation(),
				(field.isBooleanGetter()) ? "is" : "get", field.getNameForAccessor(), field.getFieldPrefix(),
				field.getNameForField());

		// setter
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnSetter)//
				.forEach(a -> getOut().printf("    @%s\n", a.getType()));
		getOut().printf("    public void set%s(%s value) {%s%s = value;}\n", field.getNameForAccessor(), field.getTypeInvocation(),
				field.getFieldPrefix(), field.getNameForField());

		getOut().println();
	}

	@Override
	public void outputAccessors()
	{
		getClassSpecs().getFields().stream().filter(f -> f.isDirectlyRequired()).forEach(f -> outputAccessor(f));
	}

	@Override
	public void outputClassBegin()
	{
		// last preparations
		final String _abstractMarker = getClassSpecs().isAbstractRequired() ? " abstract" : "";
		final String _extendsMarker = StringTools.isEmptyString(getClassSpecs().getSuperClassName()) ? "" : "\n        extends ";
		final String _implementsMarker = StringTools.isEmptyString(getClassSpecs().getInterfaceList()) ? "" : "\n      implements ";

		final Consumer<? super AnnotationSpecs> _outputAnnotation = a -> outputAnnotation(a, "");
		getClassSpecs().getAnnotations().stream()//
				.forEach(_outputAnnotation);
		getOut().printf("public%s class %s%s %s%s%s%s\n{\n\n", //
				_abstractMarker, getClassSpecs().getClassName(), getClassSpecs().getDeclaredTypeArguments()//
				, _extendsMarker, getClassSpecs().getSuperClassName()//
				, _implementsMarker, getClassSpecs().getInterfaceList());
	}

	private void outputField(FieldSpecs field)
	{
		field.getAnnotations().stream()//
				.filter(AnnotationSpecs::isOnField)//
				.forEach(a -> outputAnnotation(a, NEXT_INDENTATION));
		getOut().printf("    private %s %s%s ;\n\n", field.getTypeInvocation(), getOptions().getBeanFieldPrefix(),
				field.getNameForField());
	}

	@Override
	public void outputFields()
	{
		getClassSpecs().getFields().stream()//
				.filter(FieldSpecs::isDirectlyRequired)//
				.forEach(_field -> outputField(_field));

		getOut().println();
	}

	@Override
	public void outputImportStatements()
	{
		// TODO Auto-generated method stub
		getClassSpecs().getImports().stream().filter(ImportSpecs::isDirectlyRequired).forEach(i -> outputImportSpecIfValid(i));

		getOut().println();
	}

}
