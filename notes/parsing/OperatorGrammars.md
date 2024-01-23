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

## Reverse Polish Notation
Early electronic calculators used a simpler (to implement) syntax for arithmetic expressions using a stack.
The user would push values onto a stack and then apply operation, that would pop the top 2 elements off the stack,
apply the operation, and push it down again.  For example
```
Command         Stack
push 5          5
push 4          5 4
push 3          5 4 3
multiply        5 12
add             17
```
So the expression ``` 5 4 3 * +``` would evaluate to 17.

Another variation of this approach is to put the operator before the operand, this is called Polish Notation, 
and is so named because a Polish Logician first popularized this approach. For the previous example, the polish notation would be
```
+ 5 * 4 3
```
The infix version of this expression is
```
5 + 4 * 3
```





