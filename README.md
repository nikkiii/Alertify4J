Alertify4J
=======

Better notifications and alerts for Java applications. This code is very messy and could be done way better, but this is just the first version.

The name is temporary, but originates since the alert style was loosely based off alertify.js.

Building
========

Add the nikkii.us repository to your maven pom.xml:

	<repositories>
        <repository>
            <id>nikkiius</id>
            <name>Nikkii.us Repository</name>
            <url>http://maven.nikkii.us/maven2</url>
        </repository>
    </repositories>
    
And define Alertify4J as a dependency:

	<dependency>
        <groupId>us.nikkii.alertify4j</groupId>
        <artifactId>alertify4j</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

Example
========

Basic Example:

	Alertify.show(new AlertifyBuilder()
		.type(AlertifyType.SUCCESS)
		.text("Hello, world!")
		.autoClose(5000)
	.build());

Example with callback:

	Alertify.show(new AlertifyBuilder()
		.type(AlertifyType.SUCCESS)
		.text("Hello, world!")
		.callback((window) -> {
			// Callback code
		})
	.build());

What the result looks like
========
![Example](http://i.imgur.com/TM5PjL6.png)