# Register Allocation by Graph Coloring
Optimally allocating registers is a very challenging problem when looking at the entire flowgraph not just a basic block,
but there is a relatively simple heuristic that can give us an assignment of variables to registers over an entire flow graph
which is guaranteed not to have any conflicts. The easiest one is to just assign a different register for every variable, 
but we want something which will use fewer registers if possible.

The idea is to create a graph whose vertices are the variables in the flowgraph. We add an edge between two variables if they
are both live at any point in the program.  If there is no edge between two vertices, A and B, then that means that variables A
and B are never live at the same time and hence they can share a register.   This is called t**he interference graph**.

We can do register allocation with k registers by trying to color the interference graph with k colors in such a way that no
to vertices connected by an edge have the same color.  If we can't color the graph with k colors, then we need to pick a variable
and store it in the stack, this is called **spilling the variable**. We rewrite the 3 address code with this spilling, and start over.

We'll describe this algorithm with a made up example that is easy to analyze.

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
In this program x and r are live at the end (as they are return values), and we can infer that a and b are live at the top of the function body.

Let's compile this with just three registers....
Notice that the body is already in 3 address code mode and there are no jumps so it is relatively easy to determine which variables are live
at each point.

The approach we have to compile code using k registers is 
1. Build the interference graph from the flowgraph
2. Simplify the graph (by removing nodes onto a stack with fewer than k edges (as we can always pick a register that differs)
3. Spill (pick a node with k or more edges assuming it will be stored in memory) and go back to 2
4. Pop the nodes and try to color the graph. If you reach a point where it can't be colored, then modify the code to include LOADS and Stores and start over.

Here a worked example with one spill and one start over.

[Register Allocation by Graph Coloring](regallocColoring.pdf)
