#!/usr/bin/bash
java -classpath ../../javacc.jar javacc MiniC2Python.jj
javac MiniC2Python.java
javac MiniC2Python_Visitor.java
#javac Pascal_Visitor.java
#javac SymbolTable.java
java MiniC2Python < tests/fibs.c


