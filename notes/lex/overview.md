# Lexical Analysis

Lexical Analysis is the phase of compilatio when the program is transformed from a sequence of characters to a sequence of tokens. The tokens correspond to the core vocabulary of the formal language. 

## Tokens
Typical tokens are
* integer constants (123  -77 +1234 0777 0xfff 0b110011)
* decimal constants (0.0 2.718281828 -3.141592  6.022e23)
* keywords (while for if then else elif class function def and or not void public private ...)
* punctuation and operators (+ - * / // % ' " ! # )
* variable names (x interval Interval int2string main

## Regular Expressions
Tokens are usually expressed using Regular Expressions. These are expressions formed from
* characters taken from an set $C$ of letters (e.g. unicode characters or digits or ASCII characters)
* operators
  * '|' is disjunction or choice
  * '.' is concatenation 
  * '*' is zero or more repetitions
* parentheses

## Regular Languages
Each regular expressions defines a set of strings, called a regular language.

For example (a.(b|c))*  represent the set $S$ of strings consisting of zero or more repetitions of ab or ac. We let $\epsilon$ denote the empty string.

$S = \\{\epsilon, ab, ac, abab, abac, acab, acac, ababab, ababac, abacab, ... \\}$

We can define the language $L(R)$ accepted by a regular expression $R$ recursively as follows:

If R is a regular expression, define L(R) to the language it generates, then
1. $L(c) = \\{\alpha\\}$  for any character $\alpha$
2. $L(R_1 . R_2) = \\{\alpha . \beta | \alpha \in L(R_1) \wedge \beta\in L(R_2)\\}$,
  where $\alpha . \beta$ is the concatenation of strings $\alpha$ and $\beta$
3. $L(R_1 | R_2) = L(R_1) \cup L(R_2)$
4. $L(R*) = \bigcup_\limits{n=0}^\infty L(R)^n$

### Exercise. 
Write a python function ```regen(R,s,n)``` 
whose input is a regular expression $R$  and a string $s$ and an integer $n$ which
returns the set of all strings of length $n$ in the language $L(R)$
This is tricky because the unions in steps 3 and 4 are not necessarily disjoint! The slow method is
to generate the set of all strings of length $n$ in the language and then test if $s$ is in that set.

## Finite Automata
Another way to define regular languages is by using a directed graph whose edges are labelled with characters.  The graph $G$ consists of a set $N$ of nodes include a start node $N_0$. A subset $F\subseteq N$ of the nodes are marked as final nodes. Each of the edges in the graph is labelled with a character.
Each path through the graph starting at the start state $N_0$ and ending in a final state defines a sequence of characters (the characters on the edges of the path), and the set of all such strings is called the language defined by that automata.  

The Finite Automata is called a Deterministic Finite Automata (DFA) if for each node, all of the edges leaving that node have distinct labels; otherwise it is a Nondeterministic Finite Automata (NFA).

## Converting a Regular Expression to an NFA
We can define this algorithm recursively...

## Recognizing strings using an DFA
This is a standard state machine where the node represents the current state and the edge determines
which state to move into next.

## Recognizing strings using an NFA
In this case, we keep track of the set of all possible states the NFA could be in. This is a standard
construction that works with any non-deterministic algorithm

## Converting an NFA to a DFA
The states of the DFA are the sets of states that the NFA could be in at that point in time.

## Finding the maximal match for a DFA
We usually want to find the longest prefix of a string that the DFA accepts.
To do this we just keep track of the last final state encountered. If we reach an error state,
then we revert back to the last final state.


