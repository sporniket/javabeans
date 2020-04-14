package com.sporniket.libre.javabeans.seeg;

import static com.sporniket.libre.javabeans.seeg.CodeGeneratorHelper.FROM_DEF_CLASS_TO_ENTITY;
import static com.sporniket.libre.javabeans.seeg.CodeGeneratorHelper.FROM_DEF_CLASS_TO_FINDER;
import static com.sporniket.libre.javabeans.seeg.CodeGeneratorHelper.FROM_DEF_CLASS_TO_REPOSITORY;
import static com.sporniket.libre.javabeans.seeg.CodeGeneratorHelper.FROM_DEF_ENUM;
import static com.sporniket.libre.javabeans.seeg.ConsoleHelper.printHeader;
import static java.lang.String.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Seeg : Sporniket Entities Extractor and Generator.
 * 
 * <p>
 * Utility program that connects to a database, and generate Java JPA entities and Spring-data-jpa repositories from the tables of a
 * schema.
 * </p>
 * 
 * @author dsporn
 *
 */
public class Seeg
{
	public static void main(String[] args)
	{
		if (args.length < 3 || args.length > 4)
		{
			System.out.println("Usage: java ExtractorAndGeneratorOfEntities configDatabase targetDir targetPackage [schema].");
			System.out.println("    configDatabase : properties file describing the connection to the database.");
			System.out.println("        - url : jdbc url, e.g. 'jdbc:postgresql://localhost:54320/postgres'.");
			System.out.println("        - driverClass : jdbc url, e.g. 'org.postgresql.Driver'.");
			System.out.println("        - username : name of the database user, should have access to the schema.");
			System.out.println("        - password : password for the database user.");
			System.out.println("    targetDir : path to the location where Java source files will be generated.");
			System.out.println("    targetPackage : full qualified name of the Java package of the generated sources.");
			System.out.println("        e.g. 'com.foo.entities'.");
			System.out.println("    schema : optionnal, name of the database schema to inspect.");

			System.exit(1);
		}

		// parameters output
		final String configDatabase = args[0];
		String targetDir = args[1];
		String targetPackage = args[2];
		String schema = args.length >= 4 ? args[3] : null;

		Seeg work = new Seeg();
		try
		{
			work.perform(configDatabase, targetDir, targetPackage, schema);
		}
		catch (Exception _error)
		{
			System.exit(1);
		}
	}

	private void checkTargetDirectoryOrDie(String targetDir, PrintStream out)
	{
		File _targetDir = new File(targetDir);
		if (_targetDir.exists() && !_targetDir.isDirectory())
		{
			out.println("targetDir MUST be a directory : " + targetDir);
			System.exit(1);
		}
		else if (!_targetDir.exists() && !_targetDir.mkdirs())
		{
			out.println("Could not create targetDir : " + targetDir);
			System.exit(1);
		}
	}

	/**
	 * @param _configDatabase
	 *            path to the properties file that describe the connection to the database.
	 * @return a JDBC connection.
	 * @throws Exception
	 *             when there is a problem.
	 */
	private Connection getConnection(final String _configDatabase) throws Exception
	{
		Properties setupProps = new Properties();
		setupProps.load(new FileInputStream(_configDatabase));

		Properties connProps = new Properties();
		connProps.put("user", setupProps.getProperty("username"));
		connProps.put("password", setupProps.getProperty("password"));

		// create connection
		Driver driver = (Driver) Class.forName(setupProps.getProperty("driverClass")).getConstructor().newInstance();
		Connection result = driver.connect(setupProps.getProperty("url"), connProps);
		return result;
	}

	public void perform(final String configDatabase, String targetDir, String targetPackage, String schema) throws Exception
	{
		perform(configDatabase, targetDir, targetPackage, schema, System.out);
	}

	public void perform(final String configDatabase, String targetDir, String targetPackage, String schema, PrintStream out)
			throws Exception
	{
		checkTargetDirectoryOrDie(targetDir, out);
		try (Connection con = getConnection(configDatabase))
		{
			final Metadata _metadata = new MetadataPostgresql(con);
			ExtractionWorkspace workspace = new ExtractionWorkspacePostgresql();
			printHeader(out, "Enums values");
			for (ResultSet r = _metadata.getEnumValues(schema); r.next();)
			{
				final String _name = r.getString("enum_type");
				final String _value = r.getString("enum_value");
				out.println(format("%s.%s", _name, _value));
				workspace.registerEnumValue(_name, _value);
			}
			printHeader(out, "Tables");
			for (ResultSet r = _metadata.getTables(schema); r.next();)
			{
				final String _name = r.getString("TABLE_NAME");
				final String _comment = r.getString("REMARKS");
				out.println(format("%s (%s) : %s ", _name, r.getString("SELF_REFERENCING_COL_NAME"), _comment));
				workspace.registerClass(_name, _comment);
			}
			printHeader(out, "Columns");
			for (ResultSet r = _metadata.getColumns(schema); r.next();)
			{
				final String _table = r.getString("TABLE_NAME");
				final String _column = r.getString("COLUMN_NAME");
				final String _type = r.getString("TYPE_NAME");
				final String _nullable = r.getString("IS_NULLABLE");
				final String _default = r.getString("COLUMN_DEF");
				final String _comment = r.getString("REMARKS");
				out.println(format("%s.%s (%s -- %s) -- nullable (%s) default (%s) : %s ", _table, _column, _type,
						r.getString("DATA_TYPE"), _nullable, _default, _comment));
				workspace.registerColumn(_table, _column, _type, _nullable, _comment, _default);
			}
			printHeader(out, "Primary Keys");
			for (DefClass c : workspace.getClasses())
			{
				for (ResultSet r = _metadata.getPrimaryKeys(schema, c.nameInDatabase); r.next();)
				{
					final String _column = r.getString("COLUMN_NAME");
					workspace.registerPrimaryKey(c.nameInDatabase, _column);
					out.println(format("PK[%s][%s]->%s", r.getString("TABLE_NAME"), r.getString("KEY_SEQ"), _column));
				}
			}
			printHeader(out, "Indexes");
			for (DefClass c : workspace.getClasses())
			{
				for (ResultSet r = _metadata.getIndexes(schema, c.nameInDatabase); r.next();)
				{
					final boolean _nonUnique = r.getBoolean("NON_UNIQUE");
					final String _indexName = r.getString("INDEX_NAME");
					final String _columnName = r.getString("COLUMN_NAME");
					workspace.registerSelector(c.nameInDatabase, _indexName, _columnName, !_nonUnique);
					out.println(format("%s->%s[%s] : %s / %s / %s", r.getString("TABLE_NAME"), _indexName,
							r.getString("ORDINAL_POSITION"), _columnName, r.getString("ASC_OR_DESC"),
							_nonUnique ? "multiple" : "unique"));

				}
			}
			printHeader(out, "Foreign Keys");
			for (DefClass c : workspace.getClasses())
			{
				for (ResultSet r = _metadata.getForeignKeys(schema, c.nameInDatabase); r.next();)
				{
					final String _table = r.getString("FKTABLE_NAME");
					final String _columnName = r.getString("FKCOLUMN_NAME");
					workspace.registerSelector(_table, _columnName, _columnName, false);
					out.println(format("%s.%s -> %s.%s (%d)", _table, _columnName, r.getString("PKTABLE_NAME"),
							r.getString("PKCOLUMN_NAME"), r.getShort("KEY_SEQ")));
				}
			}
			printHeader(out, "Java code");

			workspace.getEnums().forEach(e -> FROM_DEF_ENUM.generate(e, targetDir, targetPackage, out));
			workspace.getClasses().forEach(c -> FROM_DEF_CLASS_TO_ENTITY.generate(c, targetDir, targetPackage, out));
			workspace.getClasses().forEach(c -> FROM_DEF_CLASS_TO_FINDER.generate(c, targetDir, targetPackage, out));
			workspace.getClasses().forEach(c -> FROM_DEF_CLASS_TO_REPOSITORY.generate(c, targetDir, targetPackage, out));
			out.println("Done.");
		}
		catch (Exception _error)
		{
			_error.printStackTrace(out);
			throw _error;
		}
	}

}
