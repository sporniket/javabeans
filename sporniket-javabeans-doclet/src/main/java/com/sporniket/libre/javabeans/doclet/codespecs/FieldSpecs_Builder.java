package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

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

