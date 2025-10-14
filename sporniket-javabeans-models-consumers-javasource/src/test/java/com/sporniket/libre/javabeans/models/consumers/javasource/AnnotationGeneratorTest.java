package com.sporniket.libre.javabeans.models.consumers.javasource;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import com.sporniket.libre.javabeans.models.AnnotationParameterSpecs;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsValuesArray_Builder;
import com.sporniket.libre.javabeans.models.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.AnnotationSpecs_Builder;

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
class AnnotationGeneratorTest
{
	@Test
	void should_fail_hard_on_unknown_annotation_parameter_implementation()
	{
		// prepare
		final AnnotationParameterSpecs _parameter = new AnnotationParameterSpecs()
		{
		};
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of(_parameter)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute and verify
		BDDAssertions.thenThrownBy(() -> new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream()))
				.isInstanceOf(IllegalStateException.class).hasMessage("Unsupported annotation parameter specs.");
	}

	@Test
	void should_output_simple_annotations()
	{
		// prepare
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of(_parameter)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple(", //
				"        foo = \"the value\"", //
				"    )");
	}

	@Test
	void should_output_non_string_parameter_without_quote_enclosing()
	{
		// prepare
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue(1234) //
				.withString(false) //
				.done();
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of(_parameter)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple(", //
				"        foo = 1234", //
				"    )");
	}

	@Test
	void should_output_concise_parametrized_annotation_when_possible()
	{
		// prepare
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("value") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of(_parameter)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple(\"the value\")");
	}

	@Test
	void should_output_annotation_that_has_no_parameter_without_parenthesis()
	{
		// prepare
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of()) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple");
	}

	@Test
	void should_degrade_gracefully_when_parameters_is_null()
	{
		// prepare
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(null) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple");
	}

	@Test
	void should_output_annotations_with_multiple_arguments()
	{
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of( //
						new AnnotationParameterSpecsSingleValue_Builder() //
								.withName("value") //
								.withValue("the value of value") //
								.withString(true) //
								.done(), //
						new AnnotationParameterSpecsSingleValue_Builder() //
								.withName("foo") //
								.withValue("the value of foo") //
								.withString(true) //
								.done(), //
						new AnnotationParameterSpecsValuesArray_Builder() //
								.withName("bar") //
								.withValues(List.of( //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("a") //
												.withValue("the value of bar.a") //
												.withString(true) //
												.done(), //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("b") //
												.withValue("the value of bar.b") //
												.withString(true) //
												.done(), //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("value") //
												.withValue("the value of bar.value") //
												.withString(true) //
												.done() //
								)) //
								.done() //
				)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple(", //
				"        value = \"the value of value\", ", //
				"        foo = \"the value of foo\", ", //
				"        bar = {", //
				"                  \"the value of bar.a\", ", //
				"                  \"the value of bar.b\", ", //
				"                  \"the value of bar.value\"", //
				"            }", //
				"    )");
	}

	@Test
	void should_output_annotations_with_arguments_being_parameterized_annotations()
	{
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotation = new AnnotationSpecs_Builder()//
				.withOnGetter(true)//
				.withType("my.annotations.Simple") //
				.withParameters(List.of( //
						new AnnotationParameterSpecsSingleValue_Builder() //
								.withName("value") //
								.withValue("the value of value") //
								.withString(true) //
								.done(), //
						new AnnotationParameterSpecsSingleValue_Builder() //
								.withName("foo") //
								.withValue("the value of foo") //
								.withString(true) //
								.done(), //
						new AnnotationParameterSpecsValuesArray_Builder() //
								.withName("bar") //
								.withPrefix("@foo.bar.annotation.MyAnnotation(") //
								.withPostfix(")") //
								.withValues(List.of( //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("a") //
												.withValue("the value of bar.a") //
												.withString(true) //
												.done(), //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("b") //
												.withValue("the value of bar.b") //
												.withString(true) //
												.done(), //
										new AnnotationParameterSpecsSingleValue_Builder() //
												.withName("value") //
												.withValue("the value of bar.value") //
												.withString(true) //
												.done() //
								)) //
								.done() //
				)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();

		// execute
		new AnnotationGenerator().outputAnnotation(_annotation, "    ", _psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		// verify
		then(_result).containsExactly( //
				"    @my.annotations.Simple(", //
				"        value = \"the value of value\", ", //
				"        foo = \"the value of foo\", ", //
				"        bar = @foo.bar.annotation.MyAnnotation({", //
				"                  \"the value of bar.a\", ", //
				"                  \"the value of bar.b\", ", //
				"                  \"the value of bar.value\"", //
				"            })", //
				"    )");
	}

}
