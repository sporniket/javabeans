package com.sporniket.libre.javabeans.seeg;

import java.util.Collection;

public interface ExtractionWorkspace
{
	Collection<DefClass> getClasses();

	Collection<DefEnum> getEnums();

	void registerClass(final String table, final String comment);

	void registerColumn(final String table, final String column, final String type, final String nullable, final String comment,
			final String defaultValue);

	void registerEnumValue(final String _name, final String _value);

	void registerPrimaryKey(final String table, final String column);

	void registerSelector(final String table, final String selectorName, final String columnName, boolean unique);
}