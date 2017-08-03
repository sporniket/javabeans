package com.sporniket.libre.javabeans.doclet.encapsulator.basic;

import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsFieldDoc.getAccessibleFields;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.encapsulator.BuilderGenerator;
import com.sporniket.libre.javabeans.doclet.encapsulator.UtilsFieldname;
import com.sun.javadoc.FieldDoc;

public class BasicBuilderGenerator extends BasicGenerator implements BuilderGenerator
{

	private static void outputSetter(final FieldDoc field, PrintStream out, final Map<String, String> translations,
			final Set<String> shortables)
	{
		final String _accessorSuffix = UtilsFieldname.computeFieldAccessorSuffix(field.name());
		final String _type = computeOutputType_invocation(field.type(), translations, shortables);

		// setter
		out.printf("        public Builder with%s(%s value) {bean.set%s(value); return this;}\n", _accessorSuffix, _type,
				_accessorSuffix);

		out.println();
	}

	@Override
	public void outputClassBegin()
	{
		getOut().println("    public static class Builder");
		getOut().println("    {");
	}

	@Override
	public void outputClassEnd()
	{
		getOut().println("    }");
	}

	@Override
	public void outputFields()
	{
		// bean instance
		final StringBuilder _pojoDecl = new StringBuilder("        private final ");
		outputClassName__beanType(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append(" bean = new ");
		outputClassName__beanInstanciation(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append("() ;\n\n");

		// bean getter
		_pojoDecl.append("        public ");
		outputClassName__beanType(_pojoDecl, getSource(), getTranslations(), getShortables());
		_pojoDecl.append(" done() {return bean ;}\n");

		// done
		getOut().println(_pojoDecl.toString());
	}

	@Override
	public void outputSetters()
	{
		getAccessibleFields(getSource()).forEach(f -> outputSetter(f, getOut(), getTranslations(), getShortables()));
	}

}
