/**
 *
 */
package com.sporniket.libre.javabeans.doclet.expander;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc.updateKnowClasses;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sporniket.libre.javabeans.doclet.expander.basic.BasicJavabeanGenerator;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;

/**
 * Use hierarchy of POJO structs to generate a hierarchy of Javabeans.
 * 
 * @author dsporn
 *
 */
public class ExpanderDoclet
{
	private static class CommandOptions
	{
		/**
		 * Store the <code>-d</code> option value (target directory).
		 */
		String d;

		/**
		 * Suffix of a Pojo that should be expanded into a Javabean.
		 */
		String pojoSuffix = "Raw";
	}

	private static String extractOptionName(String optionName)
	{
		return (optionName.startsWith("--")) ? optionName.substring(2) : optionName.substring(1);
	}

	/**
	 * Supports generics and annotations.
	 *
	 * @return {@link LanguageVersion#JAVA_1_5}
	 */
	public static LanguageVersion languageVersion()
	{
		return LanguageVersion.JAVA_1_5;
	}

	/**
	 * Return 1 or 2 for supported args.
	 * 
	 * Supported args and their type are found by reflection.
	 * 
	 * @param option
	 *            the option name to test.
	 * @return 0, 1 (flag arguments) or 2 (value argument).
	 */
	public static int optionLength(String option)
	{
		try
		{
			Field _declaredField = CommandOptions.class.getDeclaredField(extractOptionName(option));
			if (String.class.equals(_declaredField.getType()))
			{
				return 2;
			}
			else if (_declaredField.getType().isPrimitive() && _declaredField.getType().equals(boolean.class))
			{
				return 1;
			}
		}
		catch (Exception _exception)
		{
			return 0;
		}
		return 0;
	}

	private static CommandOptions readOptions(String[][] options)
	{
		CommandOptions _result = new CommandOptions();
		for (String[] _option : options)
		{
			String _optionName = extractOptionName(_option[0]); // remove leading "-" or "--"
			try
			{
				Field _optionField = _result.getClass().getDeclaredField(_optionName);
				if (String.class.equals(_optionField.getType()))
				{
					_optionField.set(_result, _option[1]);
				}
			}
			catch (NoSuchFieldException | SecurityException _exception)
			{
				continue;
			}
			catch (IllegalArgumentException | IllegalAccessException _exception)
			{
				_exception.printStackTrace();
				continue;
			}
		}
		return _result;
	}

	public static boolean start(RootDoc root)
	{
		CommandOptions _options = readOptions(root.options());

		List<ClassDoc> _sourceClasses = asList(root.classes());

		final Set<String> _packages = asList(root.specifiedPackages()).stream().map(PackageDoc::name)
				.collect(toCollection(TreeSet::new));
		final Set<String> _classes = _sourceClasses.stream().map(ClassDoc::qualifiedName).collect(toCollection(TreeSet::new));
		final Map<String, String> _translations = getTranslationMapWhenPojosAreSuffixed(_classes, _packages, _options.pojoSuffix);

		_sourceClasses.stream() //
				.filter(c -> _translations.containsKey(c.qualifiedName())) //
				.forEach(p -> new ExpanderDoclet().processPojoClass(p, System.out, _translations));

		return true;
	}

	private void processPojoClass(ClassDoc toScan, PrintStream _out, Map<String, String> translations)
	{
		final Set<String> _knownClasses = new TreeSet<>();
		updateKnowClasses(_knownClasses, toScan);

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _knownClasses);
		updateShortClassnameMappingFromClassnames(_shortNameMapping, translations.values());
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		// ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
		// generate class
		final BasicJavabeanGenerator _generator = new BasicJavabeanGenerator();
		_generator.setKnownClasses(_knownClasses);
		_generator.setOut(_out);
		_generator.setShortables(_shortables);
		_generator.setSource(toScan);
		_generator.setTranslations(translations);

		_generator.generate();
	}

}
