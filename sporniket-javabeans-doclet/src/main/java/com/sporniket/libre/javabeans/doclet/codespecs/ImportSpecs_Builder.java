package com.sporniket.libre.javabeans.doclet.codespecs;


public class ImportSpecs_Builder {
    private final ImportSpecs bean ;

    public ImportSpecs done() {return bean ;}

    /**Default constructor. 
     */
    public ImportSpecs_Builder() {bean = new ImportSpecs() ;}

    /**Constructor that delegates the bean instanciation. 
     * @param newBean the instanciated bean to use.
     */
    public ImportSpecs_Builder(ImportSpecs newBean) {bean = newBean ;}

    public ImportSpecs_Builder withAnnotation(boolean value) {bean.setAnnotation(value); return this;}
    public ImportSpecs_Builder withClassName(String value) {bean.setClassName(value); return this;}
    public ImportSpecs_Builder withDirectlyRequired(boolean value) {bean.setDirectlyRequired(value); return this;}
}

