Alertify4J
=======

Better notifications and alerts for Java applications. This code is very messy and could be done way better, but this is just the first version.

The name is temporary, but originates since the alert style was loosely based off alertify.js.

Building
========

Requires [Universal Tween Engine](https://github.com/nikkiii/universal-tween-engine) and Java 1.8 (Lambdas). Use either IntelliJ or Eclipse to build, unless you setup a local repository for universal-tween-engine's jar.

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