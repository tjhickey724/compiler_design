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
Similarly for the Local Variables.

## Compiling Expressions
Binary Operator Expressions  ```A op B``` are evaluated using the stack assuming that A and B
are the top two elements of the stack, and after this operation they will be popped off
and their sum, C, will be pushed on the stack..

Let's see how this is done in code:

```
   public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String timesCode = 
        
        e1Code
        + e2Code
        + "# times:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "imulq %rdx, %rax\n"
        +   "pushq %rax\n";

        return timesCode; 
    }
```
Integer literals will be compiled by pushing them onto the stack:
```
public Object visit(IntegerLiteral node, Object data){ 
        // push the constant into the stack
        int i=node.i;

        String result = 
        "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $" + i+"\n";

        return result; 
    }

```
Identifier expressions are compiled by looking up their location in the stack
and then pushing it onto the stack 
```

    public Object visit(IdentifierExp node, Object data){ 
        // lookup location of identifier in stack
        // push that value onto the stack
        String s=node.s;
        // lookup the location of the identifier
        String varName = currClass+"_"+currMethod+"_"+s;
        String location = varMap.get(varName);
        // push it into the stack
        String result = 
            "#"+node.accept(ppVisitor,0)+"\n"
            + "pushq "+location+" #"
            + "  "+node.accept(ppVisitor, 0)
            +"\n";

        return result ; 
    }

```

## Statements
Our version of MiniC has two kinds of statements:
* print statements
* assignment statements

### Assignment Statements
To compile an assignment statement (Id = Expr)
we compile the expression into code "expCode" which
evaluates the expression and stores the result at the top of the stack.
Then we pop the stack into a register (%rax$) and move it to
the location of the identifier in the Frame.
```
 public Object visit(Assign node, Object data){ 
        Identifier i=node.i;
        Exp e=node.e;
        //node.i.accept(this, data);
        String expCode = (String) node.e.accept(this, data);
        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = varMap.get(varName);

        String result =            
            expCode
            + "#"+node.accept(ppVisitor,0)+"\n"
            + "popq %rax\n"
            + "movq %rax, "+location+"\n";     
        return result; 
    }
```

### Print Statements
These are even easier, to compile "print(Exp)" we first generate code to
evaluate the expression and push the value into the stack. Then we pop the
stack into %rdi, which is the 1st argument register in the x86 C calling convention
and call _print
```
    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        String expCode = (String) node.e.accept(this, data);
        String result =     
            expCode
        + "# "+node.accept(ppVisitor, 0)
        +   "popq %rdi\n"
        +   "callq _print\n";
        return result;
    }

```

### Conditional Expressions
We will compile conditional expressions (A<B) into code which will 
* pop A and B off of the stack
* if A<B it will push 1 on the stack
* else it will push 0 on the stack
Here is the code for this operation:
``` java

    public Object visit(LessThan node, Object data){ 
        /* We implement this in an inefficient but simple way.
         * where we evaluate the two expressions in A<B
         * and push them into the stack as values "a" and "b"
         * then we do a comparison of "a" and "b"
         * and if "a"<"b" we push 1 onto the stack, else we push 0
         * So the result of LessThan is either 1 (if true) or 0 (if false)
         * This is what C does, it treats 0 as False and nonzero as True
         */
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return 
         "# "+node.accept(ppVisitor, data)+"\n"
         + e1Code
         + e2Code
         + "# compare rdx<rax and push 1 on stack if true, 0 else\n"
         + "popq %rdx\n"      // pop 1st expr value into rdx
         + "popq %rax\n"      // pop 2nd expr value into rax
         + "cmpq %rdx, %rax\n"// compare the two values
         + "jge "+label1+"\n" // if rdx >= rax jump to label1
         + "pushq $1\n"       // otherwise rdx<rax so push 1 on stack
         + "jmp "+label2+"\n" // and jump to the end of this code
         + label1+":\n"       // in this case, rdx is not < rax, so
         + "pushq $0\n"       // push 0 on stack
         + label2+":\n" ;      // both branches end up here
    }
```
Observe that we need to create new labels  (label1 and label2) for the jump destinations.
At the end of this code, 1 is on the stack if e1<e2 and 0 is on the stack otherwise.

## Expanding our language to include if statements, booleans, and comparisons
### Conditional statements: if/then/else
We compile the ```if Expr then S1 else S2 statement``` into code which
evaluates the expression (which pushes either 1 for True or 0 for False on the stack)
and then comparing that value to 1 to decide if to jump to S2 or continue to S1.
Here is the code:
```
    public Object visit(If node, Object data){ 
        // evaluate e, jmp to s1 if true, s2 if false, 
        // jump to end after either case
        Exp e=node.e;
        Statement s1=node.s1;
        Statement s2=node.s2;
        String eCode = (String) node.e.accept(this, data);
        String thenCode= (String) node.s1.accept(this, data);
        String elseCode = (String) node.s2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return 
         "# conditional statement\n" 
         + eCode 
         + "popq %rax\n"
         + "cmpq	$1, %rax\n"
         + "jne "+label1+"\n"
         + thenCode
         + "jmp "+label2+"\n"
         + label1+":\n"
         + elseCode
         + label2+":\n";   
    }
```
Here again we need to create two labels (label1 and label2) for the jump points.
We see if the expression is not 1, in which case we jump to the "else statement" at label1
otherwise we run the "then statement" and then jump to the end of this conditional at label 2.



## Arrays in MiniC
We will implement arrays in MiniC in the Java way where an array of n longs will be allocated
8*(n+1) bytes in the heap. The first 8 bytes will store the size of the array and the element a[i]
will be in the 8 bytes starting at 8*(i+1).

[Arrays in MiniJava](Arrays%20in%20Java.pdf)

### Allocating space in the heap in x86-64
To allocate space in the heap we need to use the System call _malloc whose one argument %rdi is the number
of bytes to allocate. The address of the first byte in that region of memory is returned in %rax.

Here is a slide on [malloc](./malloc.pdf)

### Array Indexing
Once we have allocate 8*(size+1) bytes in the heap to store the array "a" of longs,
we can access the element a[i] using the following x86 syntax
```
(%rax, %rcx, $8)
```
where
* %rax is the address of the first byte of the array
* %rcx is the index of the entry we want to access (i+1)
* $8 is the size of each element

For example, to move 200 into a[10] we use the following code
```
movq $10, %rcx   # store 10 in %rcx
incq %rcx   # increment it, so 11 is not in %rcx
movq $200, (%rax, %rcx, $8)
```
Likewise, to move a[10] to %r7 we use the following code
```
movq $10, %rcx   # store 10 in %rcx
incq %rcx   # increment it, so 11 is not in %rcx
movq (%rax, %rcx, $8), %r7
```
To store the length of the array "a" in %r8, 
we access the quad value stored in the first 8 bytes of the array
```
movq (%rax,$rcx,), %r8
```

Here are some slides on working with arrays in x86-64
* [Array Manipulation](./Array%20manipulation%20in%20x86-64.pdf)
* [Array Indexing](./Array%20indexing.pdf)





# PA5 - Adding loops and arrays to MiniC
You will need to implement the True and False literals (as 1 and 0 respectively),
as well as the "And" nodes corresponding to the logical conjunction operation e.g.,
```
(0<x) && (x<10)
```
You will also need to implement the "While" node corresponding to while loops, e.g.
```
while(x>0) { print(x); x = x-1; }
```

More precisely, for PA5 you will need to modify the CodeGen_Visitor so that it generates
code for the following Abstract Syntax Tree nodes, and to demonstrate that your code works
on some sample programs.

### While statements
You should be able to use the techniques shown for the if statement to generate code for the following
Abstract Syntax Tree nodes
* While
* And
* Not

### Array handling
With these instructions, you should be able to generate code for the following
Abstract Syntax Tree nodes
* ArrayLength
* ArrayAssign
* ArrayLookup
* NewArray

# Implementing the C Calling Convention
The last part of the MiniC compiler is to incorporate function calls.  We will use the C calling convention for X86-64
in which the first 6 parameters are passed in registers and the remaining ones are passed on the stack. To simplify the
code, we will assume that no function has more than 6 parameters.

One of the challenges of compiling function calls for the Mac is that the system requires that the stackpointer (%rsp)
be aligned on a 16 byte boundary for each function call. That is, %rsp should be divisible by 16 (i.e. its last digit in
hex should be a zero). In our compiler we assume all variables are long integers or addresses, both of which take 8 bytes
so our stack is automatially aligned on 8 byte boundaries, but to get 16 byte alignments is a little tricky with the way
we have implemented expression evaluation.

[The Frame Layout](./frameLayout.png) for a function in our implementation will store the values of the
arguments a1,..., ar in the first r slots of the frame, followed by spaces for the local variables b1,...,bs
and the stack pointer will increased by (8*(r+s)) but rounded up to the nearest 16 byte boundary!





