package com.sporniket.libre.javabeans.seeg;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DefClass extends Def
{

	public final Map<String, DefColumn> columns = new HashMap<String, DefColumn>();

	public final Set<String> pkeysColumns = new LinkedHashSet<>(10);

	public final Map<String, DefSelector> selectors = new HashMap<String, DefSelector>();

}
