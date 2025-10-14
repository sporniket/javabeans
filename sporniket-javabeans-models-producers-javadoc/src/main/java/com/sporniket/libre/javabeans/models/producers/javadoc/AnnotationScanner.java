package com.sporniket.libre.javabeans.models.producers.javadoc;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

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

public class AnnotationScanner
{
	List<AnnotationSpecs> scan(final TypeElement _te, final ScanContext context, final ScanConfiguration configuration)
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
				.withValue("???" + _mainType.append(" -- ").append(_subType).append(" -- ").append(_exType.toString()).append(".")
						.append(v.getValue().toString()).toString()) //
				.withString("java.lang.String".equals(_exType.toString())) //
				.done();
	}
}
