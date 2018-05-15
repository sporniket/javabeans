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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import com.sporniket.libre.javabeans.doclet.codespecs.*;
import com.sporniket.libre.lang.string.StringTools;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.TypeVariable;

/**
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
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
 * @version 17.12.00
 * @since 17.12.00
 */
public class CodeSpecsExtractor
{
	public static enum ExtractionMode
	{
		DISTILLER,
		EXPANDER;
	}

	private static final String JAVA_LANG_DEPRECATED = "java.lang.Deprecated";

	private String extractClassDeclaredTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.outputClassParameters__classDeclaration(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}

	private String extractClassInvokedTypeArguments(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		final TypeVariable[] _typeArguments = from.typeParameters();
		UtilsClassDoc.TypeInvocation.outputTypeArguments(_result, _typeArguments, translations, shortables);

		return _result.toString();
	}

	private List<AnnotationParameterSpecs> extractFieldAnnotationParameters(AnnotationDesc annotation,
			final String annotationQualifiedName)
	{
		final ElementValuePair[] _elementValues = annotation.elementValues();
		final List<AnnotationParameterSpecs> result = new ArrayList<>(_elementValues.length);

		System.out.println("========================================================");
		System.out.printf("dump parameters of annotation '%s'\n", annotationQualifiedName);
		for (AnnotationDesc.ElementValuePair valuePair : _elementValues)
		{
			final AnnotationTypeElementDoc _element = valuePair.element();
			System.out.printf("--> %s:\n", _element.name(), _element.qualifiedName());
			final AnnotationValue _value = valuePair.value();
			Object _realValue = _value.value();
			Class<?> _valueClass = _realValue.getClass();
			System.out.printf("    value of type '%s' (is array = %s)", _valueClass.getName(),
					Boolean.toString(_valueClass.isArray()));
			System.out.printf(" = %s", _realValue.toString());

			System.out.println();

			if (!_valueClass.isArray())
			{
				result.add(new AnnotationParameterSpecsSingleValue_Builder()//
						.withName(_element.name())//
						.withValue(_realValue)//
						.done());
			}
			else
			{
				result.add(new AnnotationParameterSpecsValuesArray_Builder()//
						.withName(_element.name())//
						.withValues(Arrays.asList((Object[]) _realValue))//
						.done());
			}
		}
		System.out.println("========================================================");
		return result;
	}

	private List<AnnotationSpecs> extractFieldAnnotations(ClassDoc srcClass, Map<String, String> translations,
			Set<String> shortables)
	{
		List<AnnotationSpecs> _result = new ArrayList<>(srcClass.annotations().length);
		for (AnnotationDesc annotation : srcClass.annotations())
		{
			final String _qualifiedName = annotation.annotationType().qualifiedName();
			boolean _needParametersProcessing = annotation.elementValues().length > 0;

			final AnnotationSpecs_Builder _annotationBuilder = new AnnotationSpecs_Builder()//
					.withType(computeOutputType(annotation.annotationType(), translations, shortables))//
					.withOnBuilder(JAVA_LANG_DEPRECATED.equals(_qualifiedName));

			if (_needParametersProcessing)
			{
				_annotationBuilder.withParameters(extractFieldAnnotationParameters(annotation, _qualifiedName));
			}

			_result.add(_annotationBuilder.done());
		}
		return _result;
	}

	private List<AnnotationSpecs> extractFieldAnnotations(FieldDoc field, Map<String, String> translations, Set<String> shortables)
	{
		List<AnnotationSpecs> _result = new ArrayList<>(field.annotations().length);
		for (AnnotationDesc annotation : field.annotations())
		{
			final String _qualifiedName = annotation.annotationType().qualifiedName();
			boolean _needParametersProcessing = annotation.elementValues().length > 0;
			if (!_needParametersProcessing)
			{
				_result.add(new AnnotationSpecs_Builder()
						.withType(computeOutputType(annotation.annotationType(), translations, shortables))//
						.withOnField(true)//
						.withOnGetter(JAVA_LANG_DEPRECATED.equals(_qualifiedName))//
						.withOnSetter(JAVA_LANG_DEPRECATED.equals(_qualifiedName))//
						.withOnBuilder(JAVA_LANG_DEPRECATED.equals(_qualifiedName))//
						.done());
			}
			else
			{
				extractFieldAnnotationParameters(annotation, _qualifiedName);
			}
		}
		return _result;
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
			final String _capitalizedName = computeFieldAccessorSuffix(f.name());
			return new FieldSpecs_Builder()//
					.withNameForField(_noPrefix ? f.name() : _capitalizedName)//
					.withNameForAccessor(_capitalizedName)//
					.withFieldPrefix(_noPrefix ? "this." : options.getBeanFieldPrefix())//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.withBooleanGetter(("boolean".equals(f.type().qualifiedTypeName()))
							|| ("java.lang.Boolean".equals(f.type().qualifiedTypeName())))//
					.withAnnotations(extractFieldAnnotations(f, translations, shortables))//
					.done();
		}) : (f -> {
			final String _unprefixedName = removePrefix(f.name(), options.getBeanFieldPrefix());
			return new FieldSpecs_Builder()//
					.withNameForField(_unprefixedName)//
					.withTypeInvocation(computeOutputType_invocation(f.type(), translations, shortables))//
					.withDirectlyRequired(_directlyRequiredFields.contains(f.name()))//
					.withAnnotations(extractFieldAnnotations(f, translations, shortables))//
					.done();
		});

		final List<FieldDoc> _fields = (EXPANDER == mode) ? getAccessibleFields(from) : _directFields;
		return _fields.stream()//
				.map(_toFieldSpecs)//
				.collect(toList());
	}

	private String extractInterfaceList(ClassDoc from, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
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

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping,
				_knownClasses.stream().map(ImportSpecs::getClassName).collect(toList()));
		updateShortClassnameMappingFromClassnames(_shortNameMapping, translations.values());
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

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
				.withAnnotations(extractFieldAnnotations(from, translations, _shortables))//
				.done();
	}

	private String extractSuperClassName(ClassDoc superclass, Map<String, String> translations, Set<String> shortables)
	{
		final StringBuilder _result = new StringBuilder();
		if (!Object.class.getName().equals(superclass.qualifiedName()))
		{
			_result//
					.append(computeOutputClassname(superclass.qualifiedTypeName(), translations, shortables))//
					.append(extractClassInvokedTypeArguments(superclass, translations, shortables));
		}

		return _result.toString();
	}
}
