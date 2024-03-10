#!/usr/bin/bash
java -classpath ../../javacc.jar javacc MiniC_v4.jj
javac MiniC_v4.java
#javac MiniC_Visitor.java
java MiniC_v4 < tests/fibs.c

