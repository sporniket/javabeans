package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getAccessibleDeclaredFields;

import com.sporniket.libre.javabeans.doclet.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.UtilsFieldname;
import com.sporniket.libre.lang.string.StringTools;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

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
 * @version 17.09.00
 * @since 17.09.00
 */
public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	private void outputAccessor(FieldDoc field)
	{
		final boolean _noPrefix = StringTools.isEmptyString(getOptions().getBeanFieldPrefix());
		final String _fieldPrefix = _noPrefix ? "this." : getOptions().getBeanFieldPrefix();
		final String _accessorSuffix = _noPrefix ? field.name() : UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// getter
		getOut().printf("    public %s get%s() {return %s%s ;}\n", _type, _accessorSuffix, _fieldPrefix, _accessorSuffix);

		// setter
		getOut().printf("    public void set%s(%s value) {%s%s = value;}\n", _accessorSuffix, _type, _fieldPrefix, _accessorSuffix);

		getOut().println();
	}

	@Override
	public void outputAccessors()
	{
		getAccessibleDeclaredFields(getSource()).forEach(_field -> {
			outputAccessor(_field);
		});

	}

	@Override
	public void outputClassBegin()
	{
		final StringBuilder _classDecl = new StringBuilder(
				shouldBeAbstract(getSource()) ? "public abstract class " : "public class ");
		outputClassName__classDeclaration(_classDecl, getSource(), getTranslations(), getShortables());

		final String _supername = getSource().superclass().qualifiedName();
		if (!Object.class.getName().equals(_supername))
		{
			_classDecl.append(" extends ").append(computeOutputClassname(_supername, getTranslations(), getShortables()));
		}
		final ClassDoc[] _interfaces = getSource().interfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_classDecl.append((0 == _i) ? " implements " : ", ")
						.append(computeOutputClassname(_interfaces[_i].name(), getTranslations(), getShortables()));
			}
		}

		getOut().printf(_classDecl.append("\n{\n").toString());

		getOut().println();
	}

	private void outputField(FieldDoc field)
	{
		final boolean _noPrefix = StringTools.isEmptyString(getOptions().getBeanFieldPrefix());
		final String _fieldPrefix = getOptions().getBeanFieldPrefix();
		final String _accessorSuffix = _noPrefix ? field.name() : UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// field declaration
		getOut().printf("    private %s %s%s ;\n", _type, _fieldPrefix, _accessorSuffix);
	}

	@Override
	public void outputFields()
	{
		getAccessibleDeclaredFields(getSource()).forEach(_field -> {
			outputField(_field);
		});

		getOut().println();
	}

}
