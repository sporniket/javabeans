package com.sporniket.libre.javabeans.models.producers.javadoc.typescanner;

import java.util.List;

import com.sporniket.libre.javabeans.models.ClassSpecs;

public class ParameterizedClassSpecs_Builder
{
	private final ParameterizedClassSpecs myBean;

	public ParameterizedClassSpecs_Builder(final ParameterizedClassSpecs bean)
	{
		super();
		myBean = bean;
	}

	public ParameterizedClassSpecs_Builder()
	{
		this(new ParameterizedClassSpecs());
	}

	public ParameterizedClassSpecs done()
	{
		return myBean;
	}

	public ParameterizedClassSpecs_Builder withMainSpecs(final ClassSpecs mainSpecs)
	{
		myBean.setMainSpecs(mainSpecs);
		return this;
	}

	public ParameterizedClassSpecs_Builder withParameters(final List<ParameterizedClassSpecs> parameters)
	{
		myBean.setParameters(parameters);
		return this;
	}

	public ParameterizedClassSpecs_Builder withKind(final KindOfClassSpecs kind)
	{
		myBean.setKind(kind);
		return this;
	}

	public ParameterizedClassSpecs_Builder withKindOfWildcardBound(final KindOfWildcardBound kindOfWildcardBound)
	{
		myBean.setKindOfWildcardBound(kindOfWildcardBound);
		return this;
	}

	public ParameterizedClassSpecs_Builder withBound(final ClassSpecs bound)
	{
		myBean.setBound(bound);
		return this;
	}

}
