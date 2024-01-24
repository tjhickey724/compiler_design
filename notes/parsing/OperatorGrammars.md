# Operator Grammars

One very important class of languages are the operator expression languages.
Every programming language includes some kind of arithmetic and/or boolean expressions
and these are typically expressed as infix operators with specified associativity and precedence.

Such Expression Grammars can easily be handled by LL(1) or LR(1) parsers, but it is instructive to look
at these languages in isolation, not as part of a programming language, to see how we can best parse them.

The simplest grammar for binary operators is as follows. where \$ marks the end of the input
```
S -> E $
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
multiply        5 12     (as 4*3=12)
add             17       (as 5+12=17)
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

## Parsing Polish Notation
The goal of parsing an arithmetic expression is to generate the parse tree for that expression.
For Prefix notation, we can use an LL(0) parser, where \$ is a marker for the end of the input.
```
S -> E $
E -> Op E E
E -> F
F -> num |id
```

## Parsing Reverse Polish Notation
The parse an expression in reverse polish notation, we need to use an LR(0) parser.
We keep pushing thing on the stack and if we see ```( ... Val Val Op)``` at the top of the stack
we apply the operator to the values to get ```Op(Val,Val)``` which we push onto the stack!
For example
```
| 5 4 3 * + $ ->
5 | 4 3 * + $ ->
5 4 | 3 * + $ ->
5 4 3 | * + $ ->
5 4 3 * | + $ ->              (as 4 3 * on top of the stack matches E = F F Op
5 times(4,3) | + $ ->
5 times(4,3) Op(+) | $  ->
add(5,times(4,3)) $ -> 
```

```
| 5 4 3 * + ->
F(5) | 4 3 * + ->
F(5) F(4) | 3 * + ->
F(5) F(4) F(3) | * + ->
F(5) F(4) F(3) Op(*) | + ->              (as 4 3 * on top of the stack matches E = F F Op
F(5) E(times(4,3)) | + ->
F(5) E(times(4,3)) | + ->
add(5,times(4,3))
```

## Parsing Infix Notation
To parse infix notation we can use an LR approach, but we resolve conflicts using the precedence and assocativity properties of the operators.







