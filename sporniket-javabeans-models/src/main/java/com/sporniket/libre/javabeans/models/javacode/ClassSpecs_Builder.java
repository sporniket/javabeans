package com.sporniket.libre.javabeans.models.javacode;

import java.util.Collection;
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
public class ClassSpecs_Builder {
    private final ClassSpecs bean ;

    public ClassSpecs done() {return bean ;}

    /**Default constructor. 
     */
    public ClassSpecs_Builder() {bean = new ClassSpecs() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public ClassSpecs_Builder(ClassSpecs newBean) {bean = newBean ;}

    public ClassSpecs_Builder withAbstractRequired(boolean value) {bean.setAbstractRequired(value); return this;}
    public ClassSpecs_Builder withAnnotations(List<AnnotationSpecs> value) {bean.setAnnotations(value); return this;}
    public ClassSpecs_Builder withClassName(String value) {bean.setClassName(value); return this;}
    public ClassSpecs_Builder withClassNameFullyQualified(String value) {bean.setClassNameFullyQualified(value); return this;}
    public ClassSpecs_Builder withClassNameOutput(String value) {bean.setClassNameOutput(value); return this;}
    public ClassSpecs_Builder withDeclaredTypeArguments(String value) {bean.setDeclaredTypeArguments(value); return this;}
    public ClassSpecs_Builder withFields(List<FieldSpecs> value) {bean.setFields(value); return this;}
    public ClassSpecs_Builder withImports(Collection<ImportSpecs> value) {bean.setImports(value); return this;}
    public ClassSpecs_Builder withInterfaceList(String value) {bean.setInterfaceList(value); return this;}
    public ClassSpecs_Builder withInvokedTypeArguments(String value) {bean.setInvokedTypeArguments(value); return this;}
    public ClassSpecs_Builder withJavadocLines(String[] value) {bean.setJavadocLines(value); return this;}
    public ClassSpecs_Builder withPackageName(String value) {bean.setPackageName(value); return this;}
    public ClassSpecs_Builder withSuperClassName(String value) {bean.setSuperClassName(value); return this;}
}

