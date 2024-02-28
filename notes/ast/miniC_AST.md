# Generating the miniC Abstract Syntax Tree

The classes we will use to create the miniC AST (and the miniJava AST) are in the package syntaxtree
* [syntaxtree package](../../code/MiniC/syntaxtree)

We have one class for each grammar rule in the miniJava language. Since miniC is (essentially) a subset
of miniJava, we can use the same classes for miniC, but we won't use all of them. For example, a miniC
AST will not have any ClassDecl nodes or any ArrayAssign nodes.

We will use javacc to create the abstract syntax tree and then to apply several visitors to the tree
to print it, generate a symbol table, and run a type check.

## javacc
Here is a link to the javacc program to create the miniC AST
* [miniC.jj](../../code/MiniC/MiniC.jj)

  
