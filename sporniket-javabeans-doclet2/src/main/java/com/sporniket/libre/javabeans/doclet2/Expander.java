package com.sporniket.libre.javabeans.doclet2;

import static java.util.Collections.emptySet;
import static javax.lang.model.SourceVersion.latest;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;

import com.sporniket.libre.javabeans.models.ClassSpecs;
import com.sporniket.libre.javabeans.models.consumers.javasource.BasicBuilderGenerator;
import com.sporniket.libre.javabeans.models.consumers.javasource.BasicJavabeanGenerator;
import com.sporniket.libre.javabeans.models.consumers.javasource.Builder;
import com.sporniket.libre.javabeans.models.consumers.javasource.Configuration;
import com.sporniket.libre.javabeans.models.producers.javadoc.ScanConfiguration;
import com.sporniket.libre.javabeans.models.producers.javadoc.DocletEnvironmentScanner;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class Expander implements Doclet
{

	@Override
	public void init(final Locale locale, final Reporter reporter)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getName()
	{
		return "Pojo to Javabeans Expander v2, by Sporniket";
	}

	@Override
	public Set<? extends Option> getSupportedOptions()
	{
		return emptySet();
	}

	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		return latest();
	}

	@Override
	public boolean run(final DocletEnvironment environment)
	{
		final ScanConfiguration configuration = new ScanConfiguration();
		final Configuration generatorOptions = new Configuration();
		final Collection<ClassSpecs> _classes = new DocletEnvironmentScanner().scan(environment, configuration);
		for (final ClassSpecs _class : _classes)
		{
			System.out.println("##########");
			new Builder<>(new BasicJavabeanGenerator()) //
					.withClassSpecs(_class) //
					.withOptions(generatorOptions) //
					.done() //
					.generate(System.out);

			System.out.println("==========");
			new Builder<>(new BasicBuilderGenerator()) //
					.withClassSpecs(_class) //
					.withOptions(generatorOptions) //
					.done() //
					.generate(System.out);
		}
		System.out.println("##########");
		return true;
	}

}
