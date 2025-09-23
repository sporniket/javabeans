package com.sporniket.libre.javabeans.models.producers.javadoc;

import static java.util.stream.Collectors.toList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import javax.lang.model.element.Element;

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
				.map(e -> {
					final String _packageName = computePackageName(e);
					final String _qualifiedName = _packageName + "." + e.getSimpleName().toString();
					return new ClassSpecs_Builder() //
							.withClassName(e.getSimpleName().toString()) //
							.withClassNameFullyQualified(_qualifiedName) //
							.done();
				}) //
				.collect(toList());
	}

	String computePackageName(final Element element)
	{
		final Deque<String> path = new ArrayDeque<>(20);
		for (Element container = element.getEnclosingElement(); container != null; container = container.getEnclosingElement())
		{
			path.addFirst(container.getSimpleName().toString());
		}

		return String.join(".", path);
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
