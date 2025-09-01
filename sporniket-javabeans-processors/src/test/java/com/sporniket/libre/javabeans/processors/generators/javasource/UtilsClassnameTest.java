package com.sporniket.libre.javabeans.processors.generators.javasource;

import static java.util.Arrays.asList;
import static java.util.Map.entry;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
public class UtilsClassnameTest
{
	@Nested
	class Describe__computeOutputClassname
	{
		static Set<String> theShortables = Set.of("foo.bar.Sample");

		@Nested
		class WithoutTranslations
		{
			@Test
			void should_return_simple_name_when_input_is_in_shortables()
			{
				final String _input = "foo.bar.Sample";
				// verify input requirements
				then(theShortables).contains(_input);

				// execute and verify
				then(UtilsClassname.computeOutputClassname(_input, theShortables)).isEqualTo("Sample");
			}

			@Test
			void should_return_qualified_name_when_input_is_not_in_shortables()
			{
				final String _input = "foo.bar.Whatever";
				// verify input requirements
				then(theShortables).doesNotContain(_input);

				// execute and verify
				then(UtilsClassname.computeOutputClassname(_input, theShortables)).isEqualTo(_input);
			}
		}

		@Nested
		class WithTranslations
		{
			Map<String, String> theTranslations = Map.of("foo.bar.WhateverRaw", "foo.bar.Sample", "foo.bar.AnotherRaw",
					"foo.bar.NotShortable");

			@Nested
			class WhenInputNameHasNoTranslation
			{
				@Test
				void should_return_simple_untranslated_name_when_untranslated_input_is_in_shortables()
				{
					final String _input = "foo.bar.Sample";
					// verify input requirements
					then(theTranslations).doesNotContainKey(_input);
					then(theShortables).contains(_input);

					// execute and verify
					then(UtilsClassname.computeOutputClassname(_input, theTranslations, theShortables)).isEqualTo("Sample");
				}

				@Test
				void should_return_qualified_untranslated_name_when_untranslated_input_is_not_in_shortables()
				{
					final String _input = "foo.bar.AnotherUntranslatable";
					// verify input requirements
					then(theTranslations).doesNotContainKey(_input);
					then(theShortables).doesNotContain(_input);

					// execute and verify
					then(UtilsClassname.computeOutputClassname(_input, theTranslations, theShortables))
							.isEqualTo("foo.bar.AnotherUntranslatable");
				}
			}

			@Nested
			class WhenInputNameHasTranslation
			{
				@Test
				void should_return_simple_translated_name_when_translated_input_is_in_shortables()
				{
					final String _input = "foo.bar.WhateverRaw";
					// verify input requirements
					then(theTranslations).contains(entry(_input, "foo.bar.Sample"));
					then(theShortables).contains(theTranslations.get(_input));

					// execute and verify
					then(UtilsClassname.computeOutputClassname(_input, theTranslations, theShortables)).isEqualTo("Sample");
				}

				@Test
				void should_return_qualified_translated_name_when_translated_input_is_not_in_shortables()
				{
					final String _input = "foo.bar.AnotherRaw";
					final String _translation = "foo.bar.NotShortable";
					// verify input requirements
					then(theTranslations).contains(entry(_input, _translation));
					then(theShortables).doesNotContain(theTranslations.get(_input));

					// execute and verify
					then(UtilsClassname.computeOutputClassname(_input, theTranslations, theShortables)).isEqualTo(_translation);
				}
			}
		}
	}

	@Nested
	class Describle__getPackageName
	{
		@Test
		public void should_return_package_name_from_fully_qualified_class_name()
		{
			// execute and verify
			then(UtilsClassname.getPackageName("foo.bar.MyClass")).isEqualTo("foo.bar");
		}

		@Test
		public void should_return_empty_package_name_when_class_is_in_default_package()
		{
			// execute and verify
			then(UtilsClassname.getPackageName("MyClass")).isEqualTo("");
		}
	}

	@Nested
	class Describle__getSimpleName
	{
		@Test
		public void should_return_the_simple_name_of_fully_qualified_class_name()
		{
			// execute and verify
			then(UtilsClassname.getSimpleName("foo.bar.MyClass")).isEqualTo("MyClass");
			then(UtilsClassname.getSimpleName("MyClass")).isEqualTo("MyClass");
		}
	}

	@Nested
	class Describle__getTranslationMapWhenPojosAreSuffixed
	{
		@Test
		public void should_create_translation_map_of_pojo_by_suffixed_names()
		{
			// prepare
			Set<String> _registry = new HashSet<>(asList("foo.bar.Bar", "foo.BearRaw", "foo.bar.Raw", "foo.bar.sampleRaw"));
			Set<String> _sourcePackages = new HashSet<>(asList("foo.bar", "foo.bar.bir"));
			// execute
			Map<String, String> _toTest = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(_registry, _sourcePackages, "Raw");
			// verify
			then(_toTest).hasSize(1);
			then(_toTest.get("foo.bar.sampleRaw")).isEqualTo("foo.bar.sample");
		}

	}

	@Nested
	class Describe__removeSuffixFromClassName
	{
		@Test
		public void should_remove_suffix_from_class_name()
		{
			// prepare
			// execute
			String _toTest = UtilsClassname.removeSuffixFromClassName("foobar", "bar");
			// verify
			then(_toTest).isEqualTo("foo");
		}
	}

	@Nested
	class Describe__updateShortClassnameMappingFromClassnames
	{
		@Test
		public void should_update_class_name_mapping_by_short_name()
		{
			// prepare
			List<String> _registry = Arrays.asList("foo.bar.bar", "foo.foo.bar", "foo.fee");
			Map<String, String> _mapping = new HashMap<>(_registry.size());
			// execute
			UtilsClassname.updateShortClassnameMappingFromClassnames(_mapping, _registry);
			// verify
			then(_mapping).hasSize(2);
			then(_mapping.get("bar")).isEqualTo("foo.bar.bar");
			then(_mapping.get("fee")).isEqualTo("foo.fee");
		}
	}

}
