#!/usr/bin/bash
java -classpath ../../javacc.jar javacc PA4.jj
javac PA4.java
#javac MiniC_Visitor.java
#javac Pascal_Visitor.java
#javac SymbolTable.java
java PA4 < tests/Demo.java

