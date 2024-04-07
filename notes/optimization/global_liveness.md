# Liveness Analysis

One of the most important kinds of analysis that a compiler needs to perform is determining, at each point in a program
which variables are live and which are dead.  

Let A = B OP C be an instruction, then we say that A is defined by that instruction and B and C are used.

A variable is (statically) live at a point, if there is a path from that point to a use of that variable, 
which doesn't pass through a definition of that variable, in other words, the value of that variable at that point
might be needed by a later instruction.  

If there are no such paths, then we say the variable is (statically) dead, that is, if every path from that point
eventually reaches the end of the program or reaches an instruction that redefines the variable.

