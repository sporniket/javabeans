package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.StringHelper.camelCaseCapitalizedFromSnakeCase;
import static java.lang.String.format;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ExtractionWorkspaceBase implements ExtractionWorkspace
{

	protected final Map<String, DefClass> regClasses = new HashMap<String, DefClass>();

	protected final Map<String, DefEnum> regEnums = new HashMap<String, DefEnum>();

	public ExtractionWorkspaceBase()
	{
		super();
	}

	@Override
	public Collection<DefClass> getClasses()
	{
		return regClasses.values();
	}

	private DefClass getClassOrDie(String table)
	{
		if (!regClasses.containsKey(table))
		{
			throw new IllegalStateException(format("Try to register primary key of unknown table '%s'", table));
		}
		return regClasses.get(table);
	}

	@Override
	public Collection<DefEnum> getEnums()
	{
		return regEnums.values();
	}

	@Override
	public void registerClass(final String table, final String comment)
	{
		DefClass _class = new DefClass();
		_class.nameInDatabase = table;
		_class.nameInJava = camelCaseCapitalizedFromSnakeCase(table);
		_class.comment = comment;
		regClasses.put(table, _class);
	}

	@Override
	public void registerEnumValue(final String name, final String value)
	{
		if (!regEnums.containsKey(name))
		{
			final DefEnum _enum = new DefEnum();
			_enum.nameInDatabase = name;
			_enum.nameInJava = camelCaseCapitalizedFromSnakeCase(name);
			regEnums.put(name, _enum);
		}
		regEnums.get(name).values.add(value);
	}

	@Override
	public void registerPrimaryKey(String table, String column)
	{
		DefClass _class = getClassOrDie(table);
		_class.pkeysColumns.add(column);

	}

	@Override
	public void registerSelector(String table, String selectorName, String columnName, boolean unique)
	{
		DefClass _class = getClassOrDie(table);
		if (!_class.columns.containsKey(columnName))
		{
			throw new IllegalStateException(format("Try to register selector of unknown column '%s.%s'", table, columnName));
		}
		if (1 == _class.pkeysColumns.size() && _class.pkeysColumns.contains(columnName))
		{
			return; // already exists in JpaRepository.
		}
		DefSelector selector;
		if (_class.selectors.containsKey(selectorName))
		{
			selector = _class.selectors.get(selectorName);
		}
		else
		{
			selector = new DefSelector();
			selector.nameInDatabase = selectorName;
			selector.unique = unique;
			_class.selectors.put(selectorName, selector);
		}
		selector.columns.add(columnName);

	}

}