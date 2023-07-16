package com.sporniket.libre.javabeans.seeg;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Internal representation of a table, will be converted into an entity, and eventually an id class.
 *
 * <p>
 * &copy; Copyright 2012-2023 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; seeg</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 23.07.00
 * @since 20.04.01
 */
public class DefTable extends Def
{

	public final Map<String, DefColumn> columns = new LinkedHashMap<>();

	public final Set<String> pkeysColumns = new LinkedHashSet<>(10);

	public final Map<String, DefSelector> selectors = new HashMap<>();

	/**
	 * <code>true</code> if at least one of the column use an enumeration type.
	 *
	 * @since 20.05.00
	 */
	public boolean useEnums = false;

	/**
	 * Type name (local in the target package or fully qualifiad class name) to typedef "pgsql_enum".
	 *
	 * @since 20.05.00
	 */
	public String typeDefPgEnum;
}
