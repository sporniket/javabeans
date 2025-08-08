package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.Collection;
import java.util.List;

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

