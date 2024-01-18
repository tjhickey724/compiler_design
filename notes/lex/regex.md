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
* $R = R_1|R_2$ where $R_1,R_2$ are regular expressions
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
