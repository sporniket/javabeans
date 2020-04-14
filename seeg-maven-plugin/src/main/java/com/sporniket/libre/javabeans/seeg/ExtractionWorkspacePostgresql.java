package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.camelCaseCapitalizedFromSnakeCase;
import static com.sporniket.libre.javabeans.seeg.StringHelper.undoublequote;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class ExtractionWorkspacePostgresql extends ExtractionWorkspaceBase implements ExtractionWorkspace
{

	private static final Map<String, String> PGSQL_TYPE_MAP = Arrays.asList(//
			new String[][]
			{
					{
							"serial", "long"
					},
					{
							"int8", "long"
					},
					{
							"int4", "integer"
					},
					{
							"float8", "float"
					},
					{
							"bool", "boolean"
					},
					{
							"varchar", "String"
					},
					{
							"date", Date.class.getName()
					},
					{
							"timestamp", Date.class.getName()
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	private static final Map<String, String> PGSQL_TYPE_MAP_NULLABLE = Arrays.asList(//
			new String[][]
			{
					{
							"bigserial", "Long"
					},
					{
							"serial", "Integer"
					},
					{
							"int8", "Long"
					},
					{
							"int4", "Integer"
					},
					{
							"float8", "Float"
					},
					{
							"bool", "Boolean"
					}
			}).stream()//
			.collect(toMap(e -> e[0], e -> e[1]));

	@Override
	public void registerColumn(final String table, final String column, final String type, final String nullable,
			final String comment, final String defaultValue)
	{
		if (!regClasses.containsKey(table))
		{
			registerClass(table, null);
		}
		DefClass _parent = regClasses.get(table);
		DefColumn _c = new DefColumn();
		_c.nameInDatabase = column;
		_c.comment = comment;
		_c.nameInJava = camelCaseCapitalizedFromSnakeCase(column);
		_c.generated = (null != defaultValue && defaultValue.startsWith("nextval("));
		_c.notNullable = !_c.generated && "NO".equals(nullable);
		if (_c.generated)
		{
			// supports only identity strategy, for now.
			_c.generationStrategy = "javax.persistence.GenerationType.IDENTITY";
		}
		if (PGSQL_TYPE_MAP.containsKey(type))
		{
			_c.javaType = PGSQL_TYPE_MAP.get(type);
			if ((!_c.notNullable || _c.generated) && PGSQL_TYPE_MAP_NULLABLE.containsKey(type))
			{
				_c.javaType = PGSQL_TYPE_MAP_NULLABLE.get(type);
			}
		}
		else
		{
			// enum value
			String[] _path = type.split("\\.");
			String _enum = _path[_path.length - 1];
			_enum = undoublequote(_enum);
			_c.javaType = regEnums.get(_enum).nameInJava;
		}
		_parent.columns.put(column, _c);
	}

}
