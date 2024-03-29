# Basic Blocks
In order to generate efficient code, it is helpful to break the program into maximally large segments which must always be 
evaluated sequentially, such a segment of three address code is called a basic block. 

A **basic block** is a subsequence of the three address code for a program in which there are no jumps (conditional or absolute) except possibly
the last instruction, and there are no labels except possibly for the first instruction. So any time the processor starts executing
the first instruction it will run all of the instruction in order until the last instruction.

## Example 
Consider the following example from the suggested textbook Aho,Sethi,Ullman, ... Example 8.6.
The following code initializes a 10x10 array to have zeroes everywhere except having ones on the diagonal,
i.e. initializes it to be the identity matrix:
``` java=
for (int i=0; i<10; i++)
  for (int j=0; j<10; j++)
    a[i][j] = 0;
for (int i=0; i<10; i++)
  a[i][i]=1;
```
We can convert this into the following three address code
```
  i = 1
L1:
  j = 1
L2:
  t1 = 10 * i
  t2 = t1 + j
  t3 = 8 * t2
  t4 = t3 - 88
  a[t4] = 0
  j = j + 1
  if j <= 10 goto L2:
  i = i + 1
  if i <= 10 goto L1:
  i = 1
L3:
  t5 = i = 1
  t6 = 88 * t5
  a[t6] = 1
  i = i + 1
  if i <= 10 goto L3:  
```
