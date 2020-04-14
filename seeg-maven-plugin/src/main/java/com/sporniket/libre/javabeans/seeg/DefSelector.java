package com.sporniket.libre.javabeans.seeg;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefSelector extends Def
{
	boolean unique;

	Set<String> columns = new LinkedHashSet<String>(10);
}
