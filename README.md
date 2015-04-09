# Sporniket-Javabeans
a project to generate Javabeans from a XML model.

## Features

* can generate boundable and constrainable properties
* allows true encapsulation of collections and maps
* fluent api support.

# How to use the maven plugin

## Installation and plugin declaration

As of now, there is no central repository to get the plugin. One should clone the git repository and build/install the plugin :

```
git clone https://github.com/sporniket/javabeans.git
cd sporniket-javabeans
mvn install
```

Then, the plugin can be declared in the project poms :

```maven
<!-- For generating javabeans -->
<plugin>
	<groupId>com.sporniket.javabeans</groupId>
	<artifactId>sporniket-javabeans-maven</artifactId>
	<version>[the version to use]</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>generate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

## Run the plugin

The plugin should be invoked during the build lifecycle.

Here is the manual invocation :

```
mvn com.sporniket.javabeans:sporniket-javabeans-maven:generate
```

## Model files

XML model files MUST be in *src/main/sporniket-javabeans*.

### Overall structure

An XML model file describe a set of javabeans (*BeanSet*), consisting of one or more *package* containing one or more *bean* made of one or more *property*. The XML grammar of the file is located at [http://schema.sporniket-studio.com/model/set/javabean].

Thus, a model file looks like this :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<BeanSet xmlns="http://schema.sporniket-studio.com/model/set/javabean">
	<package name="test.sporniket.javabean.generator.core">
		<bean name="MyUsefullBean">
			<property name="testJavaBasic" type="java:java.util.Date" mode="basic" />
			...
		</bean>
	</package>
</BeanSet>
```

### Element annotation

*BeanSet*, *package*, *bean* and *property* supports an *annotation* that will be converted into javadoc or comments in the generated code.

#### Beanset annotation

```xml
<?xml version="1.0" encoding="UTF-8"?>
<BeanSet xmlns="http://schema.sporniket-studio.com/model/set/javabean">
	<annotation>
		<author>David SPORN</author>
		<author>etc...</author>
		<licencenotice>This software is licensed under ...</licencenotice>
		<licencenotice>etc...</licencenotice>
		<version>1.0.5</version>
		<see>Boolean</see>
		<see>etc...</see>
	</annotation>
</BeanSet>
```


#### package and bean annotation

```xml
<?xml version="1.0" encoding="UTF-8"?>
<package name="test.sporniket.javabean.generator.core">
	<annotation>
		<summary>One phrase that will be seen in the summary table of the javadoc.</summary>
		<description>long description</description>
		<description>...</description>
		<see>Boolean</see>
		<see>etc...</see>
	</annotation>
</package>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<bean name="MyUsefullBean">
	<annotation>
		<summary>One phrase that will be seen in the summary table of the javadoc.</summary>
		<description>long description</description>
		<description>...</description>
		<see>Boolean</see>
		<see>etc...</see>
	</annotation>
</bean>
```

#### property annotation


```xml
<?xml version="1.0" encoding="UTF-8"?>
<property name="testJavaBasic" type="java:java.util.Date" mode="basic">
	<annotation>
		<summary>One phrase that will be seen in the summary table of the javadoc.</summary>
		<description>long description</description>
		<description>...</description>
		<see>Boolean</see>
		<see>etc...</see>
	</annotation>
</property>
```

### property definition

A *property* tag has 3 attributes :

* *name*
* *type*
* *mode* (optionnal)

#### name

This attribute is the property name, following the javabean convention : camel case, first letter is lower case.

#### type

This attribute specify the type of the property, and follows the pattern *type:def*, where *type* can be :

* *java* : then *def* is a Java class full name, e.g. "java.lang.String".
* *enum* : then *def* specifies an internal enumeration, following the pattern *name:value1,value2,...* ; *name* may be empty.
* *coll* : then *def* specifies a _collection_, following the pattern *interface:implementationClass:elementClass:mode*.
* *map* : then *def* specifies a _map_, following the pattern *implementationClass:keyClass:valueClass:mode*.

##### collection and map specifications

#### mode

This attribute specify the kind of Javabean property :

* *basic* : the classical getter/setter.
* *boundable* : the property can be listened for change (*PropertyChangeListener*)
* *vetoable* : the property can also be constrained (*VetoableChangeListener*)

