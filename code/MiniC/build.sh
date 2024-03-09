#!/usr/bin/bash
java -classpath ../javacc.jar javacc MiniC_v2.jj
javac MiniC_v2.java
#javac MiniC_Visitor.java
java MiniC_v2 < ../progs/fibs.c

