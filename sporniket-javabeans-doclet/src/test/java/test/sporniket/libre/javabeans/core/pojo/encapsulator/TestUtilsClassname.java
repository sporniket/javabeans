package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassname;

public class TestUtilsClassname
{
	@Test
	public void test__computeOutputClassname()
	{
		// prepare
		Set<String> _shortables = new HashSet<>(asList("foo.bar.Sample"));
		Map<String, String> _translations = new HashMap<>(1);
		_translations.put("foo.bar.WhateverRaw", "foo.bar.Sample");
		// execute
		String _toTestShortable = UtilsClassname.computeOutputClassname("foo.bar.WhateverRaw", _translations, _shortables);
		String _toTestUnshortable = UtilsClassname.computeOutputClassname("foo.bar.WhoCaresRaw", _translations, _shortables);
		// verify
		assertThat(_toTestShortable, is("Sample"));
		assertThat(_toTestUnshortable, is("foo.bar.WhoCaresRaw"));
	}

	@Test
	public void test__computeOutputClassname__noTranslations()
	{
		// prepare
		Set<String> _shortables = new HashSet<>(asList("foo.bar.Sample"));
		// execute
		String _toTestShortable = UtilsClassname.computeOutputClassname("foo.bar.Sample", _shortables);
		String _toTestUnshortable = UtilsClassname.computeOutputClassname("foo.bar.Whatever", _shortables);
		// verify
		assertThat(_toTestShortable, is("Sample"));
		assertThat(_toTestUnshortable, is("foo.bar.Whatever"));
	}

	@Test
	public void test__getPackageName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getPackageName(Object.class.getName());
		// verify
		assertThat(_toTest, is(Object.class.getPackage().getName()));
	}

	@Test
	public void test__getSimpleName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getSimpleName(Object.class.getName());
		// verify
		assertThat(_toTest, is(Object.class.getSimpleName()));
	}

	@Test
	public void test__getTranslationMapWhenPojosAreSuffixed()
	{
		// prepare
		Set<String> _registry = new HashSet<>(asList("foo.bar.Bar", "foo.BearRaw", "foo.bar.Raw", "foo.bar.sampleRaw"));
		Set<String> _sourcePackages = new HashSet<>(asList("foo.bar", "foo.bar.bir"));
		// execute
		Map<String, String> _toTest = UtilsClassname.getTranslationMapWhenPojosAreSuffixed(_registry, _sourcePackages, "Raw");
		// verify
		assertThat(_toTest.size(), is(1));
		assertThat(_toTest.get("foo.bar.sampleRaw"), is("foo.bar.sample"));
	}

	@Test
	public void test__removeSuffixFromClassName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.removeSuffixFromClassName("foobar", "bar");
		// verify
		assertThat(_toTest, is("foo"));
	}

	@Test
	public void test__updateShortClassnameMappingFromClassnames()
	{
		// prepare
		List<String> _registry = Arrays.asList("foo.bar.bar", "foo.foo.bar", "foo.fee");
		Map<String, String> _mapping = new HashMap<>(_registry.size());
		// execute
		UtilsClassname.updateShortClassnameMappingFromClassnames(_mapping, _registry);
		// verify
		assertThat(_mapping.size(), is(2));
		assertThat(_mapping.get("bar"), is("foo.bar.bar"));
		assertThat(_mapping.get("fee"), is("foo.fee"));
	}
}
