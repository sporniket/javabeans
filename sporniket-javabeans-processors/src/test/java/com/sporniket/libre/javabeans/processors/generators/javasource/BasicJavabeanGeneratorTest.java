/**
 *
 */
package com.sporniket.libre.javabeans.processors.generators.javasource;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Nested;
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
 * @version 23.07.00
 * @since 17.09.01
 */
@ExtendWith(MockitoExtension.class)
public class BasicJavabeanGeneratorTest
{
	@Mock
	DocletOptions options;

	@Nested
	class Describe__outputAccessors
	{

		@Test
		public void should_generate_correct_accessors_for_general_type()
		{
			// prepare
			final FieldSpecs _typicalField = new FieldSpecs_Builder()//
					.withDirectlyRequired(true)//
					.withFieldPrefix("my")//
					.withArrayMarker("")//
					.withNameForAccessor("TheField")//
					.withNameForField("TheField")//
					.withTypeInvocation("foo")//
					.withAnnotations(List.of())//
					.done();
			final ClassSpecs _classSpecs = new ClassSpecs_Builder()//
					.withFields(List.of(_typicalField))//
					.done();
			final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
			final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
					.withOptions(options)//
					.withClassSpecs(_classSpecs).done();

			// execute
			_generator.outputAccessors(_psh.getPrintStream());
			final List<String> _result = _psh.getLines();

			then(_result).containsExactly( //
					"    public foo getTheField() {return myTheField ;}", //
					"    public void setTheField(foo value) {myTheField = value;}", //
					"    ");

		}

		@Test
		public void should_generate_correct_accessors_for_primitive_boolean()
		{
			// prepare
			final FieldSpecs _primitiveBooleanField = new FieldSpecs_Builder()//
					.withDirectlyRequired(true)//
					.withFieldPrefix("my")//
					.withArrayMarker("")//
					.withNameForAccessor("TheField")//
					.withNameForField("TheField")//
					.withTypeInvocation("foo")//
					.withBooleanGetter(true)//
					.withAnnotations(new ArrayList<>(0))//
					.done();
			final ClassSpecs _classSpecs = new ClassSpecs_Builder()//
					.withFields(List.of(_primitiveBooleanField))//
					.done();
			final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
			final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
					.withOptions(options)//
					.withClassSpecs(_classSpecs).done();

			// execute
			_generator.outputAccessors(_psh.getPrintStream());
			final List<String> _result = _psh.getLines();

			then(_result).containsExactly( //
					"    public foo isTheField() {return myTheField ;}", //
					"    public void setTheField(foo value) {myTheField = value;}", //
					"    ");

		}

		@Test
		public void should_generate_annotation_on_targeted_accessors()
		{
			// prepare
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
					.withDirectlyRequired(true)//
					.withFieldPrefix("my")//
					.withArrayMarker("")//
					.withNameForAccessor("TheField")//
					.withNameForField("TheField")//
					.withTypeInvocation("foo")//
					.withBooleanGetter(false)//
					.withAnnotations(List.of(_annotationForGet, _annotationForSet))//
					.done();
			final ClassSpecs _classSpecs = new ClassSpecs_Builder()//
					.withFields(List.of(_typicalField))//
					.done();
			final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
			final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
					.withOptions(options)//
					.withClassSpecs(_classSpecs).done();

			// execute
			_generator.outputAccessors(_psh.getPrintStream());
			final List<String> _result = _psh.getLines();

			then(_result).containsExactly( //
					"    @my.annotations.ForGet(", //
					"        foo = \"the value\"", //
					"    )", //
					"    public foo getTheField() {return myTheField ;}", //
					"    @my.annotations.ForSet(", //
					"        foo = \"the value\"", //
					"    )", //
					"    public void setTheField(foo value) {myTheField = value;}", //
					"    ");

		}

		@Test
		public void should_generate_javadoc_when_available()
		{
			// prepare
			final FieldSpecs _primitiveBooleanField = new FieldSpecs_Builder()//
					.withDirectlyRequired(true)//
					.withFieldPrefix("my")//
					.withArrayMarker("")//
					.withNameForAccessor("TheField")//
					.withNameForField("TheField")//
					.withTypeInvocation("foo")//
					.withBooleanGetter(false)//
					.withAnnotations(List.of())//
					.withJavadocLines(new String[]
					{
							"short description of field", "", "other description"
					}).done();
			final ClassSpecs _classSpecs = new ClassSpecs_Builder()//
					.withFields(List.of(_primitiveBooleanField))//
					.done();
			final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
			final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
					.withOptions(options)//
					.withClassSpecs(_classSpecs).done();

			// execute
			_generator.outputAccessors(_psh.getPrintStream());
			final List<String> _result = _psh.getLines();

			then(_result).containsExactly( //
					"    /**", //
					"     * short description of field", //
					"     * ", //
					"     * other description", //
					"     * ", //
					"     * @returns the current value", //
					"     */", //
					"    public foo getTheField() {return myTheField ;}", //
					"    /**", //
					"     * short description of field", //
					"     * ", //
					"     * other description", //
					"     * ", //
					"     * @param value the new value", //
					"     */", //
					"    public void setTheField(foo value) {myTheField = value;}", //
					"    ");

		}
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testBooleanGetterPrefixShouldBeIsInsteadOfGet() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("my")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("TheField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(true)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors(_ps);
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo isTheField(");
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsEmpty() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("this.")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("theField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(false)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors(_ps);
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo getTheField(");
		then(_result).contains("public void setTheField(");
		then(_result).contains("{return this.theField ;}");
		then(_result).contains("{this.theField = value;}");
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsSpecified() throws UnsupportedEncodingException
	{
		// prepare
		final ClassSpecs specs = new ClassSpecs_Builder()//
				.withFields(Arrays.asList(new FieldSpecs_Builder()//
						.withDirectlyRequired(true)//
						.withFieldPrefix("my")//
						.withArrayMarker("")//
						.withNameForAccessor("TheField")//
						.withNameForField("TheField")//
						.withTypeInvocation("foo")//
						.withBooleanGetter(false)//
						.withAnnotations(new ArrayList<>(0))//
						.done()))//
				.done();

		final Charset _charset = StandardCharsets.UTF_8;
		final ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		final PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withClassSpecs(specs)//
				.done();
		// execute
		_generator.outputAccessors(_ps);
		final String _result = new String(_baos.toByteArray(), _charset);

		// verify
		then(_result).contains("public foo getTheField(");
		then(_result).contains("public void setTheField(");
		then(_result).contains("{return myTheField ;}");
		then(_result).contains("{myTheField = value;}");
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
		final BasicJavabeanGenerator _generator = new Builder<>(new BasicJavabeanGenerator())//
				.withOptions(options)//
				.withClassSpecs(_specs).done();

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
				"public class GreatClass", //
				"{", //
				"", //
				"    private foo nullTiti ;", //
				"", //
				"    /**", //
				"     * short description of field", //
				"     * ", //
				"     * other description", //
				"     */", //
				"    private foo nulltoto ;", //
				"    ", //
				"    ", //
				"    public foo getTiti() {return myTiti ;}", //
				"    public void setTiti(foo value) {myTiti = value;}", //
				"    ", //
				"    /**", //
				"     * short description of field", //
				"     * ", //
				"     * other description", //
				"     * ", //
				"     * @returns the current value", //
				"     */", //
				"    @my.annotations.ForGet(", //
				"        foo = \"the value\"", //
				"    )", //
				"    public foo gettoto() {return mytoto ;}", //
				"    /**", //
				"     * short description of field", //
				"     * ", //
				"     * other description", //
				"     * ", //
				"     * @param value the new value", //
				"     */", //
				"    @my.annotations.ForSet(", //
				"        foo = \"the value\"", //
				"    )", //
				"    public void settoto(foo value) {mytoto = value;}", //
				"    ", //
				"}");
	}
}
