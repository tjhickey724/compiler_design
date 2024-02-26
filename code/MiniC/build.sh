#!/usr/bin/bash
java -classpath ../javacc.jar javacc MiniC.jj
javac MiniC.java
javac MiniC_Visitor.java
java MiniC < ../progs/fibs.c

