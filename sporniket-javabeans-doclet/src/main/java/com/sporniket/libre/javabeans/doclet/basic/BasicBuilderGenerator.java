package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getAccessibleFields;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.BuilderGenerator;
import com.sporniket.libre.javabeans.doclet.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.UtilsClassname;
import com.sporniket.libre.javabeans.doclet.UtilsFieldname;
import com.sun.javadoc.FieldDoc;

/**
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
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
 * @version 17.09.01
 * @since 17.09.01
 */
public class BasicBuilderGenerator extends BasicGenerator implements BuilderGenerator
{

	@Override
	public void outputClassBegin()
	{
		StringBuilder _builderDecl = new StringBuilder("public class ");
		_builderDecl.append(UtilsClassname.computeOutputClassname(getSource().qualifiedName(), getTranslations(), getShortables())) ;
		_builderDecl.append(getOptions().getBuilderSuffix());
		outputClassParameters__classDeclaration(_builderDecl, getSource().typeParameters(), getTranslations(), getShortables());
		
		_builderDecl.append("{");
		
		getOut().println(_builderDecl.toString());
	}

	@Override
	public void outputFields()
	{
		// bean instance
		final StringBuilder _pojoDecl = new StringBuilder("    private final ");
		outputClassName__beanType(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append(" bean = new ");
		outputClassName__beanInstanciation(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append("() ;\n\n");

		// bean getter
		_pojoDecl.append("    public ");
		outputClassName__beanType(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append(" done() {return bean ;}\n");

		// done
		getOut().println(_pojoDecl.toString());
	}

	private void outputSetter(final FieldDoc field, PrintStream out, final Map<String, String> translations,
			final Set<String> shortables)
	{
		final String _baseName = UtilsClassname.computeOutputClassname(getSource().qualifiedName(), getTranslations(), getShortables()) ;
		final StringBuilder _builderTypeArguments = new StringBuilder() ;
		UtilsClassDoc.TypeInvocation.outputTypeArguments(_builderTypeArguments, getSource().typeParameters(), translations, shortables);
		final String _accessorSuffix = UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), translations, shortables);

		// setter
		out.printf("    public %s%s%s with%s(%s value) {bean.set%s(value); return this;}\n", _baseName, getOptions().getBuilderSuffix(), _builderTypeArguments.toString(), _accessorSuffix, _type,
				_accessorSuffix);

		out.println();
	}

	@Override
	public void outputSetters()
	{
		getAccessibleFields(getSource()).forEach(f -> outputSetter(f, getOut(), getTranslations(), getShortables()));
	}

}
