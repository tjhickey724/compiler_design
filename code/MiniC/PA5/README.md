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

|Node | Grammar Rule |
| --- | --- |
|Assign|  Var = Exp|
|Block  | \{StatementList\}|
|ExpGroup| (Expr)|
|Formal | Type Id|
|IdentifierExp | Var|
|IntegerLiteral | N|
|MethodDecl  |    Type Id (Formals) { VarDecls Statements return Expr}|
|Minus | Expr - Expr|
|Plus  | Expr + Expr|
|Print | print(Expr)|
|Times | Expr * Expr|
|VarDecl | Type Id;|

FormalList ...
MethodDeclList ...
StatementList  ...
VarDeclList  ....

