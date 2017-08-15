package com.sporniket.libre.javabeans.doclet.expander.basic;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsFieldDoc.getAccessibleFields;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.expander.BuilderGenerator;
import com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.expander.UtilsClassname;
import com.sporniket.libre.javabeans.doclet.expander.UtilsFieldname;
import com.sun.javadoc.FieldDoc;

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
