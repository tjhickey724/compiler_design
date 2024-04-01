# Architecture notes
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

