# Sporniket-Javabeans
A project to encapsulate hierarchised Java POJO structures into Javabeans replicating the hierarchy, a.k.a. "Yet Another Javabeans Generator".

WAS
a project to generate Javabeans from a XML model.

TODO
use normalized README.

## Features

Sporniket Javabeans requires at least a JDK 8, and is tested on JDK 8.

###DONE
* generate Javabeans with basic getter/setter (not bounded, not constrained)

###TO DO
* reverse engineering of existing simple Javabeans hierarchy, to convert existing projects.
* can generate boundable and constrainable properties (will use annotation)
* allows true encapsulation of collections and maps (idem)
* fluent builder api, e.g. ```bean = MyBeautifulBean.build().withId(...).withDescription(...).done() ;```

# How to use the generator in a maven project

The generator is a doclet, hence setting a call to the javadoc maven plugin with the rigth parameters for the "generate-sources" phase will do the job.

## Javadoc plugin call

TODO : call to maven plugin using plugin dependencies to sporniket-javabean-doclet

WAS

As of now, there is no central repository to get the plugin. Thus one should clone the git repository and build/install the plugin, and before that do the same for a required library :

```
git clone https://github.com/sporniket/core.git
git checkout v15.03.00.01-jdk6
cd core/sporniket-core
mvn install

cd ../..

git clone https://github.com/sporniket/javabeans.git
cd javabeans/sporniket-javabeans
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

## Pojo location

In the packages where you want to generate the javabeans.

