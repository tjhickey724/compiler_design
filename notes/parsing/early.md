# The Early Parser for general CFGs
The best way to understand how LR parsing works is to first look at the Early Parser.
This is a O(n^3) algorithm for parsing strings in any Context Free Grammar. 

The main idea is to design a non-deterministic program to generate a leftmost derivation of a string
and then to use the same idea we had for converting and NFA to a DFA to create a deterministic program.
The difference is that the non-deterministic program will go through a set of states and need to refer to previous
states, so it will use a "memory" which DFAs do not.

Lets look at the following grammar for the inherently ambiguous language { a^n b^n c^m union a^n b^m c^m}
```
1  P -> S $
2  S -> AB C
3  S -> A BC
4  AB -> a AB b
5  AB -> a b
6  A -> a A
7  A -> a
8  BC -> b BC c
9  BC -> b c
10 C -> c C
11 C -> c
```
and let try to get a derivation for abc. There are two derivations
```
1  P          -> rule 1  P -> S $         P -> . S $
2  S $        -> rule 2  S -> AB C        P -> . S $  S -> . AB C
3  AB C $     -> rule 4  AB -> a b        P -> . S $  S -> . AB C   AB -> .a b
4 |a b C $    -> scan a and b,            P -> . S $  S -> AB . C   AB -> a b.
5  a b| C $   -> rule 10 C -> c       P -> . S $  S -> AB . C   C -> . c C
6  a b|c  $  -> scan c
6  a b c | $  -> rule 11 C -> epsilon
7  a b c $
```
or
```
1  P          -> rule 1  P -> S $
2  S $        -> rule 3  S -> A BC
3  A BC $     -> rule 6  A -> a N
4  a A BC $   -> rule 7  A -> epsilon
5  a BC $     -> rule 8  BC -> b BC c
6  a b BC c $ -> rule 9  BC -> epsilon  
7  a b c $
```
So we had two choices for the second rule which makes this non-deterministic.

The Early model is to try to run all of the possible parses at once.
So for each character in the input, starting from position 0, we keep track of all of the rules we could have used so far in the derivation
and we keep track of "where" in the rule we are. For example at step 4 in both derivations, we have derived a string starting with "a"
but in the first derivation the next non-terminal is AB, where as in the second derivation the next nonterminal is A.



