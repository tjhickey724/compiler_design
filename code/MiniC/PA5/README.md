# Overview of PA5
This folder contains a compiler from MiniC to assembly code.
In this file, we'll explain how the compiler works. It is implemented
as a recursive descent compiler
   [CodeGen_Visitor.java](./CodeGen_Visitor.java)
using the Visitor pattern so we need
to specify how each of the nodes in the Abstract Syntax Tree correspond
to assembly language program.

For PA5 you will extend this code so that it also handles boolean values
and expressions (True, False, And, Not) as well as While statements
and Arrays.

## Abstract Syntax Trees of MiniC
Here is the subset of MiniJava that we need to process in our compiler.

|Node Class | Grammatical Contruct |
| --- | --- |
|Assign|  Var = Exp|
|Block  | \{StatementList\}|
|ExpGroup| \(Expr\)|
|Formal | Type Id;|
|IdentifierExp | Var|
|IntegerLiteral | N|
|MethodDecl  |    Type Id (Formals) \{VarDecls Statements return Expr;\}|
|Minus | Expr - Expr|
|Plus  | Expr + Expr|
|Print | print(Expr)|
|Times | Expr * Expr|
|VarDecl | Type Id;|
|FormalList|  Formal, Formal, ... , Formal|
|MethodDeclList| Method Method ... Method|
|StatementList|  Statement ... Statement|
|VarDeclList|  VarDecl ... VarDecl|

## Example 
Here is an example of a straightline MiniC program that can be compiled by the PA5 base code ..
```
#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int main(int x){
    int a;
    int b;
    print(x);
    a=10;
    b=7;
    x = (a+b)*(a-b);
    print(x);
    return x;
}
```
and we include a detailed explanation of the code in this link:
   [compiler_demo.md](./compiler_demo.md)


