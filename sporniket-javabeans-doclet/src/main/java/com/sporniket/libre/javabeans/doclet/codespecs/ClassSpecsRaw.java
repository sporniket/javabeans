package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.Collection;
import java.util.List;

class ClassSpecsRaw
{
	List<AnnotationSpecsRaw> annotations;

	/**
	 * Type arguments when defining the class (<code>class</code> keyword).
	 */
	String declaredTypeArguments;

	List<FieldSpecsRaw> fields;
	
	Collection<ImportSpecsRaw> imports;
	
	/**
	 * Type arguments when invoking the class (e.g. in the builder).
	 */
	String invokedTypeArguments;
	
	/**
	 * Class simple name (without package).
	 */
	String className;
	
	Boolean abstractRequired;
}
