package com.sporniket.libre.javabeans.models;


/** GENERATED CODE !
 * <p>
 * &copy; Copyright 2012-2025 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 25.11.00
 * @since 25.11.00
 */
public class AnnotationParameterSpecsSingleValue_Builder {
    private final AnnotationParameterSpecsSingleValue bean ;

    public AnnotationParameterSpecsSingleValue done() {return bean ;}

    /**Default constructor. 
     */
    public AnnotationParameterSpecsSingleValue_Builder() {bean = new AnnotationParameterSpecsSingleValue() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public AnnotationParameterSpecsSingleValue_Builder(AnnotationParameterSpecsSingleValue newBean) {bean = newBean ;}

    public AnnotationParameterSpecsSingleValue_Builder withString(boolean value) {bean.setString(value); return this;}
    public AnnotationParameterSpecsSingleValue_Builder withValue(Object value) {bean.setValue(value); return this;}
    public AnnotationParameterSpecsSingleValue_Builder withName(String value) {bean.setName(value); return this;}
}

