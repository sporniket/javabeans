/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.util.HashMap;
import java.util.Map;

import com.sporniket.studio.schema.model.set.javabean.Bean;

/**
 * Store information that will be available for a run of conversion.
 * 
 * @author dsporn
 * @since 0
 *
 */
public class GeneratorSession
{
	private final Map<String, Bean> myBeanRegistry = new HashMap<String, Bean>() ;

	public Map<String, Bean> getBeanRegistry()
	{
		return myBeanRegistry;
	}
}
