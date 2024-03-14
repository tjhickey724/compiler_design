# Translating between languages
Once we have a parser that generates an Abstract Syntax Tree, we can use that tree to do interesting things such as
* pretty print
* minify (remove all unneeded white space and comments)
* list all variables and their types (i.e. make a Symbol Table)
* type check the program

We can also translate programs from the source language (in our case MiniC) to another language.

Modern Imperative languages all have the same core data types and constrol structures, and so it is
relatively easy to convert a program from MiniC into any of these other languages.  We can also try to
convert MiniJava to the other languages but there is more variation in the way programming languages handle
Objects...

In this note we show how to convert a MiniC program to a Pascal program.

## Compiling MiniC to Pascal
Pascal was the preeminent programming language for teaching in the 1970's and 1980's but was replaced by C in the 1990's.

Wikipedia has a nice article on [Pascal](https://en.wikipedia.org/wiki/Pascal_(programming_language))

and you can write and run Pascal programs using an online IDE at [https://onecompiler.com](https://onecompiler.com/pascal/4277cam7g)

We will translate the [fibs.c](./tests/fibs.c) program to [fibs.p](./tests/fibs.p) a Pascal program.

Take a moment to look at the Pascal program and write down some of the ways it differs, syntactically, from the MiniC program.

To create a MiniC to Pascal translator, we rename the file PP_Visitor.java to be Pascal_Visitor.java, and replace the
pretty printing visitor methods, with ones appropriate to Pascal.

One of the differences betwee C/Java and Pascal is the way variables are declared. In C and Java we declare an integer variable "x" as 
```
int x;
```
in Pascal we use the more wordy form
```
VAR x : INTEGER;
```
So, let's look at the code for pretty printing a VarDecl
```
   public Object visit(VarDecl node, Object data){
        int indent = (int) data; 
        String t = (String) node.t.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        return indentString(indent)+t + " "+i +";\n";
   }
```
In our Pascal_Visitor.java class we'll use the following instead:
```
public Object visit(VarDecl node, Object data){
        int indent = (int) data; 
        String t = (String) node.t.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        return indentString(indent)+"VAR"+ " "+i +":"+t+";\n";
   }
```
We only have to change a dozen of these methods to convert the pretty printer to a Pascal translator.

## Compiling MiniC to Python
Let's do the same exercise with Python.

We would like to translate the [fibs.c](./tests/fibs.c) program to [fibs.py](./tests/fibs.py) a Python program.

List some of the syntactic differences between these two programs.

