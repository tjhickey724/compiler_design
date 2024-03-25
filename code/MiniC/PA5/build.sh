#!/usr/bin/bash
java -classpath ../../javacc.jar javacc PA5.jj
javac PA5.java
javac CodeGen_Visitor.java
echo "compiled java files"
cp tests/test0.c tests/demo.c
java PA5 < tests/demo.c > tests/demo.s
cd tests; gcc demo.s print.s -o demo; ./demo

