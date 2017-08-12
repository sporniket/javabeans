package com.sporniket.libre.javabeans.doclet.expander.basic;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.*;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsFieldDoc.getAccessibleDeclaredFields;

import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.expander.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.expander.UtilsFieldname;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.tools.internal.ws.processor.generator.GeneratorBase;

public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	private static final Predicate<? super String> IS_NOT_JAVA_LANG_TYPE = c -> !Object.class.getPackage().getName()
			.equals(getPackageName(c));

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
	public void outputBuilderPattern()
	{
		final BasicBuilderGenerator _generator = new BasicBuilderGenerator();
		_generator.setKnownClasses(getKnownClasses());
		_generator.setOut(getOut());
		_generator.setShortables(getShortables());
		_generator.setSource(getSource());
		_generator.setTranslations(getTranslations());

		_generator.generate();

	}

	@Override
	public void outputClassBegin()
	{
		final StringBuilder _classDecl = new StringBuilder("public class ");
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

	@Override
	public void outputClassEnd()
	{
		getOut().println("}\n");
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

	@Override
	public void outputImportStatements()
	{
		final Predicate<? super String> _isNotInSamePackage = c -> !getSource().containingPackage().name()
				.equals(getPackageName(c));

		getKnownClasses().stream().filter(IS_NOT_JAVA_LANG_TYPE).filter(_isNotInSamePackage)
				.collect(Collectors.toCollection(TreeSet::new)).forEach(c -> getOut().printf("import %s;\n", c));

		getOut().println();
	}

	@Override
	public void outputPackageStatement()
	{
		getOut().printf("package %s;\n", getSource().containingPackage().name());

		getOut().println();
	}

}
