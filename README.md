# Sporniket-Javabeans
a project to generate Javabeans from a XML model, support boundable and constrainable properties, allow true encapsulation of mutables objects, can be aware of collections and maps, fluent api support.

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
