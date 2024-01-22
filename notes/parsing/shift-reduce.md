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



