package com.sporniket.libre.javabeans.doclet.expander;

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
public final class UtilsFieldDoc
{
	private static Predicate<FieldDoc> IS_ACCESSIBLE_FIELD = f -> f.isPublic() || f.isPackagePrivate();

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
	public static List<FieldDoc> getAccessibleDeclaredFields(ClassDoc toScan)
	{
		return Arrays.asList(toScan.fields()).stream().filter(IS_ACCESSIBLE_FIELD).collect(Collectors.toList());
	}

	/**
	 * Get the public fields of a given class, inherited fields are included.
	 *
	 * @param toScan
	 * @return
	 */
	public static List<FieldDoc> getAccessibleFields(ClassDoc toScan)
	{
		List<FieldDoc> _buffer = new ArrayList<>();
		collectPublicFieldsInto(_buffer, toScan);
		return new ArrayList<>(_buffer);
	}
	
	private static void collectPublicFieldsInto(List<FieldDoc> buffer, ClassDoc toScan)
	{
		buffer.addAll(Arrays.asList(toScan.fields()).stream().filter(IS_ACCESSIBLE_FIELD).collect(Collectors.toList())) ;
		if(!Object.class.getName().equals(toScan.qualifiedName()))
		{
			collectPublicFieldsInto(buffer, toScan.superclass());
		}
	}

}
