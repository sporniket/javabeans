package com.sporniket.libre.javabeans.doclet.encapsulator.basic;

import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassname.*;
import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsFieldDoc.getAccessibleDeclaredFields;

import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.encapsulator.JavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.encapsulator.UtilsFieldname;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

public class BasicJavabeanGenerator extends BasicGenerator implements JavabeanGenerator
{
	private static final Predicate<? super String> IS_NOT_JAVA_LANG_TYPE = c -> !Object.class.getPackage().getName().equals(getPackageName(c));

	private void outputAccessor(FieldDoc field)
	{
		final String _accessorSuffix = UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), getTranslations(), getShortables());

		// getter
		getOut().printf("    public %s get%s() {return pojo.%s ;}\n", _type, _accessorSuffix, field.name());

		// setter
		getOut().printf("    public void set%s(%s value) {pojo.%s = value;}\n", _accessorSuffix, _type, field.name());

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
		BasicBuilderGenerator _generator = new BasicBuilderGenerator() ;
		_generator.setKnownClasses(getKnownClasses());
		_generator.setOut(getOut());
		_generator.setShortables(getShortables());
		_generator.setSource(getSource());
		_generator.setTranslations(getTranslations());
		
		_generator.generate();
		
	}

	@Override
	public void outputBuilderSection()
	{
		getOut().println();

		StringBuilder _builderUtility = new StringBuilder("    public static ");
		outputClassName__beanType(_builderUtility, getSource(), getTranslations(), getShortables());
		_builderUtility.append(".Builder build() {return new ");
		outputClassName__beanType(_builderUtility, getSource(), getTranslations(), getShortables());
		_builderUtility.append(".Builder() ;}");
		getOut().println(_builderUtility.toString());

		getOut().println();		
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

	@Override
	public void outputFields()
	{
		final StringBuilder _pojoDecl = new StringBuilder("    private final ");
		outputClassName__pojoType(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append(" pojo = new ");
		outputClassName__pojoInstanciation(_pojoDecl, getSource(), getTranslations(), getShortables());

		getOut().printf(_pojoDecl.append("() ;\n\n").toString());
	}

	@Override
	public void outputImportStatements()
	{
		final Predicate<? super String> _isNotInSamePackage = c -> !getSource().containingPackage().name().equals(getPackageName(c));

		getKnownClasses().stream().filter(IS_NOT_JAVA_LANG_TYPE).filter(_isNotInSamePackage).collect(Collectors.toCollection(TreeSet::new))
				.forEach(c -> getOut().printf("import %s;\n", c));

		getOut().println();
	}

	@Override
	public void outputPackageStatement()
	{
		getOut().printf("package %s;\n", getSource().containingPackage().name());

		getOut().println();
	}

}
