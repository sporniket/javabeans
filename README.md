# Sporniket-Javabeans

> [WARNING] Please read carefully this note before using this project. It contains important facts.

Content

1. What is **Sporniket-Javabeans**, and when to use it ?
2. What should you know before using **Sporniket-Javabeans** ?
3. How to use **Sporniket-Javabeans** ?
4. Known issues
5. Miscellanous

## 1. What is **Sporniket-Javabeans**, and when to use it ?
**Sporniket-Javabeans** is a project to generate Javabeans from a tree of Java POJO structures. The generated tree of Javabeans replicates the POJO tree, supports generics. Additionnally, a fluent builder class is generated for each non abstract generated Javabeans.

*Before Septembre 2017, it WAS
a project to generate Javabeans from a XML model. Interested people may checkout the v15.04.00 or v15.04.01 and fork from there.*

### TODO list, a.k.a. "The roadmap"

* replicate javadoc tags when appropriate : descriptions, deprecation, properties summary, ...
* can generate boundable and constrainable properties (will use annotation)
* allows true encapsulation of collections and maps (idem)
* the fluent builder api 'done()' method name may be specified.
* replicate annotations (springframework, ...) if possible, or provide a set of annotations to generate annotations on the generated code (classes and fields) (supported by the reverse-engineering doclet).


### What's in v17.09.00
* generate Javabeans with basic getter/setter (not bounded, not constrained).
* fluent builder api, e.g. ```bean = new MyBeautifulBean_Builder.withId(...).withDescription(...).done() ;```
* reverse engineering of existing simple Javabeans hierarchy, to convert existing projects.

### Licence
 **Sporniket-Javabeans** is free software: you can redistribute it and/or modify it under the terms of the
 GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 option) any later version.

 **Sporniket-Javabeans** is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 more details.

 You should have received a copy of the GNU Lesser General Public License along with **Sporniket-Javabeans**.
 If not, see http://www.gnu.org/licenses/ .


## 2. What should you know before using **Sporniket-Javabeans** ?
**Sporniket-Javabeans** relies only on standard jdk 8 and consists of :

* **Sporniket-Javabeans-doclet** : contains ```ExpanderDoclet``` to generate Javabeans, and ```DistillerDoclet``` to reverse-engineer POJOs.

> Do not use **Sporniket-Javabeans** if this project is not suitable for your project

## 3. How to use **Sporniket-Javabeans** ?

### How to write POJO structures from scratch.

* Write package private code, unless your POJOs tree spans on multiple packages. *There is less typing when writing package private code.*
* A POJO is suffixed, the default suffix is ```Raw```.
* Only public or package private fields will be the fields of a generated Javabean.

*See the test package ```test.sporniket.libre.javabeans.core.pojo.testsuite``` for a few samples.*


### Manually generate javabeans

Invoke javadoc with the doclet ```com.sporniket.libre.javabeans.doclet.ExpanderDoclet``` on the package list containing the POJOs. Use ```-d <path>``` option to specify the target directory for generated source code. Use ```-private``` to be able to scan the POJOs.

```
javadoc -sourcepath path/to/source -d target/generated-sources/javabeans -private -doclet com.sporniket.libre.javabeans.doclet.ExpanderDoclet my.package.with.pojos
```

### Manually reverse-engineer POJOs

Invoke javadoc with the doclet ```com.sporniket.libre.javabeans.doclet.DistillerDoclet``` on the packages containing the javabeans. Use ```-d <path>``` option to specify the target directory for generated source code. Use ```-private``` to be able to scan the POJOs.

```
javadoc -sourcepath path/to/source -d target/generated-sources/javabeans -private -doclet com.sporniket.libre.javabeans.doclet.DistillerDoclet my.package.with.javabeans
```

### Maven
Call the javadoc plugin during ```generate-sources``` like described for the manual procedure, the plugin dependency will be :

```
<dependency>
	<groupId>com.sporniket.javabeans</groupId>
	<artifactId>sporniket-javabeans-doclet</artifactId>
	<version>17.09.00</version>
</dependency>
```

### Directions and sample code
Read the javadoc and look at the test code.

## 4. Known issues
See the [project issues](https://github.com/sporniket/javabeans/issues) page.

## 5. Miscellanous
### Report issues
Use the [project issues](https://github.com/sporniket/javabeans/issues) page.
