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

For example (a.(b|c))*  represent the set $S$ of strings consisting of zero or more repetitions of ab or ac

$S = \\{\epsilon, ab, ac, abab, abac, acab, acac, ababab, ababac, abacab, ... \\}$

If R is a regular expression, define L(R) to the language it generates, then
* $L(c) = \\{\alpha\\}$  for any character $\alpha$
* $L(R_1 . R_2) = \\{\alpha . \beta | \alpha \in L(R_1) \wedge \beta\in L(R_2)\\}$,
  where $\alpha . \beta$ is the concatenation of strings $\alpha$ and $\beta$
* $L(R_1 | R_2) = L(R_1) \cup L(R_2)$
* $L(R*) = \bigcup_\limits{n=0}^\infty L(R)^n$
