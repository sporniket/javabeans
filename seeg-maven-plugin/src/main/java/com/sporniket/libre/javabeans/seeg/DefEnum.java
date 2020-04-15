package com.sporniket.libre.javabeans.seeg;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal representation of an enum, will be converted to a Java enum.
 * 
 * @author dsporn
 *
 */
public class DefEnum extends Def
{
	public final List<String> values = new ArrayList<String>(20);
}
