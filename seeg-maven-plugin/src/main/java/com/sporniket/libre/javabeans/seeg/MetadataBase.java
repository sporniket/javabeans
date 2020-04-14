package com.sporniket.libre.javabeans.seeg;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MetadataBase implements Metadata
{

	protected final DatabaseMetaData myMetaData;

	protected final Connection myConnection;

	public MetadataBase(Connection connection) throws SQLException
	{
		myConnection = connection;
		myMetaData = connection.getMetaData();
	}

	@Override
	public ResultSet getColumns(String schema) throws SQLException
	{
		return myMetaData.getColumns(null, schema, null, null);
	}

	@Override
	public ResultSet getForeignKeys(String schema, String table) throws SQLException
	{
		return myMetaData.getImportedKeys(null, schema, table);
	}

	@Override
	public ResultSet getIndexes(String schema, String table) throws SQLException
	{
		return myMetaData.getIndexInfo(null, schema, table, false, false);
	}

	@Override
	public ResultSet getPrimaryKeys(String schema, String table) throws SQLException
	{
		return myMetaData.getPrimaryKeys(null, schema, table);
	}

	@Override
	public ResultSet getTables(String schema) throws SQLException
	{
		return myMetaData.getTables(null, schema, null, new String[]
		{
				"TABLE"
		});
	}

}