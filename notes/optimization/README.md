# Compiler Optimizations

## Review of Naive Compilation
We have seen that it is relatively easy to create a compiler from MiniC or MiniJava to
X86-68 assembly language. Our approach was to
1. use an LL(k) Recursive Descent parser to generate an Abstract Syntax Tree
2. write visitors to generate a symbol table, type check, and to pretty print the program
3. write a visitor to generate code using only a few registers
   * rdi rsi rdx rcx r8 r9    for parameter passing (then copied into the stack)
   * rsp rbp for stack and frame management
   * rax for return values
4. implement expression evaluation using the stack
   * when evaluating a constant or variable, push the value onto a stack
   * when evaluating an expression, evaluate its arguments and push them on the stack,
     then pop them off into registers, and use an x86-64 operation to evaluate them
     and push the result back on the stack
5. implement control flow (if, while) using labels, comparisons (CMPQ),
   conditional jumps (JGEQ) and unconditional jumps (JMP)
6. implement array operations using the system call to malloc and indirect addressing (R1,R2,8)

## Sources of Inefficiency in Naive Compilation
This works but the code is much slower than it needs to be for a number of reasons
1. Inefficient use of registers - all data is moved from the registers into the stack and back
   and moving data from the stack into a register can take anywhere from 2 to hundreds of cycles
2. Redundant code - programmers often write code that results in simple expressions being evaluated
   more often than is needed
3. Use of more expensive machine operations than is needed, shifting bits left or right
   is faster than addition, which is faster than multiplication so
   imulq $2 R is the same as addq R R is the same as shlq $1 R but the latter takes only 1 cycle
   and the former can take many...
4. inefficient use of jumps  (sometime the compiler will generate a jump to a label,
   which has a jump to another label, etc, and this slows down the program.

## Architecture notes
To understand the tradeoffs in different choices for code generation, it helps to have an 
idea of the structure of modern computers, and especially their Central Processing Units (CPUs).

[Architecture Notes](architecture_notes.md) gives a brief overview of some of the features of modern computer that impact choice in code generation.

In the next three weeks we will show how to analyze programs to generate more efficient assembly
language. 

## Compiling to an Intermediate Representation

[Three Address Code](ThreeAddressCode.md) - the first step is to define an Intermediate Representation. We will use three address code, which assume infinitely many registers
and all operations for the form R1 <- op (R2, R3, ..., Rn)

## Converting the IR to a Directed Graph of Basic Blocks
[Basic Blocks](BasicBlocks.md) - once we have a program converted to 3 address code, we
need to decompose it into a directed graph of basic blocks, where each basic block has
a single entry point, and a conditional jump at the end (or a return for a function)

## Analyzing variable lifetimes in a Basic Block
[Liveness Analysis](LivenessAnalysis.md) - a key idea in register allocation is to know which
variables need to be stored in the memory at any given point in the program. Variables which won't be used again don't need to be in registers and hence their space can be occupied by another variable.  This is called liveness analysis and we first show how to do this for a basic block.

## Converting a Basic Block to a Directed Acyclic Graph
There are several optimizations that can be performed just on a single basic block
in isolations. Some of these are
* Common Subexpression elimination (e.g. dont' recompute a+b if you need it and a and b haven't changed)
* Dead Code Elimination (if you have an assignment x = ... but the value of x is never used before being
  redefined, then just eliminate that assignment
* Instruction level parallelism. Reorder the code so that multiple operations that don't depend on each other can be evaluated at the same time is separate arithmetic and logic units (ALUs) on the chip.

These optimizations can be implemented by 
[converting a basic block into a Directed Acyclic Graph](basicBlocks_as_DAGs.md) and then
compiling the three address code from this graph.

## Register Allocation
Optimal Register allocation is a very hard problem (NP-hard) but there are many heuristics that produce optimal code in particular situations or near optimal code in most situations. Here are a few:
* [Arithmetic Expressions with Ershov's algorithm](ershov.md)
* [Register Allocation by Graph Coloring](graphcoloring.md)


## Data Flow Analysis
The graph coloring register allocation method relies on knowing which variables are "live" for each instruction in the program and this requires extending our "liveness analysis" from a single basic block to the entire program. This can be done using a general technique known as data flow analysis, where we iteratively apply certain data flow equations to find a minimal (or maximal) solution as fixed point to those equations. 

The general theory involves finding fixed points of certain functions on DAGs. We'll demonstrate it with the liveness analysis.
* [Global Liveness Analysis](global_liveness.md)
* [General Data Flow Analysis](data_flow_analysis.md)


 
