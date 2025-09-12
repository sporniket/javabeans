package com.sporniket.libre.javabeans.models;

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
public class FieldSpecs_Builder {
    private final FieldSpecs bean ;

    public FieldSpecs done() {return bean ;}

    /**Default constructor. 
     */
    public FieldSpecs_Builder() {bean = new FieldSpecs() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public FieldSpecs_Builder(FieldSpecs newBean) {bean = newBean ;}

    public FieldSpecs_Builder withAnnotations(List<AnnotationSpecs> value) {bean.setAnnotations(value); return this;}
    public FieldSpecs_Builder withArrayMarker(String value) {bean.setArrayMarker(value); return this;}
    public FieldSpecs_Builder withBooleanGetter(boolean value) {bean.setBooleanGetter(value); return this;}
    public FieldSpecs_Builder withDirectlyRequired(boolean value) {bean.setDirectlyRequired(value); return this;}
    public FieldSpecs_Builder withFieldPrefix(String value) {bean.setFieldPrefix(value); return this;}
    public FieldSpecs_Builder withJavadocLines(String[] value) {bean.setJavadocLines(value); return this;}
    public FieldSpecs_Builder withNameForAccessor(String value) {bean.setNameForAccessor(value); return this;}
    public FieldSpecs_Builder withNameForField(String value) {bean.setNameForField(value); return this;}
    public FieldSpecs_Builder withTypeInvocation(String value) {bean.setTypeInvocation(value); return this;}
}

