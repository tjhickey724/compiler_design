# Example of the PA5 MiniC compiler at work

Here we give an indepth example of how the PA5 compiler generates code by recursively traversing the abstract syntax tree.

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
.globl _main
_main:
# formals
# # _main_x->16(%rbp)

# prologue
pushq %rbp
movq %rsp, %rbp
#locals
# _main_b->-8(%rbp)
# _main_a->-16(%rbp)
#make space for locals on stack
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
