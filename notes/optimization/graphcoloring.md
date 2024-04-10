# Register Allocation by Graph Coloring
Optimally allocating registers is a very challenging problem when looking at the entire flowgraph not just a basic block,
but there is a relatively simple heuristic that can give us an assignment of variables to registers over an entire flow graph
which is guaranteed not to have any conflicts. The easiest one is to just assign a different register for every variable, 
but we want something which will use fewer registers if possible.

The idea is to create a boolean matrix D where the rows and columns are variables D(a,b) is true if b is "live" at any point where
a is given a new value.

If D(a,b) and D(b,a) are both false, then a and b can share the same register because each time a is given a value, b is dead
and vice versa.



## A simple madeup example
Consider the following program
``` python
def test(a,b):
    x = a+5
    y = 2*x
    z = x+1
    u = b+z
    v = u+3
    w = u*5
    x = w*2
    r = w+y
    return (x,r)
```
In this program a and b are live at the beginning and x and r are live at the end.
How can we compile this with just three registers....

The approach we have to compile code using k registers is 
1. Build the interference graph from the flowgraph
2. Simplify the graph (by removing nodes onto a stack with fewer than k edges (as we can always pick a register that differs)
3. Spill (pick a node with k or more edges assuming it will be stored in memory) and go back to 2
4. Pop the nodes and try to color the graph. If you reach a point where it can't be colored, then modify the code to include LOADS and Stores and start over.

Here a worked example with one spill and one start over.

[Register Allocation by Graph Coloring](regallocColoring.pdf)
