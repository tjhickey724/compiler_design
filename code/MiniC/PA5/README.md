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
Let's consider how to compile the following MiniC program to X86 assembly language:
``` Java
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
We will focus only on the main method. When we run the MiniC_v5.jj parser on this, it will generate the following syntax tree
for this program:
```
MethodDeclList
    MethodDecl
        IntegerType                           int
        Identifier main                           main
        FormalList                                   (
            Formal
                IntegerType                              int
                Identifier x                              x
                                                    )
                                              {
        VarDeclList
            VarDecl               
                IntegerType                        int
                Identifier b                                b
            VarDeclList
                VarDecl
                    IntegerType                     int
                    Identifier a                           a
        StatementList
            StatementList
                StatementList
                    StatementList
                        StatementList
                            Print                     print(
                                IdentifierExp x            x );
                        Assign               
                            Identifier a               a =
                            IntegerLiteral 10               10 ;
                    Assign
                        Identifier b                    b =
                        IntegerLiteral 7                     7;
                Assign
                    Identifier x                        x =
                    Times
                        ExpGroup                             (
                            Plus
                                IdentifierExp a                a
                                                              +
                                IdentifierExp b                b
                                                             )
                                                            * 
                        ExpGroup                            (
                            Minus                              
                                IdentifierExp a               a
                                                             -
                                IdentifierExp b               b
                                                            );
            Print                                    print(
                IdentifierExp x                           x);
        IdentifierExp x                              return x;
```
Our compiler will generate the following code for this program (with comments inserted by the compiler)
```
# prologue
.globl _main
_main:
# 
pushq %rbp
movq %rsp, %rbp
subq $16, %rsp
# x 
pushq 16(%rbp) #   x 
# print( x );
popq %rdi
callq _print
# 10 
pushq $10
#a =  10 ;

popq %rax
movq %rax, -16(%rbp)
# 7 
pushq $7
#b =  7 ;

popq %rax
movq %rax, -8(%rbp)
# a 
pushq -16(%rbp) #   a 
# b 
pushq -8(%rbp) #   b 
# plus: a  +  b 
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
# a 
pushq -16(%rbp) #   a 
# b 
pushq -8(%rbp) #   b 
# minus: a  -  b 
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
# times:( a  +  b ) * ( a  -  b )
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
#x = ( a  +  b ) * ( a  -  b );

popq %rax
movq %rax, 16(%rbp)
# x 
pushq 16(%rbp) #   x 
# print( x );
popq %rdi
callq _print
# calculate return value
# x 
pushq 16(%rbp) #   x 
# epilogue
popq %rax
addq $16, %rsp
movq %rbp, %rsp
popq %rbp
retq
```
