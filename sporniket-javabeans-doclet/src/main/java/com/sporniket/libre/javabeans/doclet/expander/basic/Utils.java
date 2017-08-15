/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.expander.basic;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.getPackageName;

import java.util.function.Predicate;

/**
 * Utility class.
 * @author dsporn
 *
 */
public class Utils
{
	public static final Predicate<? super String> IS_NOT_JAVA_LANG_TYPE = c -> !Object.class.getPackage().getName()
			.equals(getPackageName(c));

}
