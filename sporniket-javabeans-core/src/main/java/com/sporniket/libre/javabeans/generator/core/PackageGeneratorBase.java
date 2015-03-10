/**
 * 
 */
package com.sporniket.libre.javabeans.generator.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.sporniket.libre.io.MessageFormatLoader;
import com.sporniket.studio.schema.model.set.javabean.BeanSet;
import com.sporniket.studio.schema.model.set.javabean.Package;

/**
 * Base implementation of {@link PackageGenerator}
 * 
 * <p>
 * &copy; Copyright 2012-2013 David Sporn
 * </p>
 * <hr />
 * 
 * <p>
 * This file is part of <i>The Sporniket Javabeans Library &#8211; core</i>.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211; 
 * core</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * @version 13.01.01
 * 
 */
public class PackageGeneratorBase implements PackageGenerator
{
	/**
	 * Name of the template file for the package-info.
	 */
	private static final String TEMPLATE_NAME__PACKAGE_INFO = "template_package.txt";

	private MessageFormat myPackageInfoTemplate;

	private void PackageGeneratorBase__loadTemplates() throws IOException
	{
		myPackageInfoTemplate = MessageFormatLoader.load(getClass().getResourceAsStream(TEMPLATE_NAME__PACKAGE_INFO));
	}

	public PackageGeneratorBase() throws IOException
	{
		PackageGeneratorBase__loadTemplates();
	}

	public void outputPackageInfo(PrintWriter out, Package pack, BeanSet set)
	{
		Object[] _params =
		{
				pack.getName(),
				(null != pack.getAnnotation()) ? pack.getAnnotation().getSummary() : "",
				JavadocUtils.generateDescription(pack),
				JavadocUtils.generateLicence(set),
				set.getAnnotation().getVersion(),
				JavadocUtils.generateAuthor(set),
				JavadocUtils.generateSee(pack, set),
				pack.getName()
		};
		out.println(myPackageInfoTemplate.format(_params));
	}

}
