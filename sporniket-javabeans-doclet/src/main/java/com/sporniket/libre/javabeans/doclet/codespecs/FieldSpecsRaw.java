package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

import com.sporniket.libre.javabeans.doclet.DocletOptions;

/**
 * <p>
 * &copy; Copyright 2012-2019 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
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
 * @version 19.03.00
 * @since 17.12.00
 */
class FieldSpecsRaw
{
	List<AnnotationSpecsRaw> annotations;

	/**
	 * <code>true</code> if the type of the field is native <code>boolean</code> or <code>java.lang.Boolean</code>.
	 */
	boolean booleanGetter;

	/**
	 * Field defined in the current class (not inherited)
	 */
	boolean directlyRequired;

	/**
	 * Either <code>this.</code> or {@link DocletOptions#getBeanFieldPrefix()}
	 */
	String fieldPrefix;

	/**
	 * Unprefixed name for the accessor.
	 */
	String nameForAccessor;

	/**
	 * Unprefixed for the Javabean field.
	 */
	String nameForField;

	/**
	 * Expression of the field type.
	 */
	String typeInvocation;
}
