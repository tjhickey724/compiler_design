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
1. Modern CPUs have multiple arithmetic and logic units and can handle multiple additions
   at the same time, and multiple multiplications (though usually fewer than the additions).
   [The basic structure of a CPU](https://en.wikipedia.org/wiki/Central_processing_unit) has a register and an arithmetic/logic unit on the chip, together with a control unit that fetches the
   instructions and determines the next instruction. Each CPU may have multiple units for addition,
   multiplication, shifts, etc. and can process multiple instructions at a time.
   ![CPU architecture](https://upload.wikimedia.org/wikipedia/commons/3/3a/ABasicComputer.svg)
3. and they are [superscalar](https://en.wikipedia.org/wiki/Superscalar_processor),
   in the sense that they can prefetch multiple instructions at a time
   and then put them into a pipeline so that dozens of instructions can be in different stages of
   execution at the same time.  Here is an example of a pipeline which takes 2 instructions
   at a time and has a five step pipeline with phases:
   * IF = instruction fetch (from cache)
   * ID = instruction decode (determine which CPU unit to activate)
   * EX = execute the instruction (e.g. MOV, ADD, MUL, JMP, CMP, ...)
   * MEM = memory access (pull data from memory)
   * WB = register write back (store data back into a register)
   ![SuperScalar](https://upload.wikimedia.org/wikipedia/commons/4/46/Superscalarpipeline.svg)
5. and they have units to prefetch the arguments to instructions
   * e.g.  -8(%rbp) require doing an addition %rbp + (-8) and then a memory fetch
   * 16(%rax,%rdx,$8) requires calculating rax + rdx*8 + 16 and then a memory fetch
6. and they have cache's (L1, L2, L3, ...) which prefetch blocks of memory onto the CPU chip
   where they can be accessed in relatively few cycles (e.g. 2, 5, 10 instead of 100's).
   Modern computers typically have many CPUs on a chip that all share the same memory
   and that each have their own caches. The higher caches are larger but slower.
   Below is an example of the memory hierarchy of am [AMD Bulldozer server](https://en.wikipedia.org/wiki/CPU_cache)
   ![Caches and Cores](https://upload.wikimedia.org/wikipedia/commons/9/95/Hwloc.png)


In the next three weeks we will show how to analyze programs to generate more efficient assembly
language. 

## Compiling to an Intermediate Representation

[Three Address Code](ThreeAddressCode.md) - the first step is to define an Intermediate Representation. We will use three address code, which assume infinitely many registers
and all operations for the form R1 <- op (R2, R3, ..., Rn)

## Converting the IR to a Directed Graph
[Basic Blocks](BasicBlocks.md) - once we have a program converted to 3 address code, we
need to decompose it into a directed graph of basic blocks, where each basic block has
a single entry point, and a conditional jump at the end (or a return for a function)

## Analyzing variable lifetimes in a Basic Block
[Liveness Analysis](LivenessAnalysis.md) - a key idea in register allocation is to know which
variables need to be stored in the memory at any given point in the program. Variables which won't be used again don't need to be in registers and hence their space can be occupied by another variable.  This is called liveness analysis and we first show how to do this for a basic block.


