package com.sporniket.libre.javabeans.doclet.codespecs;


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

