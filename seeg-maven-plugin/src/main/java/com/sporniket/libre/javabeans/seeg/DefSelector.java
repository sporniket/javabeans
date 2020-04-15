package com.sporniket.libre.javabeans.seeg;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Internal representation of a selector, will be converted to a query method.
 * 
 * @author dsporn
 *
 */
public class DefSelector extends Def
{
	boolean unique;

	Set<String> columns = new LinkedHashSet<String>(10);
}
