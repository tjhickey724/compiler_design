# Register Allocation and Liveness Analysis

One of the most important tasks of an optimizing compiler is to determine how best to use the
registers.  It is relatively simple to design a compiler where all of the variables and temporaries
are stored in the stack, but accessing elements in the stack can take 10 times longer than accessing
an element in a register.  

Consider for example an add operation
```
addq %rdx %rax
```
in the X86-64 assembly language. This will typically take one clock cycle. 

Accessing an element in the stack, e.g. with,
```
addq -24(%rbp), %rax
```
will require accessing the stack part of the main memory, but
the stack is typically stored in the L1 Cache, and so this may add 2-5 cycles 
to the execution time.

Accessing an element in the heap, e.g. an array access
```
addq (%rdx,%r7,8), %rax
```
could take 2-50 cycles if that part of the heap is in the L1, L2, or L3 cache
where these caches hold increasingly more memory, but are proportionately slower.
If that memory location is not in the L3 cache then the CPU needs to go to the main
memory which could take hundreds of cycles.

All of this is to point out that make effective use of registers can greatly
increase the speed of the compiled code. This process is called **register allocation**.

One of the most common approaches to register allocation is to determine for each
instruction and each variable in that instruction, if that variable will be used
again in some future instruction.  If so the variable is **live** for every instruction
from the current instruction until its next use. 

If a variable is in a register and is not live then that register can use used for
another variable!

## Liveness analysis for basic blocks
Lets analyze the following basic 5 instruction block,
where we put the number of the next use of each variable in the table, 
and a - if it doesn't have a next use in the block

| n | instruction | i | j | t1 | t2 | a |
| --- | --- | --- | --- | --- | --- | --- |
|1|  t1 = 10 * i  | 1| - | - | - | ? |
|2|  t2 = t1 + j  | ? | 2 | 2 | 3 | ? |
|3|  a[t2] = 0   | ? | 4 | - | 3 | ? |
|4|  j = j + 1  | ? | 4 | - | - | ? |
|5|  if j <= 10 goto L2:  | ? | 5 | - | - | ? |

Here is the algorithm for calculating the liveness and next use data for a basic block.

1. Start at the last instruction and initialize a symbol table with all non-temporary values being live (indicated with a ? as we don't know where or if they will be used again in the program).
2. For each instruction ```i: A = B op C``` do the following
   * set A to be not-live (indicated with a "-")
   * set B and C to live with next use being ```i```
3. Repeat step 2 for each instruction in the block moving up.







