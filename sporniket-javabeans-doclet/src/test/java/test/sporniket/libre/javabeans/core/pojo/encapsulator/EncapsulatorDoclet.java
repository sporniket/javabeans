package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

public class EncapsulatorDoclet
{
	public static void main(String[] args)
	{
		final DocumentationTool javadoc = ToolProvider.getSystemDocumentationTool();

		String[] _argsExpander =
		{
				"-sourcepath",
				"/home/dsporn/dev/00-src/javabeans/sporniket-javabeans-doclet/src/test/java",
				"-d",
				"target/generated-sources/javabeans",
				"-private",
				"-doclet",
				"com.sporniket.libre.javabeans.doclet.expander.ExpanderDoclet",
				"test.sporniket.libre.javabeans.core.pojo.testsuite"
		};

		javadoc.run(System.in, System.out, System.err, _argsExpander);

		String[] _argsDistiller =
		{
				"-sourcepath",
				"/home/dsporn/dev/00-src/javabeans/sporniket-javabeans-doclet/src/main/java",
				"-d",
				"target/generated-sources/javabeans",
				"-private",
				"-doclet",
				"com.sporniket.libre.javabeans.doclet.expander.DistillerDoclet",
				"com.sporniket.libre.javabeans.doclet.expander.basic"
		};

		javadoc.run(System.in, System.out, System.err, _argsDistiller);
	}
}
