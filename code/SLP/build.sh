#!/usr/bin/bash
java -classpath ../javacc.jar jjtree SLP.jjt
java -classpath ../javacc.jar javacc SLP.jj
javac SLP.java
