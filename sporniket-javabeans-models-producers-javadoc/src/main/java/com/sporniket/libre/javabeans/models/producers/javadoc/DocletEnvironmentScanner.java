package com.sporniket.libre.javabeans.models.producers.javadoc;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.sporniket.libre.javabeans.models.AnnotationParameterSpecs;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsValuesArray_Builder;
import com.sporniket.libre.javabeans.models.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.AnnotationSpecs_Builder;
import com.sporniket.libre.javabeans.models.ClassSpecs;
import com.sporniket.libre.javabeans.models.ClassSpecs_Builder;

import jdk.javadoc.doclet.DocletEnvironment;

/**
 * <p>
 * &copy; Copyright 2012-2023 David Sporn
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
 * @version 23.07.00
 * @since 25.11.00
 */
public class DocletEnvironmentScanner
{
	public Collection<ClassSpecs> scan(final DocletEnvironment root, final DocletEnvironmentScanConfiguration configuration)
	{
		final List<Element> _accumulator = new ArrayList<>();
		root.getIncludedElements().forEach(e -> {
			findClasses(e, _accumulator);
		});
		return _accumulator.stream() //
				.map(_e -> {
					final TypeElement _te = (TypeElement) _e;
					final String _packageName = root.getElementUtils().getPackageOf(_e).getQualifiedName().toString();
					final String _qualifiedName = _te.getQualifiedName().toString();
					final List<AnnotationSpecs> _annotations = mapToAnnotationSpecs(_te);
					return new ClassSpecs_Builder() //
							.withClassName(_te.getSimpleName().toString()) //
							.withClassNameFullyQualified(_qualifiedName) //
							.withPackageName(_packageName) //
							.withAnnotations(_annotations) //
							.done();
				}) //
				.collect(toList());
	}

	private AnnotationParameterSpecs mapToAnnotationParameterSpecs_scratch(final ExecutableElement _ex, final AnnotationValue _v)
	{
		// dump _ex
		System.out.println("===> executable element");
		System.out.println(_ex.getReturnType().toString());
		System.out.println(_ex.getReturnType().getKind());
		System.out.println("<--- executable element");
		switch (_ex.getReturnType().getKind())
		{
			case DECLARED -> {
				final DeclaredType dtype = (DeclaredType) _ex.getReturnType();
				final Element typeAsElement = dtype.asElement();
				switch (typeAsElement.getKind())
				{
					case ENUM_CONSTANT -> {
						return new AnnotationParameterSpecsSingleValue_Builder() //
								.withName(_ex.getSimpleName().toString()) //
								.withValue(_ex.getReturnType().toString() + "." + _v.getValue().toString()) //
								.done();
					}
					default -> {
						return new AnnotationParameterSpecsSingleValue_Builder() //
								.withName(_ex.getSimpleName().toString()) //
								.withValue(_v.getValue().toString()) //
								.withString("java.lang.String".equals(_ex.getReturnType().toString())) //
								.done();
					}
				}
			}
			case ARRAY -> {
				final ArrayType atype = (ArrayType) _ex.getReturnType();
				switch (atype.getComponentType().getKind())
				{
					case DECLARED -> {
						final DeclaredType dtype = (DeclaredType) atype.getComponentType();
						final Element typeAsElement = dtype.asElement();
						switch (typeAsElement.getKind())
						{
							case ENUM_CONSTANT -> {
								return new AnnotationParameterSpecsSingleValue_Builder() //
										.withName(_ex.getSimpleName().toString()) //
										.withValue(dtype.toString() + "." + _v.getValue().toString()) //
										.done();
							}
							default -> {
								return new AnnotationParameterSpecsSingleValue_Builder() //
										.withName(_ex.getSimpleName().toString()) //
										.withValue(_v.getValue().toString()) //
										.withString("java.lang.String".equals(_ex.getReturnType().toString())) //
										.done();
							}
						}
					}
					default -> {
						final List<AnnotationParameterSpecsSingleValue> _values = asList(_v.getValue().toString().split(",")) //
								.stream() //
								.map(p -> new AnnotationParameterSpecsSingleValue_Builder().withValue(p).done()) //
								.collect(Collectors.toList());
						return new AnnotationParameterSpecsValuesArray_Builder() //
								.withName(_ex.getSimpleName().toString()) //
								.withValues(_values)//
								.done();
					}
				}
			}
		}
		return new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("???" + _ex.getSimpleName().toString()) //
				.withValue("???" + _ex.getReturnType().toString() + "." + _v.getValue().toString()) //
				.withString("java.lang.String".equals(_ex.getReturnType().toString())) //
				.done();
	}

	private AnnotationParameterSpecs mapToAnnotationParameterSpecs(final ExecutableElement ex, final AnnotationValue v)
	{
		final TypeMirror _exType = ex.getReturnType();
		final TypeKind _exKind = _exType.getKind();
		final StringBuilder _mainType = new StringBuilder().append(_exKind.name());
		String _subType = "*";
		switch (_exKind)
		{
			case DECLARED -> {
				final DeclaredType _dtype = (DeclaredType) _exType;
				final Element _el = _dtype.asElement();
				final ElementKind _elKind = _el.getKind();
				_subType = _elKind.name();
				switch (_elKind)
				{
					case ENUM -> {
						// TODO output shortened name of value if applicable
						// * find top enclosing type of enum i.e. element directly enclosed by a package
						// * add top enclosing type to shortable (provide shortables)
						// * output shortened path of value
						// e.g. 'com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME' -> 'JsonTypeInfo.Id.NAME'
						return new AnnotationParameterSpecsSingleValue_Builder() //
								.withName(ex.getSimpleName().toString()) //
								.withValue(_exType.toString() + "." + v.getValue().toString()) //
								.done();
					}
					case CLASS -> {
						return new AnnotationParameterSpecsSingleValue_Builder() //
								.withName(ex.getSimpleName().toString()) //
								.withValue(v.getValue().toString()) //
								.withString("java.lang.String".equals(_exType.toString())) //
								.done();
					}
				}
			}
			case ARRAY -> {
				final ArrayType _atype = (ArrayType) _exType;
				final TypeMirror _cType = _atype.getComponentType();
				final TypeKind _cKind = _cType.getKind();
				final List<AnnotationParameterSpecsSingleValue> _values = asList(v.getValue().toString().split(",")) //
						.stream() //
						.map(p -> new AnnotationParameterSpecsSingleValue_Builder().withValue(p).done()) //
						.collect(Collectors.toList());

				switch (_cKind)
				{
					case DECLARED -> {
						final DeclaredType _dtype = (DeclaredType) _cType;
						final Element _el = _dtype.asElement();
						final ElementKind _elKind = _el.getKind();
						_mainType.append("->").append(_el.getSimpleName()).append("/").append(_el.toString()).append("/")
								.append(_elKind.name()).append("[]");
						final StringBuilder _subTypeBuilder = new StringBuilder("{");
						_el.getEnclosedElements().forEach(_e -> {
							_subTypeBuilder.append(_e.getSimpleName()).append(",");
						});
						_subTypeBuilder.append("}");
						_subType = _subTypeBuilder.toString();
						switch (_elKind)
						{
							case ANNOTATION_TYPE -> {
								return new AnnotationParameterSpecsValuesArray_Builder() //
										.withName(ex.getSimpleName().toString()) //
										.withPrefix("@" + _el.toString() + "(") //
										.withPostfix(")") //
										.withValues(_values)//
										.done();
							}
						}
					}
				}
			}
		}

		return new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("???" + ex.getSimpleName().toString()) //
				.withValue("???" + _mainType.append(" -- ").append(_subType).append(" -- ").append(_exType.toString()).append(".").append(v.getValue().toString()).toString()) //
				.withString("java.lang.String".equals(_exType.toString())) //
				.done();
	}

	private List<AnnotationSpecs> mapToAnnotationSpecs(final TypeElement _te)
	{
		final List<AnnotationSpecs> _annotations = _te.getAnnotationMirrors() //
				.stream() //
				.map(_a -> {
					final DeclaredType _type = _a.getAnnotationType();
					final List<AnnotationParameterSpecs> _parameters = //
							_a.getElementValues().entrySet() //
									.stream().map(_entry -> mapToAnnotationParameterSpecs(_entry.getKey(), _entry.getValue())) //
									.collect(toList());
					return new AnnotationSpecs_Builder() //
							.withType(_type.toString()) //
							.withParameters(_parameters) //
							.done();
				}) //
				.collect(toList());
		return _annotations;
	}

	private void findClasses(final Element root, final List<Element> accumulator)
	{

		switch (root.getKind())
		{
			case CLASS:
				accumulator.add(root);
				break;
			case PACKAGE:
				root.getEnclosedElements().forEach(e -> findClasses(e, accumulator));
				break;
			default:
				// ignore
		}
	}
}
