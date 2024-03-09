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
You see the definitions of both languages at this link: [MiniC vs MiniJava](../MiniCvsMiniJava.md)

## Steps
Step 1. Download the file [PA2.jj](./PA2.jj) and the folder [test](./test), to your computer (or download this entire MiniC folder!)
Also find the javacc.jar file and download it to your folder.

Step 2. Test the code as it is using the following commands...
```
java -classpath javacc.jar javacc PA2.jj
java PA2.java
java PA2 < tests/hello.c
java PA2 < tests/hello_bug1.c
```
it should give no errors on the first test, but find an syntax error on the second.

Step 3. Modify the PA2.jj file to use the MiniJava rules as described in [MiniC vs MiniJava](./MiniCvsMiniJava.md)
There are about 8-10 rules to add and/or modify to turn the MiniC parser into a MiniJava parser

Step 4. Write some MiniJava programs in your test folder and use them to see if the parser is working correctly.

Step 5. Submit your PA2.jj with movies as requested in class.
 * show yourself compiling PA2.jj to get PA2.java, then compiling that, to get PA2.class
 * show yourself running PA2 on at least two inputs one that parses and one that doesn't
 * explain how you implemented one of the grammar rules (e.g. class) in detail



