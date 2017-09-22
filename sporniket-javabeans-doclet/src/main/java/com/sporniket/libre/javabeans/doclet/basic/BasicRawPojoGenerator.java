package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getPrivateDeclaredFields;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.UtilsFieldname;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

public class BasicRawPojoGenerator extends BasicGenerator implements JavabeanGenerator
{

	@Override
	public void outputAccessors()
	{
		// nothing to do
	}

	@Override
	public void outputClassBegin()
	{
		final StringBuilder _classDecl = new StringBuilder(shouldBeAbstract(getSource()) ? "abstract class " : "class ");
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
		String _name = field.name();
		String _beanFieldPrefix = getOptions().getBeanFieldPrefix();
		if (null != _beanFieldPrefix && 0 != _beanFieldPrefix.length())
		{
			if (!_beanFieldPrefix.equals(field.name()) && field.name().startsWith(_beanFieldPrefix))
			{
				_name = field.name().substring(_beanFieldPrefix.length());
				_name = _name.substring(0, 1).toLowerCase() + _name.substring(1);
			}
		}

		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// field declaration
		getOut().printf("    %s %s ;\n", _type, _name);
	}

	@Override
	public void outputFields()
	{
		getPrivateDeclaredFields(getSource()).forEach(_field -> {
			outputField(_field);
		});

		getOut().println();
	}

}
