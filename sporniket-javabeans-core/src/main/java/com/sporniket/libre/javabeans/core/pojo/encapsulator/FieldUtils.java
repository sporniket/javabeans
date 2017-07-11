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
	static Predicate<Field> IS_PUBLIC_FIELD = f -> 0 != (f.getModifiers() & Modifier.PUBLIC);

	public static List<Field> getPublicFields(Class<?> toScan)
	{
		return Arrays.asList(toScan.getDeclaredFields()).stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList());
	}
}
