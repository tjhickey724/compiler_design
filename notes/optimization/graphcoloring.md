# Register Allocation by Graph Coloring
Optimally allocating registers is a very challenging problem when looking at the entire flowgraph not just a basic block,
but there is a relatively simple heuristic that can give us an assignment of variables to registers over an entire flow graph
which is guaranteed not to have any conflicts. The easiest one is to just assign a different register for every variable, 
but we want something which will use fewer registers if possible.

The idea is to create a boolean matrix D where the rows and columns are variables D(a,b) is true if b is "live" at any point where
a is given a new value.

If D(a,b) and D(b,a) are both false, then a and b can share the same register because each time a is given a value, b is dead
and vice versa.
