# DAG representation of Basic Blocks
One very useful way to optimize basic blocks is to represent them as Directed Acyclic Graphs
in a way that allows one to easily find common subexpressions and to eliminate dead code.

We'll give some examples on this page...

Consider the following basic block
```
a = b + c
d = b - c
e = a * d
f = b + c
b = d - a
d = f + b
```
We first renumber the variables with indices representing whether the number of times their value has changed:
```
a = b + c
d = b - c
e = a * d
f = b + c
b1 = d - a
d1 = f + b1
```

Then we create a DAG where the leaves are the original variables
![bbdag](https://github.com/tjhickey724/compiler_design/assets/195879/36187835-ded8-41a6-b7c8-901c39e59cc2)


