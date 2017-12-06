/**
 * 
 */
package com.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor.ExtractionMode.EXPANDER;
import static com.sporniket.libre.javabeans.doclet.UtilsClassDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsClassname.*;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.*;
import static com.sporniket.libre.javabeans.doclet.UtilsFieldname.*;
import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.FieldSpecs_Builder;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
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
	public static enum ExtractionMode
	{
		DISTILLER,
		EXPANDER;
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

	private List<FieldSpecs> extractFields(ClassDoc from, Map<String, String> translations, Set<String> shortables,
			DocletOptions options, ExtractionMode mode)
	{
		final boolean _noPrefix = StringTools.isEmptyString(options.getBeanFieldPrefix());

		final List<FieldDoc> _directFields = (EXPANDER == mode)
				? getAccessibleDeclaredFields(from)
				: getPrivateDeclaredFields(from);

		final TreeSet<String> _directlyRequiredFields = _directFields//
				.stream()//
				.map(FieldDoc::name)//
				.collect(toCollection(TreeSet::new));

		final Function<? super FieldDoc, ? extends FieldSpecs> _toFieldSpecs = (EXPANDER == mode) ? (f -> {
			String _capitalizedName = computeFieldAccessorSuffix(f.name());
			return new FieldSpecs_Builder()//
					.withNameForField(_noPrefix ? f.name() : _capitalizedName)//
					.withNameForAccessor(_capitalizedName)//
					.withFieldPrefix(_noPrefix ? "this." : options.getBeanFieldPrefix())//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.done();
		}) : (f -> {
			String _unprefixedName = removePrefix(f.name(), options.getBeanFieldPrefix());
			return new FieldSpecs_Builder()//
					.withNameForField(_unprefixedName)//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.done();
		});

		final List<FieldDoc> _fields = (EXPANDER == mode) ? getAccessibleFields(from) : _directFields;
		return _fields.stream()//
				.map(_toFieldSpecs)//
				.collect(toList());
	}

	private String extractInterfaceList(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		StringBuilder _result = new StringBuilder();
		final ClassDoc[] _interfaces = from.interfaces();
		if (_interfaces.length > 0)
		{
			for (int _i = 0; _i < _interfaces.length; _i++)
			{
				_result.append((0 == _i) ? "" : ", ")
						.append(computeOutputClassname(_interfaces[_i].name(), translations, shortables));
			}
		}

		return _result.toString();
	}

	public ClassSpecs extractSpecs(ClassDoc from, Map<String, String> translations, DocletOptions options, ExtractionMode mode)
	{
		final Collection<ImportSpecs> _knownClasses = updateKnownClasses(from);

		Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping,
				_knownClasses.stream().map(ImportSpecs::getClassName).collect(toList()));
		updateShortClassnameMappingFromClassnames(_shortNameMapping, translations.values());
		Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		return new ClassSpecs_Builder()//
				.withImports(_knownClasses)//
				.withClassName(computeOutputClassname(from.qualifiedTypeName(), translations, _shortables))//
				.withPackageName(from.containingPackage().name())//
				.withDeclaredTypeArguments(extractClassDeclaredTypeArguments(from, translations, _shortables))//
				.withInvokedTypeArguments(extractClassInvokedTypeArguments(from, translations, _shortables))//
				.withFields(extractFields(from, translations, _shortables, options, mode))//
				.withAbstractRequired(shouldBeAbstract(from))//
				.withSuperClassName(extractSuperClassName(from.superclass(), translations, _shortables))//
				.withInterfaceList(extractInterfaceList(from, translations, _shortables))//
				.done();
	}

	private String extractSuperClassName(ClassDoc superclass, Map<String, String> translations, Set<String> shortables)
	{
		StringBuilder _result = new StringBuilder();
		if (!Object.class.getName().equals(superclass.qualifiedName()))
		{
			_result//
					.append(computeOutputClassname(superclass.qualifiedTypeName(), translations, shortables))//
					.append(extractClassInvokedTypeArguments(superclass, translations, shortables));
		}

		return _result.toString();
	}
}
