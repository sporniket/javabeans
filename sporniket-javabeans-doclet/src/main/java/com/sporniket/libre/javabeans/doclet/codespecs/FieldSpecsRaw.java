package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

import com.sporniket.libre.javabeans.doclet.DocletOptions;

class FieldSpecsRaw
{
	List<AnnotationSpecsRaw> annotations;

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
	
	/**
	 * Field defined in the current class (not inherited)
	 */
	Boolean directlyRequired ;
}
