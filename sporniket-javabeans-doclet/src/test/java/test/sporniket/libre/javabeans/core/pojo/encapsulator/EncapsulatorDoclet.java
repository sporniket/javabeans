package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import java.io.File;

import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

/**
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * @version 17.09.01
 * @since 17.09.01
 */
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
