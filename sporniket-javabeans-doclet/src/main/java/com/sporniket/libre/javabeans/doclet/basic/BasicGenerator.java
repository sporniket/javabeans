/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.basic;

import static com.sporniket.libre.javabeans.doclet.UtilsClassname.getPackageName;
import static com.sporniket.libre.javabeans.doclet.basic.Utils.IS_NOT_JAVA_LANG_TYPE;

import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.JavaSourceGenerator;

/**
 * Base class for generating java code.
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
