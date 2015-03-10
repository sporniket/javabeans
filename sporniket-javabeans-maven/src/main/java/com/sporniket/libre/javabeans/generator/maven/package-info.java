/**
 * Plugin for generating javabeans from specific xml descriptions.
 * 
 * <h3>Plugin declaration</h3>
 * 
 * <p>Add the following plugin declaration in the plugins section of your POM file :
 * 
 * 
 * <pre>
 * &lt;!-- For generating javabeans --&gt;
 * &lt;plugin&gt;
 * 	&lt;groupId&gt;com.sporniket.javabeans&lt;/groupId&gt;
 * 	&lt;artifactId&gt;sporniket-javabeans-maven&lt;/artifactId&gt;
 * 	&lt;version&gt;[the version to use]&lt;/version&gt;
 * 	&lt;executions&gt;
 * 		&lt;execution&gt;
 * 			&lt;goals&gt;
 * 				&lt;goal&gt;generate&lt;/goal&gt;
 * 			&lt;/goals&gt;
 * 		&lt;/execution&gt;
 * 	&lt;/executions&gt;
 * &lt;/plugin&gt;
 * </pre>
 * 
 * <h3>Javabean description files and generated sources location</h3>
 * 
 * <p>The plugin will look for xml files in src/main/sporniket-javabeans.
 * 
 * <p>The plugin will generate javacode in target/generated-sources/sporniket-javabeans, and will create the packages directories as needed.
 * 
 * <h3>Run the plugin</h3>
 * 
 * <p>Invoke maven and instruct to use the "generate" goal of the plugin :
 * 	
 * <pre>
 * mvn com.sporniket.javabeans:sporniket-javabeans-maven:generate
 * </pre>
 * 	
 * <p>If not done yet, add target/generated-sources/sporniket-javabeans in the list of your source code folders.
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr />
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; maven</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; maven</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; maven</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * maven</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * @version 13.01.01
 */
package com.sporniket.libre.javabeans.generator.maven;

