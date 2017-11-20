/**
 * 
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.updateKnownClasses;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;

import java.util.Map;
import java.util.Set;

import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs_Builder;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.TypeVariable;

/**
 * Extract code specification data from {@link ClassDoc} data.
 * 
 * @author dsporn
 *
 */
public class CodeSpecsExtractor
{
	public ClassSpecs extractSpecs(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		return new ClassSpecs_Builder()//
				.withImports(updateKnownClasses(from))//
				.withClassName(computeOutputClassname(from.qualifiedTypeName(), translations, shortables))//
				.withDeclaredTypeArguments(extractClassDeclaredTypeArguments(from, translations, shortables))//
				.withInvokedTypeArguments(extractClassInvokedTypeArguments(from, translations, shortables))//
				//FIXME fields
				.done();
	}

	private String extractClassDeclaredTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.outputClassParameters__classDeclaration(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}

	private String extractClassInvokedTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.TypeInvocation.outputTypeArguments(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}
}
