# Shift-Reduce Parsing

#UNDER CONSTRUCTION

In this note we present the main ideas of shift-reduce parsing and LR grammars.

The key idea is to keep track of the current state of a parser after seeing a prefix of the input
using a set of LR(1)-items.  These LR(1)-items have the following form:

$$(A \rightarrow \alpha \cdot \beta , x)$$

where $A \rightarrow \alpha\beta$ is a production in the grammar and $x$ is a terminal symbol
and $\alpha$ and $\beta$ are (possibly empty) strings of terminals and non-terminals. An LR(1) item
indicates a position in a left-to-right leftmost derivation stating that the derivation involves
that rule but only the part up through $\alpha$ has been fully derived, and the part in $\beta$
has not been expanded to capture any terminals.

The States of an LR(1) parser consist of sets of LR(1)-items that correspond to the most recent
rewriting rules that could have been applied in a leftmost derivation.

This is similar to the NFA to DFA transformation... we try to keep track of all of the states
we could be at if we had a non-deterministic parser that had to choose which rule to apply
in trying to generate a derivation for the current string. 

Let's look at an example to see how the shift-reduce parser would work.
Let G0 be the following grammar for parenthesized sums:
```
S -> E$
E -> E + T
E -> T
T -> v
T -> (E)
```
We can build a parse tree for a string in this language from the left to the right, bottom to the top, as follows.
At each point we can reduce the leftmost sentential form that is the right hand side of a production. 
```
parse
 1                        v + ( v + v ) $ SHIFT v
 2 v                        + ( v + v ) $ REDUCE T->v and store (T0->v) 
 3 T0                       + ( v + v ) $ REDUCE E -> T  and store (E0->T0)
 4 E0 +                       ( v + v ) $ SHIFT +
 5 E0 + (                       v + v ) $ SHIFT (
 6 E0 + ( v                       + v ) $ SHIFT v
 7 E0 + ( T1                      + v ) $ REDUCE T->v   and store T1->v)
 8 E0 + ( E1                      + v ) $ REDUCE E->T   and store E1->T1)
 9 E0 + ( E1 +                      v ) $ SHIFT v
10 E0 + ( E1 + v                      ) $ REDUCE T->v  and store T2->v
11 E0 + ( E1 + T2                     ) $ REDUCE E->E+T and store E2->E1+T2
12 E0 + ( E2                          ) $ SHIFT )
13 E0 + ( E2 )                          $ REDUCE T -> (E) and store T3->(E2)
14 E0 + T3                              $ REDUCE E->E+T  and store E3->E0+T3
15 E3                                   $ SHIFT $
16 E3 $                                   REDUCE S->E$ and store S0->E3$
17 S0
```
The challenge her is knowing when to shift and when to reduce. 
In step 11 though we had two choices... we could have reduced with E->T  or with E->E+T





