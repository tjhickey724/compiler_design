# Dynamic Programming
When attempting to compile an arithmetic expression to assembly language with a limited number of registers
there are a large number of possible results and it is not immediately clear what an optimal strategy should be.

Dynamic programming provides an approach which is not that computationally expensive (linear in the size of the expression)
and which provides the best possible results for a simple model of a CPU.

The idea is to assume you have up to "r" registers and to calculate for each node of the parse tree for the expression
the minimal cost of evaluating that expression with $i$ registers for $i=0,\ldots,r$.  In the case of 0 registers, we
calculate the minimal cost of evaluating it with all "r" registers and then storing the result into memory.

For this to work we must have a complete list of the assembly language operations and their costs. For today, we consider
the following operations, where Ri are registers and Mi are memory locations
| instruction | cost | meaning |
|---|---|---|
|ST Ri Mj| 10 | store the value of R in memory location Mj|

