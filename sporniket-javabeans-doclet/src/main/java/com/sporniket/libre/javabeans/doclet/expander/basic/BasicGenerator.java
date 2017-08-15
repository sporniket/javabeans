/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.expander.basic;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.getPackageName;
import static com.sporniket.libre.javabeans.doclet.expander.basic.Utils.IS_NOT_JAVA_LANG_TYPE;

import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.expander.JavaSourceGenerator;

/**
 * Base class for generating java code.
 * 
 * @author dsporn
 *
 */
public abstract class BasicGenerator extends BasicGeneratorBase implements JavaSourceGenerator
{
	@Override
	public void outputClassEnd()
	{
		getOut().println("}\n");
	}

	@Override
	public void outputImportStatements()
	{
		final Predicate<? super String> _isNotInSamePackage = c -> !getSource().containingPackage().name()
				.equals(getPackageName(c));

		getKnownClasses().stream().filter(c -> !"?".equals(c)).filter(IS_NOT_JAVA_LANG_TYPE).filter(_isNotInSamePackage)
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
