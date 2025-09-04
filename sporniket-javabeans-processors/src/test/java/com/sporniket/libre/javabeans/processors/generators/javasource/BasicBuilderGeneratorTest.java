package com.sporniket.libre.javabeans.processors.generators.javasource;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sporniket.libre.javabeans.models.javacode.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.javacode.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.models.javacode.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.javacode.AnnotationSpecs_Builder;
import com.sporniket.libre.javabeans.models.javacode.ClassSpecs;
import com.sporniket.libre.javabeans.models.javacode.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.models.javacode.FieldSpecs;
import com.sporniket.libre.javabeans.models.javacode.FieldSpecs_Builder;
import com.sporniket.libre.javabeans.models.javacode.ImportSpecs_Builder;

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
 * @version 25.11.00
 * @since 25.11.01
 */
@ExtendWith(MockitoExtension.class)
final class BasicBuilderGeneratorTest
{
	/**
	 * @deprecated details should be setup differently.
	 */
	@Deprecated
	@Mock
	DocletOptions options;

	@BeforeEach
	void setupOptions()
	{
		when(options.getBuilderSuffix()).thenReturn("_Builder");
	}

	@Test
	public void should_generate_javabean_source_code()
	{
		// prepare
		// -- field titi (primitive boolean)
		final FieldSpecs _primitiveBooleanField = new FieldSpecs_Builder()//
				.withDirectlyRequired(true)//
				.withFieldPrefix("my")//
				.withArrayMarker("")//
				.withNameForAccessor("Titi")//
				.withNameForField("Titi")//
				.withTypeInvocation("foo")//
				.withAnnotations(List.of())//
				.done();

		// -- field toto (general type)
		final String[] _javadocLines = new String[]
		{
				"short description of field", "", "other description"
		};
		final String[] _javadocLinesClass = new String[]
		{
				"A very usefull class."
		};
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotationForGet = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.ForGet") //
				.withParameters(List.of(_parameter)) //
				.done();
		final AnnotationSpecs _annotationForSet = new AnnotationSpecs_Builder()//
				.withOnSetter(true)//
				.withType("my.annotations.ForSet") //
				.withParameters(List.of(_parameter)) //
				.done();
		final FieldSpecs _typicalField = new FieldSpecs_Builder()//
				.withDirectlyRequired(true) //
				.withFieldPrefix("my") //
				.withArrayMarker("") //
				.withNameForAccessor("toto") //
				.withNameForField("toto") //
				.withTypeInvocation("foo") //
				.withBooleanGetter(false) //
				.withAnnotations(List.of(_annotationForGet, _annotationForSet)) //
				.withJavadocLines(_javadocLines) //
				.done();

		// -- class
		final ClassSpecs _specs = new ClassSpecs_Builder() //
				.withPackageName("my.great.package") //
				.withImports(List.of( //
						new ImportSpecs_Builder().withClassName("a.b.c.Cee").withDirectlyRequired(true).done(), //
						new ImportSpecs_Builder().withClassName("a.b.d.Dee").withDirectlyRequired(true).done(), //
						new ImportSpecs_Builder().withClassName("a.b.e.Eee").withDirectlyRequired(true).done() //
				)) //
				.withAnnotations(List.of()) //
				.withJavadocLines(_javadocLinesClass)//
				.withClassName("GreatClass") //
				.withFields(List.of(_primitiveBooleanField, _typicalField)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
		final BasicBuilderGenerator _generator = new Builder<>(new BasicBuilderGenerator()) //
				.withOptions(options) //
				.withClassSpecs(_specs) //
				.done();

		// execute
		_generator.generate(_psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		then(_result).containsExactly( //
				"package my.great.package;", //
				"", //
				"import a.b.c.Cee;", //
				"import a.b.d.Dee;", //
				"import a.b.e.Eee;", //
				"", //
				"/**", //
				" * A very usefull class.", //
				" */", //
				"public class GreatClass_Builder", //
				"{", //
				"    private final GreatClass bean ;", //
				"", //
				"    public GreatClass done() {return bean ;}", //
				"", //
				"    /**", //
				"     * Default constructor.", //
				"     */", //
				"    public GreatClass_Builder() {bean = new GreatClass() ;}", //
				"", //
				"    /**", //
				"     * Constructor that delegates the bean instanciation.", //
				"     * @param newBean the instanciated bean to use.", //
				"     */", //
				"    public GreatClass_Builder(GreatClass newBean) {bean = newBean ;}", //
				"", //
				"    public GreatClass_Builder withTiti(foo value) {bean.setTiti(value); return this;}", //
				"    /**", //
				"     * short description of field", //
				"     * ", //
				"     * other description", //
				"     * ", //
				"     * @param value the new value", //
				"     * ", //
				"     * @returns the builder", //
				"     */", //
				"    public GreatClass_Builder withtoto(foo value) {bean.settoto(value); return this;}", //
				"}");
	}
}
