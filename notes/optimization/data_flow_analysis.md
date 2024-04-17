# General Data Flow Analysis

In our general model we have a labelled directed graph representing the flow graph of a program
and we want to be able to infer certain features of the program at different points in the flowgraph.

## Basic Assumptions
To do Data Flow Analysis, we need the following components:
* a dataflow graph G with an Entry point and and Exit point
* a direction of data flow
  * forwards, e.g. for constant propagation
  * backwards, e.g. for live variable analysis
* a set of values $V$
  * maps from the variables to the lattice of possible values for constant propagation
  * subsets of the set of variables (for live variable analysis)
* a meet operator $\wedge: V \rightarrow V$ for joining values 
* a set of functions $F_B:V \rightarrow B$ for each basic block B in $G$
* a constant value $v_{\rm entry}$ or $v_{\rm exit}$ in $V$ used to start the data flow

We also usually assume that $V$ is a lattice, that is, it is a partially ordered set with
* a smallest element "bottom" = $\bot$
* a largest element "top" = $\top$
* a greatest lower bound function $v \wedge w$
* a least upper bound function $v \vee w$


## Algorithm for forward data flow analysis
We will compute entry and exit values IN[B] and OUT[B] for each basic block B
```
OUT[ENTRY] = v_entry
for each basic block B except entry:
    out[B] = TOP
while any OUT value changes:
    for each basic block B except for ENTRY:
        IN[B] = MEET([OUT[B1] for B1 in Predecessor(B)])
        OUT[B] = f_B(IN[B])
```

The algorithm for backward data flow analysis is similar:
```
IN[EXIT] = v_exit
for each basic block B except EXIT:
    IN[B] = TOP
while any IN value changes:
    for each basic block B except for ENTRY:
        OUTB] = MEET([OUT[B1] for B1 in Successor(B)])
        IN[B] = f_B(OUT[B])
```

The idea is that we keep track of the values at the beginning and ending of each block
and we use the transfer functions $f_B$ to update the OUT value of a block from its IN value
(or vice versa for backward flow analysis). Then we update the IN value by looking at all of the
OUT values of blocks that lead into that block, and using our MEET operator to join all of those values.


***Under Construction***
