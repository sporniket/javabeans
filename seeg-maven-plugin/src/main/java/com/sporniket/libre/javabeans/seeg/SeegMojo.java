package com.sporniket.libre.javabeans.seeg;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "seeg")
public class SeegMojo extends AbstractMojo
{
	/**
	 * Path to the Java source directory of the target project.
	 */
	@Parameter(defaultValue = "${project.build.sourceDirectory}", property = "sourceDir", required = true, readonly = true)
	private File sourceDirectory;

	@Parameter(defaultValue = "${project.basedir}/seeg-connection.properties", property = "connectionConfig", required = true)
	private File connectionConfiguration;

	@Parameter(property = "targetPackage", required = true)
	private String targetPackage;

	@Parameter(property = "schemaName", required = false)
	private String schemaName;

	@Override
	public void execute() throws MojoExecutionException
	{
		if (StringUtils.isBlank(targetPackage))
		{
			throw new MojoExecutionException("Target package MUST be specified.");
		}
		final String _localPath = trimToEmpty(targetPackage).replace('.', File.separatorChar);
		String _targetDirectory = new File(sourceDirectory, _localPath).getAbsolutePath();
		String _connectionConfiguration = connectionConfiguration.getAbsolutePath();
		String _schemaName = StringUtils.trimToNull(schemaName);

		try (PipedInputStream pis = new PipedInputStream();
				PipedOutputStream pos = new PipedOutputStream(pis);
				BufferedReader br = new BufferedReader(new InputStreamReader(pis), 32000);
				PrintStream out = new PrintStream(pos);)
		{
			final List<Throwable> errorStack = new ArrayList<Throwable>(1);
			final ExecutorService executor = newSingleThreadExecutor();
			executor.submit(() -> {
				try
				{
					new Seeg().perform(_connectionConfiguration, _targetDirectory, targetPackage, _schemaName, out);
				}
				catch (Exception _error)
				{
					// _error.printStackTrace(out);
					errorStack.add(_error);
				}
				finally
				{
					executor.shutdown();
				}
			});

			while (!executor.isShutdown())
			{
				try
				{
					String nextLine = br.readLine();
					if (null != nextLine)
					{
						getLog().info(nextLine);
					}
				}
				catch (IOException _error)
				{
					getLog().error("Error while reading pipe.", _error);
				}
			}
			if (!errorStack.isEmpty())
			{
				throw errorStack.get(0);
			}
		}
		catch (Throwable _error)
		{
			throw new MojoExecutionException("A problem occurred.", _error);
		}
	}
}
