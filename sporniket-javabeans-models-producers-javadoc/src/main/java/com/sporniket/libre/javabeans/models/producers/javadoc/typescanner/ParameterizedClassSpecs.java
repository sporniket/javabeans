package com.sporniket.libre.javabeans.models.producers.javadoc.typescanner;

import java.util.List;

import com.sporniket.libre.javabeans.models.ClassSpecs;

public class ParameterizedClassSpecs
{
	private ClassSpecs myMainSpecs;

	private List<ParameterizedClassSpecs> parameters;

	private KindOfClassSpecs kind;

	private KindOfWildcardBound kindOfWildcardBound;

	private ClassSpecs myBound;

	public ClassSpecs getMainSpecs()
	{
		return myMainSpecs;
	}

	public void setMainSpecs(final ClassSpecs mainSpecs)
	{
		myMainSpecs = mainSpecs;
	}

	public List<ParameterizedClassSpecs> getParameters()
	{
		return parameters;
	}

	public void setParameters(final List<ParameterizedClassSpecs> parameters)
	{
		this.parameters = parameters;
	}

	public KindOfClassSpecs getKind()
	{
		return kind;
	}

	public void setKind(final KindOfClassSpecs kind)
	{
		this.kind = kind;
	}

	public KindOfWildcardBound getKindOfWildcardBound()
	{
		return kindOfWildcardBound;
	}

	public void setKindOfWildcardBound(final KindOfWildcardBound kindOfWildcardBound)
	{
		this.kindOfWildcardBound = kindOfWildcardBound;
	}

	public ClassSpecs getBound()
	{
		return myBound;
	}

	public void setBound(final ClassSpecs bound)
	{
		myBound = bound;
	}

}
