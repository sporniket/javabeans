package com.sporniket.libre.javabeans.seeg;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstraction layer, for at least PostgreSql, the jdbc connection metadata cannot give access to the definition of enums.
 * 
 * @author dsporn
 *
 */
public interface Metadata
{

	ResultSet getEnumValues(String schema) throws SQLException;

	ResultSet getTables(String schema) throws SQLException;

	ResultSet getColumns(String schema) throws SQLException;

	ResultSet getPrimaryKeys(String schema, String table) throws SQLException;

	ResultSet getIndexes(String schema, String table) throws SQLException;

	ResultSet getForeignKeys(String schema, String table) throws SQLException;

}