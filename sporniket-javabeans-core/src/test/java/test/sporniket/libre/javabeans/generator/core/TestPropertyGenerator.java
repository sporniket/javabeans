/**
 * 
 */
package test.sporniket.libre.javabeans.generator.core;

import com.sporniket.studio.schema.model.set.javabean.Bean;

import junit.framework.TestCase;

/**
 * Base class for testing property generator.
 * 
 * Those test suite requires a named bean or they throw a NPE.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public abstract class TestPropertyGenerator extends TestCase
{
	/**
	 * Dummy bean name.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final String DUMMY_BEAN_NAME = "DummyBean";

	/**
	 * Dummy bean to avoid NPE.
	 * 
	 * @since 0-SNAPSHOT
	 */
	protected final Bean myDummyBean = new Bean();

	/**
	 * 
	 * @since 0-SNAPSHOT
	 */
	public TestPropertyGenerator()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @since 0-SNAPSHOT
	 */
	public TestPropertyGenerator(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * 
	 * @since 0-SNAPSHOT
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		myDummyBean.setName(DUMMY_BEAN_NAME);
	}

}
