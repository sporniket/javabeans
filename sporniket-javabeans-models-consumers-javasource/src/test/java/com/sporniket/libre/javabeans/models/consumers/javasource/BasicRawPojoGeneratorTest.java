package com.sporniket.libre.javabeans.models.consumers.javasource;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue;
import com.sporniket.libre.javabeans.models.AnnotationParameterSpecsSingleValue_Builder;
import com.sporniket.libre.javabeans.models.AnnotationSpecs;
import com.sporniket.libre.javabeans.models.AnnotationSpecs_Builder;
import com.sporniket.libre.javabeans.models.ClassSpecs;
import com.sporniket.libre.javabeans.models.ClassSpecs_Builder;
import com.sporniket.libre.javabeans.models.FieldSpecs;
import com.sporniket.libre.javabeans.models.ImportSpecs_Builder;
import com.sporniket.libre.javabeans.models.consumers.javasource.BasicRawPojoGenerator;
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
final class BasicRawPojoGeneratorTest
{
	Configuration myOptions = new Configuration();

	@Test
	public void should_generate_raw_pojo_source_code()
	{
		// prepare
		// -- fields
		final FieldSpecs _minimalField = FieldSpecsFixtures.setupMinimalField("foo", "", "tata", "Tata").done();
		final FieldSpecs _forceThisField = FieldSpecsFixtures.setupMinimalField("foo", "", "value", "Value").done();
		final FieldSpecs _primitiveBooleanField = FieldSpecsFixtures.setupBooleanField("foo", "my", "Titi", "Titi").done();
		final FieldSpecs _arrayField = FieldSpecsFixtures.setupArrayField("foo", "my", "Tete", "Tete").done();

		// -- javadoc
		final String[] _javadocLines = new String[]
		{
				"short description of field", "", "other description"
		};
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
		final AnnotationSpecs _annotationForField = new AnnotationSpecs_Builder()//
				.withOnField(true)//
				.withType("my.annotations.ForField") //
				.withParameters(List.of(_parameter)) //
				.done();
		final FieldSpecs _allTheFeaturesField = FieldSpecsFixtures.setupMinimalField("foo", "my", "toto", "toto")//
				.withDirectlyRequired(true) //
				.withAnnotations(List.of(_annotationForGet, _annotationForSet, _annotationForField)) //
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
				.withClassName("GreatClassRaw") //
				.withFields(List.of(_minimalField, _forceThisField, _arrayField, _primitiveBooleanField, _allTheFeaturesField)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
		final BasicRawPojoGenerator _generator = new Builder<>(new BasicRawPojoGenerator()) //
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
				" * A very usefull class.", //
				" */", //
				"class GreatClassRaw", //
				"{", //
				"    foo tata ;", //
				"    ", //
				"    foo value ;", //
				"    ", //
				"    foo[] myTete ;", //
				"    ", //
				"    foo myTiti ;", //
				"    ", //
				"    /**", //
				"     * short description of field", //
				"     * ", //
				"     * other description", //
				"     */", //
				"    @my.annotations.ForField(", //
				"        foo = \"the value\"", //
				"    )", //
				"    foo mytoto ;", //
				"    ", //
				"}");
	}

	@Test
	void should_generate_class_with_all_the_features()
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
				.withSuperClassName("SuperClass") //
				.withInterfaceList("a, b, c") //
				.withFields(List.of(_minimalField)) //
				.done();

		// --
		final InMemoryPrintStreamHelper _psh = new InMemoryPrintStreamHelper();
		final BasicRawPojoGenerator _generator = new Builder<>(new BasicRawPojoGenerator()) //
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
				" * A very usefull class.", //
				" */", //
				"@my.annotations.ForClass(", //
				"    foo = \"the value\"", //
				")", //
				"abstract class GreatClass<DeclaredTypeArgument>", //
				"        extends SuperClass", //
				"        implements a, b, c", //
				"{", //
				"    foo tata ;", //
				"    ", //
				"}");
	}
}
