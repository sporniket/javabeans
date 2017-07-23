package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class for {@link Field}.
 *
 * @author dsporn
 *
 */
public final class FieldUtils
{
	private static Predicate<Field> IS_PUBLIC_FIELD = f -> 0 != (f.getModifiers() & Modifier.PUBLIC);

	/**
	 * Capitalize the first letter of the field name.
	 *
	 * @param fieldName
	 *            the field name to transform.
	 * @return the transformed field name, e.g. <code>fooBar</code> will give <code>FooBar</code>.
	 */
	public static String computeFieldAccessorSuffix(String fieldName)
	{
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * Get the declared public fields of a given class, inherited fields are excluded.
	 *
	 * @param toScan
	 * @return
	 */
	public static List<Field> getPublicDeclaredFields(Class<?> toScan)
	{
		return Arrays.asList(toScan.getDeclaredFields()).stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList());
	}

	/**
	 * Get the public fields of a given class, inherited fields are included.
	 *
	 * @param toScan
	 * @return
	 */
	public static List<Field> getPublicFields(Class<?> toScan)
	{
		return Arrays.asList(toScan.getFields()).stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList());
	}

}
