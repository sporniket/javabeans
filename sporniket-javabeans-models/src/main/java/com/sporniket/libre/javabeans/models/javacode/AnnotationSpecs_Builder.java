package com.sporniket.libre.javabeans.models.javacode;

import java.util.List;

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
public class AnnotationSpecs_Builder {
    private final AnnotationSpecs bean ;

    public AnnotationSpecs done() {return bean ;}

    /**Default constructor. 
     */
    public AnnotationSpecs_Builder() {bean = new AnnotationSpecs() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public AnnotationSpecs_Builder(AnnotationSpecs newBean) {bean = newBean ;}

    public AnnotationSpecs_Builder withOnBuilder(boolean value) {bean.setOnBuilder(value); return this;}
    public AnnotationSpecs_Builder withOnField(boolean value) {bean.setOnField(value); return this;}
    public AnnotationSpecs_Builder withOnGetter(boolean value) {bean.setOnGetter(value); return this;}
    public AnnotationSpecs_Builder withOnSetter(boolean value) {bean.setOnSetter(value); return this;}
    public AnnotationSpecs_Builder withParameters(List<AnnotationParameterSpecs> value) {bean.setParameters(value); return this;}
    public AnnotationSpecs_Builder withType(String value) {bean.setType(value); return this;}
}

