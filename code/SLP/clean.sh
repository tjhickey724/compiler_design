#! /usr/bin/bash
mkdir save
mv *DumpVisitor.java save
rm *.class
rm *.jj
rm *.java
mv save/* .
rmdir save

