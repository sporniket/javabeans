/**
 *
 */
package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Utility package for class names.
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

	public static String getSimpleName(String fullClassName)
	{
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}

	public static String getPackageName(String fullClassName)
	{
		return fullClassName.substring(0, fullClassName.lastIndexOf('.'));
	}

	public static String removeSuffixFromClassName(String name, String suffix)
	{
		return name.substring(0, name.length() - suffix.length());
	}

	public static void updateShortClassnameMappingFromClassnames(Map<String, String> mapping, Collection<String> registry)
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
