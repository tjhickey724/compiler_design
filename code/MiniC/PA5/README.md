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

## Compiler Architecture
The PA5 compiler maintains several "global" variables so that it can determine where the
variables of the program should be stored:

We maintain a few hashmaps 
 *  labelMap: to keep track of the labels for class methods
    *     this will map method names to their labels
 *  varMap: to keep track of the local variables in a method
    *     this will map variable names to their negative offsets from the base pointer
 *  paramMap: to keep track of the parameters of a method
    *     this will map parameter names to their positive offsets from the base pointer
  
We also need some auxiliary variables to keep track of 
 *  currClass - the current class
 *  currMethod - the current method
 *  labelNum - the current label number, for use in loops and conditionals
 *  stackOffset - the current stack offset, to keep track of temporaries
 *  formalOffset - the current formal offset, to keep track of parameters

``` java
    private HashMap<String, String> labelMap = new HashMap<String, String>();
    private HashMap<String, String> varMap = new HashMap<String, String>();
    private HashMap<String, String> paramMap = new HashMap<String, String>();
    private String currClass = "";
    private String currMethod = "";
    private int labelNum = 0;
    private int stackOffset = 0;
    private int formalOffset = 0;
    private Visitor ppVisitor = new PP_Visitor()
```

The Formals variables will be stored "above" the current stack frame and the Visitor code
assigns them a location:
``` java
    public Object visit(Formal node, Object data){ 
        // find location of formal in stack
        Identifier i=node.i;
        Type t=node.t;

        formalOffset += 8;
        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = formalOffset + "(%rbp)";
        varMap.put(varName, location);

        //node.i.accept(this, data);
        //node.t.accept(this, data);

        return "# " + varName+"->"+location+"\n"; 
    }
```
