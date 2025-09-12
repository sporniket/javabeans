package com.sporniket.libre.javabeans.models.consumers.javasource;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.models.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.AnnotationSpecs_Builder;
import com.sporniket.libre.javabeans.models.ClassSpecs;
import com.sporniket.libre.javabeans.models.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.models.FieldSpecs;
import com.sporniket.libre.javabeans.models.FieldSpecs_Builder;
import com.sporniket.libre.javabeans.models.ImportSpecs_Builder;
import com.sporniket.libre.javabeans.models.consumers.javasource.BasicBuilderGenerator;
import com.sporniket.libre.javabeans.models.consumers.javasource.Builder;
import com.sporniket.libre.javabeans.models.consumers.javasource.Configuration;

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
final class BasicBuilderGeneratorTest
{
	Configuration myOptions = new Configuration();

	@BeforeEach
	void setupOptions()
	{
		myOptions.setBuilderSuffix("_Builder");
	}

	@Test
	public void should_generate_javabean_builder_source_code()
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
				.withOptions(myOptions) //
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
				" * Fluent builder/wrapper for GreatClass", //
				" * ", //
				" * @see GreatClass", //
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

	@Test
	void should_generate_javabean_builder_for_concrete_javabean_with_all_the_features()
	{
		// prepare
		// -- fields
		final FieldSpecs _minimalField = FieldSpecsFixtures.setupMinimalField("foo", "", "tata", "Tata").done();

		// -- javadoc
		final String[] _javadocLinesClass = new String[]
		{
				"A very usefull class."
		};

		// -- annotations
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotationForClass = new AnnotationSpecs_Builder()//
				.withType("my.annotations.ForClass") //
				.withParameters(List.of(_parameter)) //
				.done();

		// -- class
		final ClassSpecs _specs = new ClassSpecs_Builder() //
				.withPackageName("my.great.package") //
				.withImports(List.of()) //
				.withAnnotations(List.of(_annotationForClass)) //
				.withJavadocLines(_javadocLinesClass)//
				.withClassName("GreatClass") //
				.withDeclaredTypeArguments("<DeclaredTypeArgument>") //
				.withInvokedTypeArguments("<InvokedTypeArgument>") //
				.withSuperClassName("SuperClass") //
				.withInterfaceList("a, b, c") //
				.withFields(List.of(_minimalField)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
		final BasicBuilderGenerator _generator = new Builder<>(new BasicBuilderGenerator()) //
				.withOptions(myOptions) //
				.withClassSpecs(_specs) //
				.done();

		// execute
		_generator.generate(_psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		then(_result).containsExactly( //
				"package my.great.package;", //
				"", //
				"", //
				"/**", //
				" * Fluent builder/wrapper for GreatClass", //
				" * ", //
				" * @see GreatClass", //
				" */", //
				"public class GreatClass_Builder<DeclaredTypeArgument>", //
				"{", //
				"    private final GreatClass<InvokedTypeArgument> bean ;", //
				"", //
				"    public GreatClass<InvokedTypeArgument> done() {return bean ;}", //
				"", //
				"    /**", //
				"     * Default constructor.", //
				"     */", //
				"    public GreatClass_Builder() {bean = new GreatClass<InvokedTypeArgument>() ;}", //
				"", //
				"    /**", //
				"     * Constructor that delegates the bean instanciation.", //
				"     * @param newBean the instanciated bean to use.", //
				"     */", //
				"    public GreatClass_Builder(GreatClass<InvokedTypeArgument> newBean) {bean = newBean ;}", //
				"", //
				"    public GreatClass_Builder<InvokedTypeArgument> withTata(foo value) {bean.setTata(value); return this;}", //
			    "}");
	}

	@Test
	void should_omit_default_constructor_for_abstract_javabean()
	{
		// prepare
		// -- fields
		final FieldSpecs _minimalField = FieldSpecsFixtures.setupMinimalField("foo", "", "tata", "Tata").done();

		// -- javadoc
		final String[] _javadocLinesClass = new String[]
		{
				"A very usefull class."
		};

		// -- annotations
		final AnnotationParameterSpecsSingleValue _parameter = new AnnotationParameterSpecsSingleValue_Builder() //
				.withName("foo") //
				.withValue("the value") //
				.withString(true) //
				.done();
		final AnnotationSpecs _annotationForClass = new AnnotationSpecs_Builder()//
				.withType("my.annotations.ForClass") //
				.withParameters(List.of(_parameter)) //
				.done();

		// -- class
		final ClassSpecs _specs = new ClassSpecs_Builder() //
				.withAbstractRequired(true) //
				.withPackageName("my.great.package") //
				.withImports(List.of()) //
				.withAnnotations(List.of(_annotationForClass)) //
				.withJavadocLines(_javadocLinesClass)//
				.withClassName("GreatClass") //
				.withDeclaredTypeArguments("<DeclaredTypeArgument>") //
				.withInvokedTypeArguments("<InvokedTypeArgument>") //
				.withFields(List.of(_minimalField)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
		final BasicBuilderGenerator _generator = new Builder<>(new BasicBuilderGenerator()) //
				.withOptions(myOptions) //
				.withClassSpecs(_specs) //
				.done();

		// execute
		_generator.generate(_psh.getPrintStream());
		final List<String> _result = _psh.getLines();

		then(_result).containsExactly( //
				"package my.great.package;", //
				"", //
				"", //
				"/**", //
				" * Fluent builder/wrapper for GreatClass", //
				" * ", //
				" * @see GreatClass", //
				" */", //
				"public class GreatClass_Builder<DeclaredTypeArgument>", //
				"{", //
				"    private final GreatClass<InvokedTypeArgument> bean ;", //
				"", //
				"    public GreatClass<InvokedTypeArgument> done() {return bean ;}", //
				"", //
				"    /**", //
				"     * Constructor that delegates the bean instanciation.", //
				"     * @param newBean the instanciated bean to use.", //
				"     */", //
				"    public GreatClass_Builder(GreatClass<InvokedTypeArgument> newBean) {bean = newBean ;}", //
				"", //
				"    public GreatClass_Builder<InvokedTypeArgument> withTata(foo value) {bean.setTata(value); return this;}", //
				"}");
	}
}
