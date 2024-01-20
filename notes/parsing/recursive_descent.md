# Recursive Descent Parsing
There are two basic approaches to parsing programs using context free grammars - top down and bottom up.

The top down approach, which we discuss here, proceeds by scanning the string to be parsed from left to right
and constructing the parse tree using a top-down left-to-right depth first search traveral of the tree. This
corresponds to a leftmost derivation of the string and at each derivation step we need to pick which of the
rules for the left most nonterminal we should use.

The bottom up approach corresponds to rightmost derivation of the string and builds the tree from the bottom up.
This is similar to performing a postfix traversal of the parse tree.  

The algorithm for recursive descent parsing is straightforward. 

We generate a leftmost derivation $S_0,\ldots,S_n$ of the string $\omega$ as follows:
* let $S_0$ be the sentential form consisting of the start symbol $S$
* in the $i$ th step, let $k$ be the position of the first non-terminal $N$ in the sentential form $S_i$
  * the first $k-1$ characters in $S_i$ must match the first $k-1$ characters of $\omega$. Let $c=\omega[k]$ be the next character.
  * **use $c$ to determine which of the grammar rules for $N$ should be used next** (We'll talk about how to do this next!)
  * replace $N$ with the left hand side of the chosen grammar rule and extend the parse tree by adding the LHS of the rule as the children of $N$
* continue until $S_n = \omega$

