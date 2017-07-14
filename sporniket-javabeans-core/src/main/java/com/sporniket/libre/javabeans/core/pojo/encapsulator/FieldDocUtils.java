package com.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

/**
 * Utility class for {@link FieldDoc}.
 *
 * @author dsporn
 *
 */
public final class FieldDocUtils
{
	private static Predicate<FieldDoc> IS_PUBLIC_FIELD = f -> f.isPublic();

	/**
	 * Capitalize the first letter of the field name.
	 *
	 * @param field
	 *            the field name to transform.
	 * @return the transformed field name, e.g. <code>fooBar</code> will give <code>FooBar</code>.
	 */
	public static String computeFieldAccessorSuffix(FieldDoc field)
	{
		return field.name().substring(0, 1).toUpperCase() + field.name().substring(1);
	}

	/**
	 * Get the declared public fields of a given class, inherited fields are excluded.
	 *
	 * @param toScan
	 * @return
	 */
	public static List<FieldDoc> getPublicDeclaredFields(ClassDoc toScan)
	{
		return Arrays.asList(toScan.fields()).stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList());
	}

	/**
	 * Get the public fields of a given class, inherited fields are included.
	 *
	 * @param toScan
	 * @return
	 */
	public static List<FieldDoc> getPublicFields(ClassDoc toScan)
	{
		List<FieldDoc> _buffer = new ArrayList<>();
		collectPublicFieldsInto(_buffer, toScan);
		return new ArrayList<>(_buffer);
	}
	
	private static void collectPublicFieldsInto(List<FieldDoc> buffer, ClassDoc toScan)
	{
		if(!Object.class.getName().equals(toScan.qualifiedName()))
		{
			buffer.addAll(Arrays.asList(toScan.fields()).stream().filter(IS_PUBLIC_FIELD).collect(Collectors.toList())) ;
			collectPublicFieldsInto(buffer, toScan.superclass());
		}
	}

}