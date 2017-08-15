package com.sporniket.libre.javabeans.doclet.expander.basic;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsFieldDoc.getAccessibleDeclaredFields;

import com.sporniket.libre.javabeans.doclet.expander.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.expander.UtilsFieldname;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

public class BasicRawPojoGenerator extends BasicGenerator implements JavabeanGenerator
{

	@Override
	public void outputAccessors()
	{
		//nothing to do
	}

	@Override
	public void outputClassBegin()
	{
		final StringBuilder _classDecl = new StringBuilder("class ");
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
		final String _accessorSuffix = UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// field declaration
		getOut().printf("    %s my%s ;\n", _type, _accessorSuffix);
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
