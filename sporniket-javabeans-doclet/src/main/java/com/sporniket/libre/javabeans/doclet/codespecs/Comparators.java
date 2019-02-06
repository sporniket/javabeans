/**
 * 
 */
package com.sporniket.libre.javabeans.doclet.codespecs;

import static com.sporniket.strings.StringComparators.STRING_COMPARATOR_NATURAL;
import static com.sporniket.strings.StringComparators.STRING_COMPARATOR_REVERSE;
import static java.util.Comparator.nullsFirst;

import java.util.Comparator;

/**
 * Builtin comparators for the model.
 * 
 * @author dsporn
 *
 */
public final class Comparators
{
	public final static Comparator<ImportSpecs> IMPORT_SPECS_COMPARATOR_NATURAL = nullsFirst(
			(is1, is2) -> STRING_COMPARATOR_NATURAL.compare(is1.getClassName(), is2.getClassName()));//

	public final static Comparator<ImportSpecs> IMPORT_SPECS_COMPARATOR_REVERSE = nullsFirst(
			(is1, is2) -> STRING_COMPARATOR_REVERSE.compare(is1.getClassName(), is2.getClassName()));//
}
