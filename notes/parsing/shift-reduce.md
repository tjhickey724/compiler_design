# Shift-Reduce Parsing

In this note we present the main ideas of shift-reduce parsing and LR grammars.

The key idea is to keep track of the current state of a parser after seeing a prefix of the input
using a set of LR(1)-items.  These LR(1)-items have the following form:
* $(A \rightarrow \alpha \cdot \beta , x)$

where $A \rightarrow \alpha\beta$ is a production in the grammar and $x$ is a terminal symbol.

The States of an LR(1) parser consist of sets of LR(1)-items that correspond to the most recent
rewriting rules that could have been applied in a leftmost derivation.

This is similar to the NFA to DFA transformation... we try to keep track of all of the states
we could be at if we had a non-deterministic parser that had to choose which rule to apply
in trying to generate a derivation for the current string. 



