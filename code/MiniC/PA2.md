# Programming Assignment 2
The purpose of this assignment is to have you learn how to write a recursive descent LL(k) parser 
using a parser generator. 

Parsing is a fundamental tool of Computer Science that is used whenever we are designing software that
needs to process commands defined in a formal language such as full programming languages or small
domain specific command languages.  Parsing is the first step in building a compiler and this project
will help you understand how to build LL(k) parsers and test them.  

We will use the javacc parser generator to build the parser rather than creating the parsing table by ourselves.

Your goal in this homework is to produce a parser for MiniJava.
I have given you, in this folder, a parser (PA2.jj) for a subset of MiniJava called MiniC.
You see the definitions of both languages at this link: [MiniC vs MiniJava](./MiniCvsMiniJava.md)

## Steps
Step 1. Download the file [PA2.jj](./PA2.jj), the [javacc.jar](../javacc.jar) file, and the folder [test](./test), to your computer (or download this entire MiniC folder!)

Step 2. Test the code as it is
```
java -classpath javacc.jar javacc PA2.jj
java PA2.java
java PA2 < tests/hello.c
java PA2 < tests/hello_bug1.c
```
it should give no errors on the first test, but find an syntax error on the second.

Step 3. Modify the PA2.jj file to use the MiniJava rules as described in [MiniC vs MiniJava](./MiniCvsMiniJava.md)



