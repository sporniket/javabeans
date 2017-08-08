/**
 *
 */
package com.sporniket.libre.javabeans.doclet.expander;

import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassDoc.updateKnowClasses;
import static com.sporniket.libre.javabeans.doclet.expander.UtilsClassname.*;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.sporniket.libre.javabeans.doclet.expander.basic.BasicJavabeanGenerator;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
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
		private String d;
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

	public static boolean start(RootDoc root)
	{
		CommandOptions _options = readOptions(root.options());
		final ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i)
		{
			System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ");
			new ExpanderDoclet().processPojoClass(classes[i], System.out);
		}
		return true;
	}
	
	private static CommandOptions readOptions(String[][] options)
	{
		CommandOptions result = new CommandOptions() ;
		//FIXME HERE
		return result ;
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
			Field _declaredField = CommandOptions.class.getDeclaredField(option);
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

	private void processPojoClass(ClassDoc toScan, PrintStream _out)
	{
		final Set<String> _knownClasses = new TreeSet<>();
		updateKnowClasses(_knownClasses, toScan);

		final Map<String, String> _translations = getTranslationMapWhenPojosAreSuffixed(_knownClasses,
				Arrays.asList(toScan.containingPackage().name()).stream().collect(Collectors.toCollection(TreeSet::new)), "Raw");

		final Map<String, String> _shortNameMapping = new HashMap<>(_knownClasses.size() + _translations.size());
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _knownClasses);
		updateShortClassnameMappingFromClassnames(_shortNameMapping, _translations.values());
		final Set<String> _shortables = new HashSet<>(_shortNameMapping.values());

		// ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====
		// generate class
		final BasicJavabeanGenerator _generator = new BasicJavabeanGenerator();
		_generator.setKnownClasses(_knownClasses);
		_generator.setOut(_out);
		_generator.setShortables(_shortables);
		_generator.setSource(toScan);
		_generator.setTranslations(_translations);

		_generator.generate();
	}

}
