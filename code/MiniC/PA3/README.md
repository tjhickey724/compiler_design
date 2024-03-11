# Programming Assignment 3 - Pretty Printing MiniJava
The purpose of this assignment is to have you learn how to write a pretty printer by modifying a
recursive descent LL(k) parser (from [PA2](../PA2/README.md).

Recursive Descent in a very useful and common pattern in programming and this assignment will give
you some experience using this approach to do something useful -- pretty printing a program. The same
approach can be used to cross compile to another language, including machine language, but for full
compilation we will use a better approach - first create the abstract syntax tree and then create modules
that traverse that tree to perform the many different stages of compilation.  For now, though, the
assignment is just to modify the code from [PA2](../PA2/README.md) to have it pretty print in addition to parse.

I have given you, in this folder, a pretty printer (MiniC_v3.jj) for a subset of MiniJava called MiniC.
You see the definitions of both languages at this link: [MiniC vs MiniJava](../MiniCvsMiniJava.md)
Your goal is to modify this pretty printer so that it works for MiniJava rather than MiniC.

# Steps
## Step 1. Download needed files
* Download the file [MiniC_v3.jj](./MiniC_v3.jj) and rename it to be PA3.JJ, also replace all occurrences of "MiniC_v3" in the "jj" file with "PA3"
* Download the folder [tests](./tests), to your computer (or download this entire MiniC folder!)
* Also find the javacc.jar file from [https://javacc.github.io/javacc/](https://javacc.github.io/javacc/) and download it to your folder.

## Step 2. Test the code you downloaded
Test the code as it is using the following commands...
```
java -classpath javacc.jar javacc PA3.jj
java PA3.java
java PA3 < tests/hello.c
java PA3 < tests/fibs.c
```
it should pretty print these two programs with no errors.
To make sure your code is working correctly, you should compile and run the original code and the pretty printed code to see that they have the same results.
e.g. first see what fibs.c prints when your run it
```
cd tests
cc fibs.c
./a.out
```
The do the same with the result of pretty printing fibs.c
```
java PA3 < tests/fibs.c > fibs.c
cc fibs.c
./a.out
```
When you have extended your code to work with MiniJava instead of C, you can run the same tests with java instead of C
using the test file tests/mjPPa.java
```
cd tests
javac mjPPa.java
java mjPPa
```
```
java PA3 < tests/mjPPa.java > mjPPa.java
javac mjPPa.java
java mjPPa
```

## Step 3. Modify the PA3.jj file
Modify the PA3.jj file to use the MiniJava rules as described in [MiniC vs MiniJava](../MiniCvsMiniJava.md)
You can copy the new MiniJava rules you wrote for the parser PA2.jj to your PA3.jj and add code to have it pretty print.
For example, the code for parsing the "Start" symbol in PA2 is
```
void Start():
{}
{
  MainClass()
  ClassDeclList()
}
```
To get this to pretty print, we just modify the formals to pass in the indentation level "n"
and pass that level to the MainClass and ClassDeclList methods.
```
void Start(int n):
{}
{
  MainClass(n)
  ClassDeclList(n)
}
```
You have to do this to all of the rules.  For some rules you will need to add print statements.
The main class in the PA3.jj file defines some printing functions you can use:
```
    static void indent(int indent_count){
        System.out.print(String.format("\n%1$"+4*indent_count+"s", ""));}   
    static void print(String s){ System.out.print(s); }
    static void space(){System.out.print(" "); }
```
For example, the rule for VarDecl in PA2 is
```
void VarDecl() :
{}
{ 
  Type() Identifier() <SEMICOLON>
}
```
To turn this into a pretty printing rule we need to pass in the indent level, and print out
some indentation spaces, and a space between the type and the identifier, and print out the semicolon.
```
void VarDecl(int n) :
{}
{ 
  {indent(n);} Type()
  {space();} Identifier()
  <SEMICOLON> {print(";")}
}
```
There are about 8-10 rules to add and/or modify to turn the MiniC parser into a MiniJava parser.

The rest of the changes are similar... It is actually quite easy to generate this pretty printer!

## Step 4. Test your code
Write some MiniJava programs in your test folder and use them to see if the parser is working correctly.
You should try the "tests/mjPP.java" and "tests/mjPPa.java" files. They contain essentially the same program
but the second has had all extra spaces and newlines returned. It is valid Java, but is unreadable!

## Step 5. Submit your PA3.jj 
Use the Mastery Learning App to upload your PA3.jj with a comment containing linkes to 60 second movies as requested in class.
 * show yourself
     * compiling PA3.jj to get PA3.java,
     * compiling that, to get PA3.class, then
     * printint out tests/mjPPa.java to show it is not pretty printed
     * finally, running PA4 on the tests/mjPPa.java to show it is pretty printed
     * and testing it a shown above...
 * explain how you transformed one of the grammar rules to a pretty printer, as if you were teaching a classmate how to do this assignment! 



