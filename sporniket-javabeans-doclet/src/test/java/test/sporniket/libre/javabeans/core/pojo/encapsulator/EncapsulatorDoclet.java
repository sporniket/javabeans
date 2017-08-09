package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.util.HashMap;
import java.util.Map;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

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

	public static void main(String[] args)
	{
		final String[] _args =
		{
				"-sourcepath",
				"/home/dsporn/dev/00-src/javabeans/sporniket-javabeans-doclet/src/test/java",
				"-d",
				"target/generated-classes/javabeans",
				"-private",
				"-doclet",
				"com.sporniket.libre.javabeans.doclet.expander.ExpanderDoclet",
				"test.sporniket.libre.javabeans.core.pojo.testsuite"

		};

		final DocumentationTool javadoc = ToolProvider.getSystemDocumentationTool();
		javadoc.run(System.in, System.out, System.err, _args);
	}
}
