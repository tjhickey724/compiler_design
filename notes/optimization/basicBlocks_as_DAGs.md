# DAG representation of Basic Blocks
One very useful way to optimize basic blocks is to represent them as Directed Acyclic Graphs
in a way that allows one to easily find common subexpressions and to eliminate dead code.

We'll give some examples on this page...

Consider the following basic block
```
a = b + c
d = b - c
e = a * d
b = d - a
d = a + b

