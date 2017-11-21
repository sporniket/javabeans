/**
 * 
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.computeOutputClassname;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldname.computeFieldAccessorSuffix;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs_Builder;
import com.sporniket.libre.lang.string.StringTools;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.TypeVariable;

/**
 * Extract code specification data from {@link ClassDoc} data.
 * 
 * @author dsporn
 *
 */
public class CodeSpecsExtractor
{
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

	private List<FieldSpecs> extractFields(ClassDoc from, Map<String, String> translations, Set<String> shortables,
			DocletOptions options)
	{
		final TreeSet<String> _directlyRequiredFields = getAccessibleDeclaredFields(from)//
				.stream()//
				.map(FieldDoc::name)//
				.collect(toCollection(TreeSet::new));

		final boolean _noPrefix = StringTools.isEmptyString(options.getBeanFieldPrefix());

		final Function<? super FieldDoc, ? extends FieldSpecs> _toFieldSpecs = f -> {
			String _capitalizedName = computeFieldAccessorSuffix(f.name());
			return new FieldSpecs_Builder()//
					.withNameForField(_noPrefix ? f.name() : _capitalizedName)//
					.withNameForAccessor(_capitalizedName)//
					.withFieldPrefix(_noPrefix ? "this." : options.getBeanFieldPrefix())//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.done();
		};

		return getAccessibleFields(from).stream()//
				.map(_toFieldSpecs)//
				.collect(toList());
	}

	public ClassSpecs extractSpecs(ClassDoc from, Map<String, String> translations, Set<String> shortables, DocletOptions options)
	{
		return new ClassSpecs_Builder()//
				.withImports(updateKnownClasses(from))//
				.withClassName(computeOutputClassname(from.qualifiedTypeName(), translations, shortables))//
				.withDeclaredTypeArguments(extractClassDeclaredTypeArguments(from, translations, shortables))//
				.withInvokedTypeArguments(extractClassInvokedTypeArguments(from, translations, shortables))//
				.withFields(extractFields(from, translations, shortables, options))//
				.done();
	}
}
