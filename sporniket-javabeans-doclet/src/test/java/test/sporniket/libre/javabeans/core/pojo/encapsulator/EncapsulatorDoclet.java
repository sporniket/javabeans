package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.File;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

public class EncapsulatorDoclet
{
	private static final String ENV_NAME__PROJECT_LOCATION = "project.base.dir";

	private static String getProjectBaseDirOrDie()
	{
		String projectBaseDir = System.getProperty(ENV_NAME__PROJECT_LOCATION);
		if (null == projectBaseDir)
		{
			throw new RuntimeException("'project.base.dir' property MUST be defined in the invocation command.");
		}

		File dir = new File(projectBaseDir);
		if (!dir.exists())
		{
			throw new RuntimeException("Folder '" + dir.getAbsolutePath() + "' MUST exist.");
		}
		else if (!dir.isDirectory())
		{
			throw new RuntimeException("'" + dir.getAbsolutePath() + "' MUST be a folder.");
		}

		projectBaseDir = dir.getAbsolutePath();
		return projectBaseDir;
	}

	public static void main(String[] args)
	{
		String projectBaseDir = getProjectBaseDirOrDie();

		final DocumentationTool javadoc = ToolProvider.getSystemDocumentationTool();

		String[] _argsExpander =
		{
				"-sourcepath",
				projectBaseDir + "/src/test/java",
				"-d",
				"target/generated-sources/javabeans",
				"-private",
				"-doclet",
				"com.sporniket.libre.javabeans.doclet.ExpanderDoclet",
				"test.sporniket.libre.javabeans.core.pojo.testsuite"
		};

		javadoc.run(System.in, System.out, System.err, _argsExpander);

		String[] _argsDistiller =
		{
				"-sourcepath",
				projectBaseDir + "/src/main/java",
				"-d",
				"target/generated-sources/javabeans",
				"-private",
				"-doclet",
				"com.sporniket.libre.javabeans.doclet.DistillerDoclet",
				"com.sporniket.libre.javabeans.doclet.basic"
		};

		javadoc.run(System.in, System.out, System.err, _argsDistiller);
	}
}
