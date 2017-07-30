# Sporniket-Javabeans
A project to encapsulate hierarchised Java POJO structures into Javabeans replicating the hierarchy, a.k.a. "Yet Another Javabeans Generator".

Before Septembre 2017, it WAS
a project to generate Javabeans from a XML model. There is a legacy branch in the git repository for interested people.

TODO
use normalized README.

## Features

Sporniket Javabeans requires at least a JDK 8, and is tested on JDK 8.

###DONE
* generate Javabeans with basic getter/setter (not bounded, not constrained)
* fluent builder api, e.g. ```bean = MyBeautifulBean.build().withId(...).withDescription(...).done() ;```

###TO DO
* reverse engineering of existing simple Javabeans hierarchy, to convert existing projects.
* replicate javadoc tags when appropriate : descriptions, deprecation, properties summary, ...
* can generate boundable and constrainable properties (will use annotation)
* allows true encapsulation of collections and maps (idem)
* the fluent builder api 'done()' method name may be specified.

# How to use the generator in a maven project

The generator is a doclet, hence setting a call to the javadoc maven plugin with the rigth parameters for the "generate-sources" phase will do the job.

## Javadoc plugin call

TODO : call to maven plugin using plugin dependencies to sporniket-javabean-doclet

## Pojo location

In the packages where you want to generate the javabeans.

## Pojo requirements

The pojo and it's properties MUST be accessible : public or package private. If no code depends directly on the pojo, it is recommended to make it package private, as well as it's properties.

