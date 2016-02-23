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

Soya has some syntax like this:

    lst := [1, 3, 'Apple', 'Peter', 2, 'House', 14]
    lst[String] = 'X'
    lst[int v] = v * 2
    println(lst)  // Output: [2, 6, "X", "X", 4, "X", 28]

A more complex sample:

    users :=
       * name: 'Peter'
         age: 16
       * name: 'Marry'
         age: 12
       * name: 'Scott'
         age: 15

    users[age: 14..20].each { println(it.name) }

    // Output:
    // Peter
    // Scott

    users.each {
       match it
         | age: 0..50   -> println("{it.name} is young")
         | age: 50..100 ->  println("{it.name} is old")
    }

    // Output:
    // Peter is young
    // Marry is young
    // Scott is old

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

### How to contribute
Forking and sending a pull request: https://github.com/mySingleLive/soya.

Discussion: https://github.com/mySingleLive/soya/issues.

### License (BSD License)
Copyright (c) 2013-2014 Jun Gong

