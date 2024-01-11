# Lexical Analysis

Lexical Analysis is the phase of compilatio when the program is transformed from a sequence of characters to a sequence of tokens. The tokens correspond to the core vocabulary of the formal language. 

On this page we'll define Lexical Analysis, Regular expressions, Regular Languages, DFAs, and NFAs and show how they are all related
and how to convert one into another...

## Tokens
Typical tokens are
* integer constants (123  -77 +1234 0777 0xfff 0b110011)
* decimal constants (0.0 2.718281828 -3.141592  6.022e23)
* keywords (while for if then else elif class function def and or not void public private ...)
* punctuation and operators (+ - * / // % ' " ! # )
* variable names (x interval Interval int2string main



## Regular Expressions
Tokens are usually expressed using Regular Expressions. These are expressions formed from
* characters taken from a set $\Sigma$ of letters (e.g. unicode characters or digits or ASCII characters)
* operators
  * '|' is disjunction or choice
  * '.' is concatenation 
  * '*' is zero or more repetitions
* parentheses "()"

We can define regular expressions over an alphabet $\Sigma$ as follows:
R is a regular expression if
* $R = a$  for some $a \in \Sigma \cup \\{\epsilon\\}$ where $\epsilon$ denotes the empty string.
* $R = R_1.R_2$ where $R_1,R_2$ are regular expressions
* $R = R_1*$ where $R_1$ is a regular expression

It is useful to allow the empty string $\epsilon$ in Regular Expressions because you can use it to
represent optional elements as  $(\epsilon | R)$

### Exercise
*  Write the RE for any numbers of as or bs followed by a c
*  Let ```R1 = 0*.(1.0*.1.0*)*```   What is the language that this RE generates?


## Regular Languages
Each regular expressions defines a set of strings, called a regular language.

For example (a.(b|c))*  represent the set $S$ of strings consisting of zero or more repetitions of ab or ac. We let $\epsilon$ denote the empty string.

$S = \\{\epsilon, ab, ac, abab, abac, acab, acac, ababab, ababac, abacab, ... \\}$

We can define the language $L(R)$ accepted by a regular expression $R$ recursively as follows:

If R is a regular expression oer an alphabet $\Sigma$, define L(R) to the language it generates, then
1. $L(\alpha) = \\{\alpha\\}$  for any character $\alpha\in\Sigma$
2. $L(R_1 . R_2) = \\{\alpha . \beta | \alpha \in L(R_1) \wedge \beta\in L(R_2)\\}$,
  where $\alpha . \beta$ is the concatenation of strings $\alpha$ and $\beta$
3. $L(R_1 | R_2) = L(R_1) \cup L(R_2)$
4. $L(R*) = \bigcup_\limits{n=0}^\infty L(R)^n$

### Exercise
List all of the tokens in the following program 
```
a := 5+3; b := (print(a,a-1), 10*a); print(b)
```
and give names and regular expressions for each token type, e.g.
* ```NUM`` = integer constant = 1.(0|1)*

* The RE for a single digit is D=0|1|2|3|4|5|6|7|7|9 and non-zero digits could be N=1|2|3|4|5|6|7|8|9
* Likewise we can define a RE for letters L =a|b|c|...|A|B|..|Z
* What is a RE for Java (or Python) identifiers using D,N,L
* What is a RE for integer constants like 2024 or 123


### Exercise. 
Write a regular expression for the language of binary numbers where the number of ones is a multiple of 3!

## Finite Automata aka Finite State Machines
Another way to define regular languages is by using a directed graph whose edges are labelled with characters.  The graph $G$ consists of a set $N$ of nodes include a start node $N_0$. A subset $F\subseteq N$ of the nodes are marked as final nodes. Each of the edges in the graph is labelled with a character from some alphabet $\Sigma$ or with the empty string $\epsilon$

Each path through the graph starting at the start state $N_0$ and ending in a final state defines a sequence of characters (the characters on the edges of the path), and the set of all such strings is called the language defined by that automata.  

The Finite Automata is called a Deterministic Finite Automata (DFA) if for each node, all of the edges leaving that node have distinct labels; otherwise it is a Nondeterministic Finite Automata (NFA). The NFAs are most convenient for describing regular languages, but testing if a string is accepted by an NFA is challenging. 

We can represent an NFA as a set of triples:
* $(s_i,c_i,t_i) \in S\times\Sigma\times S$ which represents the edge from node $s_i$ to node $t_i$ labelled with symbol $c_i$

Equivalently we could view it as a partial function $\delta$ from $S\times\Sigma$ to $S$ which explains which state to change to for a given symbol.

We can see if a deterministic finite automata accepts a string by using the DFA to change state for each symbol and checking to see if the last state is a final state.

### Exercise: 
* Draw a DFA for the RE '0*.(1.0*.1.0*)*' and trace through the state changes for 0001101110001000

## Converting a Regular Expression to an NFA
We can define this algorithm to convert a Regular Expression R to an NFA as follows.
* construct the parse tree for R
  * this is pretty easy for humans to do, for small examples, but we won't explain how to do this programmatically until we discuss parsing!
* for each node in the parse tree constuct an NFA with a single start state and a single final state as follows:
  * for a leaf labelled with a symbol $s$ we have s simple NFA  start -> final where the edge is labelled with s
  * for a dot node R1 . R2
    * add an epsilon edge between the final node of R1 and the start node of R2
    * OR just identify the final state of R1 with the start state of R2
  * for a bar node R1 | R2
    * create a new start and final node and have epsilon edges from the new start node to the starts of R1 and R2
    and expilon edges from the final nodes of R1 and R2 to the new final node.
    * OR just identify the start states of R1 and R2 and the final state of R1 and R2
  * for a star node R1*
    * create new start and final node with epsilon edge from the new start to the start and to the final,
   and and epsilon edge from the final of R1 to the start of R1

The only place we need to introduce epsilon edges is for the star nodes.

### Exercise
Convert the following regular expression $R_1$ to an NFA $N_1$ using the algorithm above:
* R1 = ```(a.(b|c))*```
*  Remove the epsolon edges from $N_1$ to get a new NFA $N_2$
*  Do the same with R2 = ```(a.b|c)*``` (which is the same as ```((a.b)|c)*```
*  Now with ```(a.b*|c)*



## Recognizing strings using an DFA
This is a standard state machine where the node represents the current state and the edge determines
which state to move into next.

For a DFA D it is relatively easy to see if a string $\omega$ is accepted by D with the following algorithm.
First let $S$ be the set of states of the DFA and $\Sigma$ the alphabet for the DFA and assume one of the states is an "Error" state $E$.

Let $\delta:S\times \Sigma \rightarrow S$ be the function defined by
* $\delta(s_1,\sigma) = s_2$ if there is an edge from $s_1$ to $s_2$ labelled with $\sigma$
* $\delta(s_1,\sigma) = E$, if there is no edge from $s_1$ labelled with $\sigma$

The following algorithm will determine if a string $\omega$ of length $n$ is accepted by the DFA.
* let $s_0$ be the start state
* define $s_{i+1} = \delta(s_i,\omega_i)$ for each $i$ from 0 to $n-1$ if $s_i\ne E$, otherwise $s_{i+1}=E$
If $s_n$ is a final state, then the string is accepted, otherwise it is not accepted.

Note that we can have the final states labelled with token types, so a DFA could not only accept a string, it could classify it!


## Recognizing strings using an NFA
In this case, we keep track of the set of all possible states the NFA could be in. This is a standard
construction that works with any non-deterministic algorithm. To do this we keep track of all of the possible states we might be in.

## Exercise: Try this out!

## Converting an NFA to a DFA
The states of the DFA are the sets of states that the NFA could be in at that point in time.

We can define the next state function on the power set by 
* $\delta(U,\sigma) = \\{\delta(u,\sigma) : \forall u \in U\\}$
* the start state if $\\s_o\\}$ and the final states are those subsets that contain a final state.
We only care about those states which are reachable from the start state so in general we won't have $2^n$ states in our DFA,
but in the worst case, that is a possibility.

### Exercise: Try this out!

## Converting NFA to DFA when there are episolon edges

Step 1 is to define the epsiolon expansion of a node n to be the set of all nodes you can reach from n by following epsilon edges.
We then replace each node by its epsilon expansion add combine all of the non-epsilon edges.

This new NFA has no epsilon edges so we proceed as before.

### Exercise.  Try this out!


## Finding the maximal match for a DFA
We usually want to find the longest prefix of a string that the DFA accepts.
To do this we just keep track of the last final state encountered. If we reach an error state,
then we revert back to the last final state.


How would we find the longest possible match for a DFA?
* run the algorithm above, but keep track of each $i$ for which $s_i$ is a final state,
* when an Error state is reached or the end of the string, return the last final state, and restart the DFA from position $i$.

### Exercise
* Create an NFA for recognizing the tokens in the mini-java program above where the final states are labelled with the type of the token.
* Convert the NFA to a DFA
* Show how the DFA could be used to convert the program into a list of tokens.

## Implementing a Lexer
The idea here is to define tokens by NFAs and to combine them into a single DFA
where the final states are labelled by the type of token that has been recognized.
We then can write a program which finds the maximal match for a given string, then removes
that string, and repeats the process. For each maximal match it adds the token type and the string to a list.
The algorithm thus converts a string to a list of tokens. The last "token" could be a string that doesn't
match with anything.

### Exercise
Implement the lexer in Python, as described above.

