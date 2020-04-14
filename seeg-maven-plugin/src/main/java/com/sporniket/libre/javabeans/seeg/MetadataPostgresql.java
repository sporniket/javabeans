package com.sporniket.libre.javabeans.seeg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetadataPostgresql extends MetadataBase
{

	private static final String PGSQL_GET_ENUM_VALUES = "SELECT pg_type.typname AS enum_type, pg_enum.enumlabel AS enum_value FROM pg_type JOIN pg_enum ON pg_enum.enumtypid = pg_type.oid order by pg_enum.enumtypid, pg_enum.enumsortorder;";

	public MetadataPostgresql(Connection connection) throws SQLException
	{
		super(connection);
	}

	@Override
	public ResultSet getEnumValues(String schema) throws SQLException
	{
		return myConnection.prepareCall(PGSQL_GET_ENUM_VALUES).executeQuery();
	}

}
