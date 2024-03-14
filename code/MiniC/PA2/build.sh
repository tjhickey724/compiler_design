#!/usr/bin/bash
java -classpath ../../javacc.jar javacc MiniC_v2.jj
javac MiniC_v2.java
java MiniC_v2 < tests/errors1.c

