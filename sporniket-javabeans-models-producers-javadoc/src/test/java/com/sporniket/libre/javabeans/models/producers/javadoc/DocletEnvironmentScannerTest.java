package com.sporniket.libre.javabeans.models.producers.javadoc;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.junit.jupiter.api.Test;

import com.sporniket.libre.javabeans.models.ClassSpecs;

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
class DocletEnvironmentScannerTest
{
	@Test
	void should_extract_class_specifications_from_doclet_environment()
	{
		final List<Element> _elements = List.of(mockClassElement("MyGreatClass"), mockClassElement("MyOtherClass"));
		final String _simpleName = "my-great-package";
		final Element _elPackage = mockPackage(_simpleName, _elements);

		final DocletEnvironment _environment = mock(DocletEnvironment.class);
		doReturn(Set.of(_elPackage)).when(_environment).getIncludedElements();

		final Collection<ClassSpecs> _result = new DocletEnvironmentScanner().scan(_environment,
				new ScanConfiguration());

		then(_result) //
				.hasSize(2) //
				.extracting("className", "classNameFullyQualified") //
				.containsExactly( //
						tuple("MyGreatClass", "my-great-package.MyGreatClass"), //
						tuple("MyOtherClass", "my-great-package.MyOtherClass") //
				);
	}

	Element mockPackage(final String simpleName, final List<Element> enclosedElements)
	{
		final Element _elPackage = mock(Element.class);
		when(_elPackage.getKind()).thenReturn(ElementKind.PACKAGE);
		when(_elPackage.getSimpleName()).thenReturn(new NameString(simpleName));
		doReturn(enclosedElements).when(_elPackage).getEnclosedElements();
		enclosedElements.forEach(e -> when(e.getEnclosingElement()).thenReturn(_elPackage));
		return _elPackage;
	}

	Element mockClassElement(final String simpleName)
	{
		final Element _elClass = mock(Element.class);
		when(_elClass.getKind()).thenReturn(ElementKind.CLASS);
		when(_elClass.getSimpleName()).thenReturn(new NameString(simpleName));
		return _elClass;
	}

}
