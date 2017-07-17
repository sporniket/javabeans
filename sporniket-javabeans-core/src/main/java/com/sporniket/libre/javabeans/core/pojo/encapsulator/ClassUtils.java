/**
 *
 */
package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Utility package for {@link Class}.
 *
 * @author dsporn
 *
 */
public final class ClassUtils
{
	public static String computeOutputClassname(String classToOutput, Map<String, String> translations, Set<String> shortables)
	{
		final String _name = classToOutput;
		final String _translatedName = (translations.containsKey(_name)) ? translations.get(_name) : _name;
		final String _className = (shortables.contains(_translatedName)) ? getSimpleName(_translatedName) : _translatedName;

		return _className;
	}

	private static String getSimpleName(String fullClassName)
	{
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}

	public static String getPackageName(String fullClassName)
	{
		return fullClassName.substring(0, fullClassName.lastIndexOf('.'));
	}

	public static Map<String, String> getTranslationMapWhenPojosAreSuffixed(Set<Class<?>> registry, Set<String> sourcePackages,
			String pojoSuffix)
	{
		final Map<String, String> result = new HashMap<>(registry.size());
		final Predicate<Class<?>> _isPojo = c -> sourcePackages.contains(c.getPackage().getName())
				&& c.getSimpleName().endsWith(pojoSuffix);

		registry.stream().filter(_isPojo).forEach(c -> {
			result.put(c.getName(), removeSuffixFromClassName(c.getName(), pojoSuffix));
		});

		return result;
	}

	public static String removeSuffixFromClassName(String name, String suffix)
	{
		return name.substring(0, name.length() - suffix.length());
	}

	/**
	 * Add into a collection of 'known classes' the specified class, its superclass and implemented interfaces, and the type of its
	 * public fields.
	 *
	 * @param knownClasses
	 *            the collection to update.
	 * @param toScan
	 *            the class to scan.
	 */
	public static void updateKnownClasses(Set<Class<?>> knownClasses, Class<?> toScan)
	{
		final Predicate<? super Class<?>> _isNotRegistered = i -> !knownClasses.contains(i);
		final Consumer<? super Class<?>> _registerKnownClass = i -> knownClasses.add(i); // do not support parametrized types

		knownClasses.add(toScan);
		knownClasses.add(toScan.getSuperclass());

		Arrays.asList(toScan.getInterfaces()).stream().filter(_isNotRegistered).forEach(_registerKnownClass);
		FieldUtils.getPublicFields(toScan).stream().map(Field::getType).filter(_isNotRegistered).forEach(_registerKnownClass);
	}

	public static void updateShortClassnameMappingFromClasses(Map<String, String> mapping, Collection<Class<?>> registry)
	{
		registry.forEach(c -> {
			final String _simpleName = c.getSimpleName();
			if (!mapping.containsKey(_simpleName))
			{
				mapping.put(_simpleName, c.getName());

			}
		});
	}

	public static void updateShortClassnameMappingFromStrings(Map<String, String> mapping, Collection<String> registry)
	{
		registry.forEach(c -> {
			final String _simpleName = getSimpleName(c);
			if (!mapping.containsKey(_simpleName))
			{
				mapping.put(_simpleName, c);

			}
		});
	}

}
