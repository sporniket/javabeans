package com.sporniket.libre.javabeans.processors.generators.javasource;

import static java.util.Map.entry;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		@Nested
		class WithoutTranslations
		{
			@Test
			void should_return_qualified_name_when_input_is_not_in_shortables()
			{
				final String _input = "foo.bar.Whatever";
				// verify input requirements
				then(theShortables).doesNotContain(_input);

				// execute and verify
				then(UtilsClassname.computeOutputClassname(_input, theShortables)).isEqualTo(_input);
			}

			@Test
			void should_return_simple_name_when_input_is_in_shortables()
			{
				final String _input = "foo.bar.Sample";
				// verify input requirements
				then(theShortables).contains(_input);

				// execute and verify
				then(UtilsClassname.computeOutputClassname(_input, theShortables)).isEqualTo("Sample");
			}
		}

		@Nested
		class WithTranslations
		{
			@Nested
			class WhenInputNameHasNoTranslation
			{
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
			}

			@Nested
			class WhenInputNameHasTranslation
			{
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
			}

			private static final Map<String, String> theTranslations = Map.of("foo.bar.WhateverRaw", "foo.bar.Sample",
					"foo.bar.AnotherRaw", "foo.bar.NotShortable");
		}

		private static final Set<String> theShortables = Set.of("foo.bar.Sample");
	}

	@Nested
	class Describle__getPackageName
	{
		@Test
		public void should_return_empty_package_name_when_class_is_in_default_package()
		{
			// execute and verify
			then(UtilsClassname.getPackageName("MyClass")).isEqualTo("");
		}

		@Test
		public void should_return_package_name_from_fully_qualified_class_name()
		{
			// execute and verify
			then(UtilsClassname.getPackageName("foo.bar.MyClass")).isEqualTo("foo.bar");
		}
	}

	@Nested
	class Describe__getReverseTranslationMapWhenPojoAreSuffixed
	{
		private final Set<String> myRegistry = Set.of( //
				"foo.bar.Bar", "foo.bar.BarBuilder", //
				"foo.Truck", "foo.BearRaw", //
				"foo.bar.Raw", "foo.bar.Builder", //
				"foo.bar.sampleRaw", //
				"foo.bar.bir.BirRaw", "foo.bar.bir.Car", //
				"foo.bar.bor.BorRaw", "foo.bar.bor.Peble");

		private final Set<String> mySourcePackages = Set.of("foo.bar", "foo.bar.bir");

		@Test
		public void should_return_pojo_names_by_bean_names_map()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getReverseTranslationMapWhenPojosAreSuffixed(myRegistry,
					mySourcePackages, "Raw", "Builder");

			// verify
			then(_translationMap) //
					.hasSize(4) //
					.contains( //
							entry("foo.bar.Bar", "foo.bar.BarRaw"), //
							entry("foo.bar.Raw", "foo.bar.RawRaw"), //
							entry("foo.bar.Builder", "foo.bar.BuilderRaw"), //
							entry("foo.bar.bir.Car", "foo.bar.bir.CarRaw") //
					);
		}

		@Test
		public void should_not_filter_input_classes_when_source_packages_is_null()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getReverseTranslationMapWhenPojosAreSuffixed(myRegistry,
					null, "Raw", "Builder");

			// verify
			then(_translationMap) //
					.hasSize(6) //
					.contains( //
							entry("foo.bar.Bar", "foo.bar.BarRaw"), //
							entry("foo.Truck", "foo.TruckRaw"), //
							entry("foo.bar.Raw", "foo.bar.RawRaw"), //
							entry("foo.bar.Builder", "foo.bar.BuilderRaw"), //
							entry("foo.bar.bir.Car", "foo.bar.bir.CarRaw"), //
							entry("foo.bar.bor.Peble", "foo.bar.bor.PebleRaw") //
					);
		}

		@Test
		public void should_not_filter_input_classes_when_source_packages_is_empty()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getReverseTranslationMapWhenPojosAreSuffixed(myRegistry,
					Set.of(), "Raw", "Builder");

			// verify
			then(_translationMap) //
					.hasSize(6) //
					.contains( //
							entry("foo.bar.Bar", "foo.bar.BarRaw"), //
							entry("foo.Truck", "foo.TruckRaw"), //
							entry("foo.bar.Raw", "foo.bar.RawRaw"), //
							entry("foo.bar.Builder", "foo.bar.BuilderRaw"), //
							entry("foo.bar.bir.Car", "foo.bar.bir.CarRaw"), //
							entry("foo.bar.bor.Peble", "foo.bar.bor.PebleRaw") //
					);
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
		private final Set<String> myRegistry = Set.of("foo.bar.Bar", "foo.BearRaw", "foo.bar.Raw", "foo.bar.sampleRaw",
				"foo.bar.bir.BirRaw", "foo.bar.bor.BorRaw");

		private final Set<String> mySourcePackages = Set.of("foo.bar", "foo.bar.bir");

		@Test
		public void should_create_translation_map_of_pojo_by_suffixed_names()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(myRegistry,
					mySourcePackages, "Raw");

			// verify
			then(_translationMap).hasSize(2);
			then(_translationMap).contains(entry("foo.bar.sampleRaw", "foo.bar.sample"),
					entry("foo.bar.bir.BirRaw", "foo.bar.bir.Bir"));
		}

		@Test
		public void should_process_all_classes_when_package_list_is_empty()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(myRegistry, Set.of(),
					"Raw");

			// verify
			then(_translationMap).hasSize(4);
			then(_translationMap).contains( //
					entry("foo.BearRaw", "foo.Bear"), //
					entry("foo.bar.sampleRaw", "foo.bar.sample"), //
					entry("foo.bar.bir.BirRaw", "foo.bar.bir.Bir"), //
					entry("foo.bar.bor.BorRaw", "foo.bar.bor.Bor") //
			);

		}

		@Test
		public void should_process_all_classes_when_package_list_is_null()
		{
			// execute
			final Map<String, String> _translationMap = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(myRegistry, null,
					"Raw");

			// verify
			then(_translationMap).hasSize(4);
			then(_translationMap).contains( //
					entry("foo.BearRaw", "foo.Bear"), //
					entry("foo.bar.sampleRaw", "foo.bar.sample"), //
					entry("foo.bar.bir.BirRaw", "foo.bar.bir.Bir"), //
					entry("foo.bar.bor.BorRaw", "foo.bar.bor.Bor") //
			);

		}

	}

	@Nested
	class Describe__removeSuffixFromClassName
	{
		@Test
		public void should_remove_suffix_from_class_name()
		{
			// execute and verify
			then(UtilsClassname.removeSuffixFromClassName("foobar", "bar")).isEqualTo("foo");
		}
	}

	@Nested
	class Describe__updateShortClassnameMappingFromClassnames
	{
		private final List<String> myRegistry = List.of("foo.bar.bar", "foo.foo.bar", "foo.fee");

		@Test
		public void should_update_class_name_mapping_by_short_name()
		{
			// prepare
			final Map<String, String> _mapping = new HashMap<>(myRegistry.size());

			// execute
			UtilsClassname.updateShortClassnameMappingFromClassnames(_mapping, myRegistry);

			// verify
			then(_mapping).hasSize(2);
			then(_mapping.get("bar")).isEqualTo("foo.bar.bar");
			then(_mapping.get("fee")).isEqualTo("foo.fee");
		}
	}

}
