package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

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

