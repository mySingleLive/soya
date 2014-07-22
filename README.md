Soya Programming Language
===================================
Jun Gong (gongjun_dt@yahoo.com)

### Introduction
Soya is a programming language running on JVM. It has following features:
* Intuitive Syntax
* OOP features
* Functional Programming features
* Support many basic type literals (ex. collection, regular expression, file, url, date)
* Pattern Matching features
* Exception Handling
* Advanced Assignment

### Build and Install
To build everything using Gradle (the command below will download Gradle automatically, you do not need to download it first).

    ./gradlew installSoya

This will build project and generate the build/install folder, it contains soya jars, documentation, license and samples.

To build from IntelliJ IDEA:

    ./gradlew idea

Then open the generated project in IDEA.

To build from Eclipse:

    ./gradlew eclipse

Then import the generated project in Eclipse.

### License (BSD License)
Copyright (c) 2013-2014 Jun Gong

