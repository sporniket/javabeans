package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.Collection;
import java.util.List;

class ClassSpecsRaw
{
	Boolean abstractRequired;

	List<AnnotationSpecsRaw> annotations;

	/**
	 * Class simple name (without package).
	 */
	String className;
	
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
	 * fully qualified package name.
	 */
	String packageName;
	
	/**
	 * (NOT NULL) Simple name of the extended class (MUST be imported), or empty if the super class is <code>Object</code>.
	 */
	String superClassName ;
	
	/**
	 * (NOT NULL) Comma separated values of interfaces names. 
	 * 
	 * Each name SHOULD be simple, unless the type name is not in the list of shortable types. 
	 */
	String interfaceList ;
}
