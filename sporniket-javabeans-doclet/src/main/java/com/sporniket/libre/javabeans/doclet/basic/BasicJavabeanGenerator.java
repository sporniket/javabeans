package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.getAccessibleDeclaredFields;

import com.sporniket.libre.javabeans.doclet.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.UtilsFieldname;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	//FIXME use DocletOptions.getBeanFieldPrefix() to optionnaly prefix the field name, or use 'this.field'.

	private void outputAccessor(FieldDoc field)
	{
		final String _accessorSuffix = UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// getter
		getOut().printf("    public %s get%s() {return my%s ;}\n", _type, _accessorSuffix, _accessorSuffix);

		// setter
		getOut().printf("    public void set%s(%s value) {my%s = value;}\n", _accessorSuffix, _type, _accessorSuffix);

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
		final StringBuilder _classDecl = new StringBuilder(shouldBeAbstract(getSource()) ? "public abstract class " : "public class ");
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
		getOut().printf("    private %s my%s ;\n", _type, _accessorSuffix);
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
