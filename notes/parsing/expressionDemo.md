# Parsing Expressions

Let's work through an example of LL parsing for expressions.

Consider the following grammar: G0
```
1  S  -> E $
2  E  -> E + T
3  E  -> T
4  T  -> T * F
5  T  -> F
6  F  -> v
7  F  -> (E)
8  F  -> v[E]
9  F  -> F.v
```
where v is the token symbol for identifiers,
and rule 8 corresponds to array lookup and rule 9 to field access in an object.

## Removing Left Recursion and Left Factors
The first step is to remove the left recursion. 
We'll do this to get a new grammar G1

The next step is to notice that rules 6 and 8 have a common left factor (the terminal v)
So we either need to use left factoring, or use a local lookahead of 2 provided follow(F) does not contain "["

## Calculate nullable, first, and follow for the non-terminals in G1
We apply the algorithms in the book to calculate the set of nullable non-terminals
and the terminal sets first and follow for each non-terminal

## Generate the parsing table
Next we create a parsing table which states, for each nonterminal to be expanded in a leftmost derivation
and for each terminal that is the next unparsed element of the string, which rules we could use to expand that non-terminal
If the parsing table contains no duplicate entries in any cell, then the language in LL(1).

## Next up
The next thing we will do is show how to write javacc programs to parse LL(1) grammars, including this one.


