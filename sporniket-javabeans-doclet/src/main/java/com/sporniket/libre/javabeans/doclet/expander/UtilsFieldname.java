package com.sporniket.libre.javabeans.doclet.encapsulator;

/**
 * Utility class for Field names.
 *
 * @author dsporn
 *
 */
public final class UtilsFieldname
{
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

}
