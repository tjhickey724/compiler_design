#!/usr/bin/bash
java -classpath ../javacc.jar javacc MiniC_v1.jj
javac MiniC_v1.java
#javac MiniC_Visitor.java
java MiniC_v1 < ../progs/fibs.c

