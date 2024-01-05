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
* characters
* operators
  * | = disjunction or choice
  * . = concatenation 
  * * = zero or more repetitions
* parentheses

For example (a.(b|c))*  represent the set of strings consisting of zero or more repetitions of ab or ac
e.g. abababacabacacac

If R is a regular expression, define L(R) to the language it generates, then
* $L(c) = \\{c\\}$  for any character c
* $L(R_1 . R_2) = \\{a.b | a \in L(R_1) \wedge b\in L(R_2)\\}$
* $L(R_1 | R_2) = \\{c | c \in L(R_1) \vee c\in L(R_2)\\}$
* $L(R*) = \bigcup_\limits{n=0}^\infty\\{a | a \in L(R)^n\\}$
