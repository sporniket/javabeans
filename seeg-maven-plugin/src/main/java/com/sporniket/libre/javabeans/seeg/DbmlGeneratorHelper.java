/**
 *
 */
package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.escapeQuotes;
import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generate a dbml representation of the workspace.
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
 * @since 20.07.00
 */
public class DbmlGeneratorHelper
{
	private static final String FILENAME = "schema.dbml";

	private static final String FM_RULER = "//----------------------------------------------//";

	private static final String FM_COMMENT = "// %s";

	private static final Map<String, String> DB_TYPE_MAP = Arrays.asList(//
			new String[][]
			{
					{
							"bigserial", "bigint"
					},
					{
							"serial", "int"
					},
					{
							"int8", "bigint"
					},
					{
							"int4", "int"
					},
					{
							"float8", "float"
					},
					{
							"bool", "bool"
					},
					{
							"varchar", "varchar"
					},
					{
							"date", "date"
					},
					{
							"time", "time"
					},
					{
							"timestamp", "timestamp"
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	private static void printSectionTitle(PrintStream out, String title)
	{
		out.println(FM_RULER);
		out.println(format(FM_COMMENT, convertEnumAsType(title)));
		out.println(FM_RULER);
	}

	static final CodeGenerator<ExtractionWorkspace> FROM_WORKSPACE = (specs, targetDir, targetPackage, out) -> {
		File target = new File(targetDir, FILENAME);
		try (//
				FileOutputStream _fout = new FileOutputStream(target); //
				PrintStream _out = new PrintStream(_fout, true)//
		)
		{
			Collection<DefEnum> enums = specs.getEnums();
			printEnums(_out, enums);

			printSectionTitle(_out, "Tables");
			_out.println();
			specs.getClasses().forEach(t -> {
				out.println(format("Table %s...", t.nameInDatabase));
				_out.println(format("Table %s {", t.nameInDatabase));
				t.columns.forEach((k, c) -> {
					String dbType = c.dbType;
					String type = DB_TYPE_MAP.containsKey(dbType) ? DB_TYPE_MAP.get(dbType) : convertEnumAsType(dbType);
					List<String> complements = new ArrayList<>(10);
					if (c.notNullable)
					{
						complements.add("not null");
					}
					if (t.pkeysColumns.size() == 1)
					{
						if (t.pkeysColumns.contains(k))
						{
							complements.add("pk");
						}
					}
					if (c.generated)
					{
						complements.add("javax.persistence.GenerationType.IDENTITY".equals(c.generationStrategy)
								? "increment"
								: c.generationStrategy);
					}
					if (null != c.foreignKey)
					{
						complements.add(format("ref: > %s.%s", c.foreignKey.nameInDatabase, c.foreignKey.column));
					}
					if (null != c.comment)
					{
						complements.add(format("note:'%s'", escapeQuotes(c.comment)));
					}

					if (complements.isEmpty())
					{
						_out.println(format("  %s %s", k, type));
					}
					else
					{
						String complement = complements.stream().collect(Collectors.joining(", "));
						_out.println(format("  %s %s [%s]", k, type, complement));
					}
				});
				if (null != t.comment)
				{
					_out.println(format("  note:'%s'", escapeQuotes(t.comment)));
				}
				_out.println("}");
				_out.println();
			});
			_out.println();
			_out.close();
		}
		catch (

		Exception _error)
		{
			_error.printStackTrace(out);

		}
		catch (Throwable e)
		{
			e.printStackTrace(out);
		}
	};

	private static final String ENUM_PATH_SEPARATOR = "\".\"";

	private static String convertEnumAsType(String dbType)
	{
		String result = dbType.contains(ENUM_PATH_SEPARATOR)
				? dbType.substring(dbType.lastIndexOf(ENUM_PATH_SEPARATOR) + ENUM_PATH_SEPARATOR.length())
				: dbType;
		result = result.endsWith("\"") ? result.substring(0, result.length() - 1) : result;
		return result;
	}

	private static void printEnums(PrintStream _out, Collection<DefEnum> enums)
	{
		printSectionTitle(_out, "Enumerations");
		_out.println();
		enums.forEach(e -> {
			_out.println(format("Enum %s {", e.nameInDatabase));
			e.values.forEach(v -> {
				_out.println(format("  %s", convertEnumAsType(v)));
			});
			_out.println("}");
			_out.println();
		});
	}
}
