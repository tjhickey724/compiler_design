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

Architecture notes
1. Modern CPUs have multiple arithmetic and logic units and can handle multiple additions
   at the same time, and multiple multiplications (though usually fewer than the additions),
2. and they have units to prefetch the next instruction from memory
3. and they have units to prefetch the arguments to instructions
   * e.g.  -8(%rbp) require doing an addition %rbp + (-8) and then a memory fetch
   * 16(%rax,%rdx,$8) requires calculating rax + rdx*8 + 16 and then a memory fetch
4. and they have cache's (L1, L2, L3, ...) which prefetch blocks of memory onto the CPU chip
   where they can be accessed in relatively few cycles (e.g. 2, 5, 10 instead of 100's)


In the next three weeks we will show how to analyze programs to generate more efficient assembly
language. 

[Three Address Code](ThreeAddressCode.md) - the first step is to define an Intermediate Representation. We will use three address code, which assume infinitely many registers
and all operations for the form R1 <- op (R2, R3, ..., Rn)


[Basic Blocks](BasicBlocks.md) - once we have a program converted to 3 address code, we
need to decompose it into a directed graph of basic blocks, where each basic block has
a single entry point, and a conditional jump at the end (or a return for a function)

[Liveness Analysis](LivenessAnalysis.md) - a key idea in register allocation is to know which
variables need to be stored in the memory at any given point in the program. Variables which won't be used again don't need to be in registers and hence their space can be occupied by another variable.  This is called liveness analysis and we first show how to do this for a basic block.


