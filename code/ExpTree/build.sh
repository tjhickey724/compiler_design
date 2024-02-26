#!/usr/bin/bash
java -classpath ../javacc.jar javacc ExpTree.jj
javac ExpTree.java
