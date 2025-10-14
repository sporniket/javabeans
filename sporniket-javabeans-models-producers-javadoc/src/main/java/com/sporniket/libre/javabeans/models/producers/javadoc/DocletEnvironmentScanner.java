package com.sporniket.libre.javabeans.models.producers.javadoc;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import com.sporniket.libre.javabeans.models.AnnotationSpecs;
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
	private final AnnotationScanner myAnnotationScanner = new AnnotationScanner();

	private final Set<String> DEADEND_ANNOTATION_NAMES = Set.of("java.lang.annotation.Documented",
			"java.lang.annotation.Retention", "java.lang.annotation.Target");

	public Collection<ClassSpecs> scan(final DocletEnvironment root, final ScanConfiguration configuration)
	{
		dumpEnvironment(root);
		final List<Element> _accumulator = new ArrayList<>();
		root.getIncludedElements().forEach(e -> {
			findClasses(e, _accumulator);
		});
		return _accumulator.stream() //
				.map(_e -> {
					final ScanContext _context = new ScanContext();
					final TypeElement _te = (TypeElement) _e;
					final String _packageName = root.getElementUtils().getPackageOf(_e).getQualifiedName().toString();
					final String _qualifiedName = _te.getQualifiedName().toString();
					final List<AnnotationSpecs> _annotations = myAnnotationScanner.scan(_te, _context, configuration);
					return new ClassSpecs_Builder() //
							.withClassName(_te.getSimpleName().toString()) //
							.withClassNameFullyQualified(_qualifiedName) //
							.withPackageName(_packageName) //
							.withAnnotations(_annotations) //
							.done();
				}) //
				.collect(toList());
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

	private void dumpEnvironment(final DocletEnvironment root)
	{
		System.out.println("==============================[Doclet environment]==============================");
		root.getIncludedElements().forEach(e -> dumpElement(e, ""));
		System.out.println("--------------------------------------------------------------------------------");
	}

	private void dumpElement(final Element e, final String indentation)
	{
		final String _name = e.toString();
		System.out.println(format("%s[%s] > '%s'", indentation, e.getKind(), _name));

		if (!DEADEND_ANNOTATION_NAMES.contains(_name))
		{
			final String _indentationAnnotation = indentation + "|    ";
			e.getAnnotationMirrors().forEach(am -> {
				dumpAnnotationMirror(am, _indentationAnnotation);
			});
		}

		final String _indentationChildren = indentation + "| ";
		e.getEnclosedElements() //
				.stream() //
				.filter(ee -> ee != e) //
				.forEach(ee -> {
			dumpElement(ee, _indentationChildren);
		});
	}

	private void dumpAnnotationMirror(final AnnotationMirror am, final String indentation)
	{
		System.out.println(format("%s[_annotation_]", indentation));
		final String _indentationMain = indentation + "| ";
		dumpElement(am.getAnnotationType().asElement(), _indentationMain);
		final String _indentationChildren = _indentationMain + "|   ";
		am.getElementValues().forEach((_x, _v) -> {
			dumpAnnotationValue(_x, _v, _indentationChildren);
		});
	}

	private void dumpAnnotationValue(final ExecutableElement x, final AnnotationValue v, final String indentation)
	{
		System.out.println(format("%s%s", indentation, x.getSimpleName()));
		System.out.println(format("%s = %s", indentation, v.getValue().toString()));

	}
}
