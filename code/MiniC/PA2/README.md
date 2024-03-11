# Programming Assignment 2 - Parsing MiniJava
The purpose of this assignment is to have you learn how to write a recursive descent LL(k) parser 
using the [javacc parser generator](https://javacc.github.io/javacc/)

Parsing is a fundamental tool of Computer Science that is used whenever we are designing software that
needs to process commands defined in a formal language such as full programming languages or small
domain specific command languages.  Parsing is the first step in building a compiler and this project
will help you understand how to build LL(k) parsers and test them.  

We will use the javacc parser generator to build the parser rather than creating the parsing table by ourselves.

Your goal in this homework is to produce a parser for MiniJava.
I have given you, in this folder, a parser (MiniC_v2.jj) for a subset of MiniJava called MiniC.
You see the definitions of both languages at this link: [MiniC vs MiniJava](../MiniCvsMiniJava.md)

# Steps
## Step 1. Download needed files
* Download the file [MiniC_v2.jj](./MiniC_v2.jj) and rename it to be PA2.JJ, 
* Download the folder [tests](./tests), to your computer (or download this entire MiniC folder!)
* Also find the javacc.jar file from [https://javacc.github.io/javacc/](https://javacc.github.io/javacc/) and download it to your folder.

## Step 2. Test the code you downloaded
Test the code as it is using the following commands...
```
java -classpath javacc.jar javacc PA2.jj
javac PA2.java
java PA2 < tests/hello.c
java PA2 < tests/hello_bug1.c
```
it should give no errors on the first test, but find an syntax error on the second.

## Step 3. Modify the PA2.jj file
Modify the PA2.jj file to use the MiniJava rules as described in [MiniC vs MiniJava](../MiniCvsMiniJava.md)
There are about 15 rules to add and/or modify to turn the MiniC parser into a MiniJava parser.
For example, the rule in MiniC for the START symbol is ```Start -> \<PREFACE\> MethodDeclList```
and the current code in PA2.jj for the START symbol is therefore
```
void Start():
{}
{
  <PREFACE>
   MethodDeclList()
  <EOF>
}
```
But the corresponding rule for MiniJava is ``` Start -> MainClass ClassDeclList```  so we must change this the
PA2.jj code to the following
```
void Start():
{}
{
  MainClass()
  ClassDeclList()
}
```
The rest of the changes are similar...

## Step 4. Test your code
Write some MiniJava programs in your test folder and use them to see if the parser is working correctly.
There are a few there for testing C programs, but  you need to add some for testing Java programs.
The file "mjPP.java" is a MiniJava program with many of the features of MiniJava that is reasonably will styled.
The file "mjPPa.java" is essentially the same program but with all linebreaks and extra spaces removed. 
Your program should accept both of them even though they look so different.


## Step 5. Submit your PA2.jj 
Use the Mastery Learning App to upload your PA2.jj with a comment containing linkes to 60 second movies as requested in class.
 * show yourself compiling PA2.jj to get PA2.java, then compiling that, to get PA2.class
and running PA2 on at least two inputs one that parses and one that doesn't
 * explain how you implemented one of the grammar rules (e.g. class) as if you were teaching a classmate how to do this assignment! 



