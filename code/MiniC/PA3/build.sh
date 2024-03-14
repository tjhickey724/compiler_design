#!/usr/bin/bash
java -classpath ../../javacc.jar javacc MiniC_v3.jj
javac MiniC_v3.java
java MiniC_v3 < tests/errors1.c

