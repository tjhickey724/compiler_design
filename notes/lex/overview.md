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
whose input is a regular expression$R$  and a string $s$ and an integer $n$ which
returns the set of all strings of length $n$ in the language $L(R)$
This is tricky because the unions in steps 3 and 4 are not necessarily disjoint! The slow method is
to generate the set of all strings of length $n$ in the language and then test if $s$ is in that set.

## Deterministic Finite Automata
Another way to represent 
