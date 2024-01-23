# Operator Grammars

One very important class of languages are the operator expression languages.
Every programming language includes some kind of arithmetic and/or boolean expressions
and these are typically expressed as infix operators with specified associativity and precedence.

Such Expression Grammars can easily be handled by LL(1) or LR(1) parsers, but it is instructive to look
at these languages in isolation, not as part of a programming language, to see how we can best parse them.

The simplest grammar for binary operators is as follows:
```
E -> E Op E
E -> F
F -> num | id | (E)
Op -> + | - | * | / | ...
```
and we typically specify that */ have higher precedence than +- and we use that to parse expressions like
```
3+4*5
```
as 
```
3+(4*5)
```
rather than
```
(3+4)*5
```


