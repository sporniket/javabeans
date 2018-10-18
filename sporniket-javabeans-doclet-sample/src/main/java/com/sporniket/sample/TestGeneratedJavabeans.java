package com.sporniket.sample;

import com.sporniket.sample.pojos.SampleGenerics;
import com.sporniket.sample.pojos.SampleGenerics_Builder;

public class TestGeneratedJavabeans
{
	static class Parametrized extends SampleGenerics<CharSequence, Double>
	{
		public void doSomething()
		{
			System.out.println("hi !");
		}
	}

	public static void main(String[] args)
	{
		SampleGenerics<String, Long> sampleGenerics = new SampleGenerics_Builder<String, Long>()//
				// ...etc...
				.done();

		Parametrized sampleGenericsSubclass = (Parametrized) new SampleGenerics_Builder<>(new Parametrized())
				// ...etc...
				.done();
		sampleGenericsSubclass.doSomething();
	}

}
