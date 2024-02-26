#! /usr/bin/bash
mkdir save
mv *Visitor.java save
rm *.class
rm *.java
rm syntaxtree/*.class
mv save/* .
rmdir save

