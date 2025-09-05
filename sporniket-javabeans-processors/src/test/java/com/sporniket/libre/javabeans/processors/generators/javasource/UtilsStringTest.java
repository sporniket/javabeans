package com.sporniket.libre.javabeans.processors.generators.javasource;

import static com.sporniket.libre.javabeans.processors.generators.javasource.UtilsString.TransformationFactories.buildPrefixRemover;
import static com.sporniket.libre.javabeans.processors.generators.javasource.UtilsString.Transformations.CAPITALIZER;
import static com.sporniket.libre.javabeans.processors.generators.javasource.UtilsString.Transformations.UNCAPITALIZER;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sporniket.strings.pipeline.StringTransformation;

public class UtilsStringTest
{
	@Nested
	class Describe_Transformations_Capitalizer
	{
		@Test
		void should_capitalize_input()
		{
			then(CAPITALIZER.transform("foo")).isEqualTo("Foo");
			then(CAPITALIZER.transform("Foo")).isEqualTo("Foo");
			then(CAPITALIZER.transform("FOO")).isEqualTo("FOO");
		}
	}

	@Nested
	class Describe_Transformations_Uncapitalizer
	{
		@Test
		void should_uncapitalize_input()
		{
			then(UNCAPITALIZER.transform("foo")).isEqualTo("foo");
			then(UNCAPITALIZER.transform("Foo")).isEqualTo("foo");
			then(UNCAPITALIZER.transform("FOO")).isEqualTo("fOO");
		}
	}

	@Nested
	class Describe_TransformationsFactories_buildPrefixRemover
	{
		@Test
		void should_create_transformer_that_removes_given_prefix()
		{
			final StringTransformation prefixRemover = buildPrefixRemover("foo");

			then(prefixRemover.transform("fooBar")).isEqualTo("Bar");
			then(prefixRemover.transform("foo")).isEqualTo("foo");
			then(prefixRemover.transform("FooBar")).isEqualTo("FooBar");
			then(prefixRemover.transform("fOoBar")).isEqualTo("fOoBar");
			then(prefixRemover.transform("foOBar")).isEqualTo("foOBar");
			then(prefixRemover.transform(null)).isEqualTo(null);
		}

		@Test
		void should_create_no_operation_prefix_remover_when_prefix_is_empty()
		{
			then(buildPrefixRemover("").transform("fooBar")).isEqualTo("fooBar");
			then(buildPrefixRemover(null).transform("fooBar")).isEqualTo("fooBar");
		}
	}

}
