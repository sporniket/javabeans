package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassDoc.updateKnowClasses;
import static com.sporniket.libre.javabeans.doclet.encapsulator.UtilsClassname.*;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

import com.sporniket.libre.javabeans.doclet.encapsulator.basic.BasicJavabeanGenerator;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

public class EncapsulatorDoclet
{
	/**
	 * Constant data and state globally accessible during the work of the doclet.
	 *
	 * @author dsporn
	 *
	 */
	public static class Session
	{
		private final Map<String, String> myTranslations = new HashMap<>();

		public Map<String, String> getTranslations()
		{
			return myTranslations;
		}
	}

	/**
	 * Constant data and state globally accessible during the work of the doclet on a package.
	 *
	 * @author dsporn
	 *
	 */
	public static class SessionPackage
	{
		private final Map<String, String> myShortNameMapping = new HashMap<>();

		public Map<String, String> getShortNameMapping()
		{
			return myShortNameMapping;
		}
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

	public static void main(String[] args)
	{
		final String[] _args =
		{
				"-sourcepath",
				"/home/dsporn/dev/00-src/javabeans/sporniket-javabeans-doclet/src/test/java",
				"-private",
				"-doclet",
				"test.sporniket.libre.javabeans.core.pojo.encapsulator.EncapsulatorDoclet",
				"test.sporniket.libre.javabeans.core.pojo.testsuite"

		};

		final DocumentationTool javadoc = ToolProvider.getSystemDocumentationTool();
		javadoc.run(System.in, System.out, System.err, _args);
	}

	public static boolean start(RootDoc root)
	{
		final ClassDoc[] classes = root.classes();
		for (int i = 0; i < classes.length; ++i)
		{
			System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ");
			new EncapsulatorDoclet().processPojoClass(classes[i], System.out);
		}
		return true;
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
		BasicJavabeanGenerator _generator = new BasicJavabeanGenerator() ;
		_generator.setKnownClasses(_knownClasses);
		_generator.setOut(_out);
		_generator.setShortables(_shortables);
		_generator.setSource(toScan);
		_generator.setTranslations(_translations);
		
		_generator.generate();
	}
}
