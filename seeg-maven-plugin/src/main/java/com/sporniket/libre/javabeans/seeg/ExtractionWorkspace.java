package com.sporniket.libre.javabeans.seeg;

import java.util.Collection;

/**
 * Workspace to maintains the internal representation for extracting the entities.
 * 
 * @author dsporn
 *
 */
public interface ExtractionWorkspace
{
	Collection<DefTable> getClasses();

	Collection<DefEnum> getEnums();

	void registerClass(final String table, final String comment);

	void registerColumn(final String table, final String column, final String type, final String nullable, final String comment,
			final String defaultValue);

	void registerEnumValue(final String _name, final String _value);

	void registerPrimaryKey(final String table, final String column);

	void registerSelector(final String table, final String selectorName, final String columnName, boolean unique);
}