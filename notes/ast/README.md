# Abstract Syntax Trees

The goal of the front end of a compiler is to produce an abstract syntax tree (AST) representing the structure of the program and
then to convert that AST into some sort of Intermediate Representation structure that can be converted to assembly language by the back end.

There are tools, such as [jjTree](./jjTree.md) for automatically gnerating abstract syntax trees, but we will show how to build them directly
which gives us more control over the structure of the trees.

## Generating Abstract Syntax Trees
We begin with showing how to use javacc to generate ASTs for miniJava expressions
* [ExpTree](./ExpTree.md)

## Introducing miniC
Then we introduce a subset of miniJava we will use for the homework assignments
* [miniC langauge](./miniC.md)

We will be asking you to extend miniC programs to miniJava programs for the remaining PAs.
This will require you read and understand the miniC compiler components well-enough that you
can write the corresponding components for miniJava. Writing a compiler is a lot of work
and this should help you focus on the ideas rather than getting bogged down in the somewhat
repetitive nature of compiler code!

## Generating the miniC Abstract Syntax Trees
Our first step is to complete the front end of the compiler by converting the program
into an abstract syntax tree and then performing semantic analysis on the tree to
report on all (if any) Type Errors. 

The classes we use to build the abstract syntax trees for
miniJava and miniC are in the package syntaxtree.  
* [generating the miniC Abstract Syntax Tree](./miniC_AST.md)
* [printing the miniC AST](./miniC_printAST.md)
* [pretty printing the miniC program](miniC_prettyprint.md)
* [computing the miniC symbol table](./miniC_symbol_table.md)
* [type checking miniC](./miniC_type_checking.md)








