package com.sporniket.libre.javabeans.seeg;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.TreeSet;

public class CodeGeneratorHelper
{
	private static final String PREFIX_ENTITY = "Entity";

	private static final String PREFIX_REPOSITORY = "Dao";

	private static final String PREFIX_FINDER = "FinderOf";

	private static final String ANNOTATION__NOT_NULL = "@javax.validation.constraints.NotNull ";

	private static final String JAVA_EXTENSION_SUFFIX = ".java";

	static final CodeGenerator<DefEnum> FROM_DEF_ENUM = (specs, targetDir, targetPackage, out) -> {
		File target = new File(targetDir, specs.nameInJava + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			_out.println(format("public enum %s {%s;}", specs.nameInJava, String.join(",", specs.values)));
			_out.close();
		}
		catch (

		Exception _error)
		{
			_error.printStackTrace(out);
		}
	};

	static final CodeGenerator<DefClass> FROM_DEF_CLASS_TO_FINDER = (specs, targetDir, targetPackage, out) -> {
		// sanity check : require pk
		if (specs.pkeysColumns.isEmpty())
		{
			out.println(format("Table %s has no primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		else if (specs.pkeysColumns.size() > 1)
		{
			out.println(
					format("Table %s has compounded primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		final String _finderName = PREFIX_FINDER + specs.nameInJava;
		final String _entityName = PREFIX_ENTITY + specs.nameInJava;
		File target = new File(targetDir, _finderName + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@org.springframework.data.repository.NoRepositoryBean");
			final String _javaTypeOfPrimaryKey = specs.columns.get(specs.pkeysColumns.iterator().next()).javaType;
			_out.println(format("public interface %s extends org.springframework.data.jpa.repository.JpaRepository<%s, %s> {",
					_finderName, _entityName, _javaTypeOfPrimaryKey));
			specs.selectors.values().forEach(s -> {
				final String _return = s.unique ? _entityName : format("java.util.List<%s>", _entityName);
				final String _find = s.unique ? "findBy" : "findAllBy";
				final String _queryMethod = s.columns.stream()//
						.map(c -> specs.columns.get(c))//
						.map(c -> c.nameInJava)//
						.collect(joining("And"));
				final String _args = s.columns.stream()//
						.map(c -> specs.columns.get(c))//
						.map(c -> format("%s %s", c.javaType, StringHelper.uncapitalizeFirstLetter(c.nameInJava)))//
						.collect(joining(", "));
				_out.println(format("  %s %s%s(%s);", _return, _find, _queryMethod, _args));
			});
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}

	};

	static final CodeGenerator<DefClass> FROM_DEF_CLASS_TO_REPOSITORY = (specs, targetDir, targetPackage, out) -> {
		// sanity check : require pk
		if (specs.pkeysColumns.isEmpty())
		{
			out.println(format("Table %s has no primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		else if (specs.pkeysColumns.size() > 1)
		{
			out.println(
					format("Table %s has compounded primary key, cannot generate repository interface !!", specs.nameInDatabase));
			return;
		}
		final String _repoName = PREFIX_REPOSITORY + specs.nameInJava;
		final String _finderName = PREFIX_FINDER + specs.nameInJava;
		File target = new File(targetDir, _repoName + JAVA_EXTENSION_SUFFIX);

		// the repository class should be generated if absent.
		// then it is manually edited and thus should not be overwritten.
		if (target.exists())
		{
			out.println(format("Repository class already exist, skip : %s", target.getAbsolutePath()));
			return;
		}

		// ok proceed
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@org.springframework.stereotype.Repository");
			_out.println(format("public interface %s extends %s {", _repoName, _finderName));
			_out.println("  // write your own method here");
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}

	};

	static final CodeGenerator<DefClass> FROM_DEF_CLASS_TO_ENTITY = (specs, targetDir, targetPackage, out) -> {
		final String _finalTypeName = PREFIX_ENTITY + specs.nameInJava;
		File target = new File(targetDir, _finalTypeName + JAVA_EXTENSION_SUFFIX);
		try (PrintStream _out = new PrintStream(target))
		{
			printPackage(targetPackage, _out);
			_out.println();
			if (null != specs.comment)
			{
				_out.println(format("/** %s. */", specs.comment));
			}
			_out.println("@javax.persistence.Entity");
			_out.println(format("@javax.persistence.Table(name = \"%s\")", specs.nameInDatabase));
			_out.println(format("public class %s {", _finalTypeName));
			new TreeSet<String>(specs.columns.keySet()).stream()//
					.map(k -> specs.columns.get(k))//
					.forEach(colSpecs -> {
						// field
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   *%s. \n   */", colSpecs.comment));
						}
						_out.println(format("  @javax.persistence.Column(name = \"%s\")", colSpecs.nameInDatabase));
						if (colSpecs.generated)
						{
							if (null != colSpecs.generationStrategy)
							{
								_out.println(
										format("  @javax.persistence.GeneratedValue(strategy = %s)", colSpecs.generationStrategy));
							}
							else
							{
								_out.println("  @javax.persistence.GeneratedValue");
							}
						}
						if (specs.pkeysColumns.contains(colSpecs.nameInDatabase))
						{
							_out.println("  @javax.persistence.Id");
						}
						if (null != colSpecs.temporalMapping)
						{
							_out.println(format("  %s", colSpecs.temporalMapping));
						}
						if (colSpecs.notNullable)
						{
							_out.println("  " + ANNOTATION__NOT_NULL);
						}
						_out.println(format("  private %s my%s ;", colSpecs.javaType, colSpecs.nameInJava));

						// getter
						if (null != colSpecs.comment)
						{
							_out.println(
									format("  /**\n   * %s. \n   *\n   * @returns the current value. \n   */", colSpecs.comment));
						}
						_out.println(
								format("  public %s%s get%s() { return my%s ;} ", colSpecs.notNullable ? ANNOTATION__NOT_NULL : "",
										colSpecs.javaType, colSpecs.nameInJava, colSpecs.nameInJava));

						// setter
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   * %s. \n   *\n   * @value the new value. \n   */", colSpecs.comment));
						}
						_out.println(format("  public void set%s(%s%s value) { my%s = value ;} ", colSpecs.nameInJava,
								colSpecs.notNullable ? ANNOTATION__NOT_NULL : "", colSpecs.javaType, colSpecs.nameInJava));

						// fluent setter
						if (null != colSpecs.comment)
						{
							_out.println(format("  /**\n   * %s. \n   *\n   * @value the new value. \n   */", colSpecs.comment));
						}
						_out.println(format("  public %s with%s(%s%s value) { my%s = value ; return this ;} ", _finalTypeName,
								colSpecs.nameInJava, colSpecs.notNullable ? ANNOTATION__NOT_NULL : "", colSpecs.javaType,
								colSpecs.nameInJava));
						_out.println();
					});
			_out.println("}");
			_out.println();
		}
		catch (

		FileNotFoundException _error)
		{
			_error.printStackTrace();
		}
	};

	private static void printPackage(String targetPackage, PrintStream _out)
	{
		_out.println(format("package %s;", targetPackage));
	}

}
