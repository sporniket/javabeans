package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

public class AnnotationParameterSpecsValuesArray_Builder {
    private final AnnotationParameterSpecsValuesArray bean ;

    public AnnotationParameterSpecsValuesArray done() {return bean ;}

    /**Default constructor. 
     */
    public AnnotationParameterSpecsValuesArray_Builder() {bean = new AnnotationParameterSpecsValuesArray() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public AnnotationParameterSpecsValuesArray_Builder(AnnotationParameterSpecsValuesArray newBean) {bean = newBean ;}

    public AnnotationParameterSpecsValuesArray_Builder withValues(List<AnnotationParameterSpecsSingleValue> value) {bean.setValues(value); return this;}
    public AnnotationParameterSpecsValuesArray_Builder withName(String value) {bean.setName(value); return this;}
}

