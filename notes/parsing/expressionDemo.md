# Parsing Expressions

Let's work through an example of LL parsing for expressions.

Consider the following grammar: G0 which allows for arithmetic expressions of 
integers, variables, object attributes and array elements
```
1  S  -> E $
2  E  -> E + T
3  E  -> T
4  T  -> T * F
5  T  -> F
6  F  -> F.v       attribute access for objects
7  F  -> F[E]      array indexing
8  F  -> G
9  G  -> v         variable tokens
10 G  -> (E)

```
Some examples of strings that could be parsed in this language are:
```
node[(r+s+1)*3*t].value.dollars$
car.info[constants.PRICE] + base + discount$
pixel[2*row+1][col+5].red$
```
You could easily extend this to add other operators (-,/,%,...)
Let's draw the parse tree for one of these...

## Removing Left Recursion and Left Factors
The first step is to remove the left recursion. 
We'll do this to get a new grammar G1 which is not left recursive
```
1  S  -> E $
2  E  -> T E1
3  E1 -> + T E1
4  E1 -> epsilon
5  T  ->  F T1
6  T1 -> * F T1
7  T1 -> epsilon
8  F  -> G F1
9  F1 -> . v F1
10 F1 -> [E] F1
11 F1 -> epsilon
12  G  -> v         variable tokens
13 G  -> (E)
```
We see that E1, T1, and F1 are nullable (directly we have E1-> epsilon, etc.)
but no other noneterminals are nullable as there are no rules where the right hand side has all nullable non-terminals

So we need to compute the first and follow sets next by iterating through each rule N -> A B ...Z and applying the following algorithm
* add first(A) to first(N)
* if A is not nullable the go to the next rule
* if A is nullable, add first(B) to first(N), then look at the next non-terminal B, and continue

Applying that to our grammar we get:
```
first(S) += first(E)
first(E) += first(T)
first(E1) += {'+'}
first(T) += first(F)
first(T1) += {'*'}
first(F) += first(G)
first(F1) += {'.', '['}
first(G) += {'v', '('}
```
We keep iterating this process until there is no change in the first sets..
It is more efficient to start at the last production and move up!
```
   first pass  second pass
S              v (
E              v (
E1  +
T              v (  
T1  *
F              v (
F1  . [
G   v (
```
Next we compute the follow sets by iterating through each rule N -> A ... X Y U ... Z and applying the following algorithm
* add first(Y) to follow(X)  as anything that begins a Y can follow an X
* if Y is not nullable, go to the next rule
* if Y is nullable, add first(U) to follow(X) and continue through all the nullable non-terminals following X
* if X is the last element in the rule or if Y U ... Z are all nullable, add follow(N) to follow(X)

So for us the rules are
* follow(E) += { '$', ']', ')' }     from rules 1,10,13
* follow(E1) += follow(E)            from E -> T E1
* follow(T) += first(E1) + follow(E) from E1 -> + T E1 and  E -> T E1 with E1 nullable
* follow(T1) += follow(T)            from T -> F T1
* follow(F) += first(T1) + follow(T) from T1 -> * F T1  and T -> F T1 with T1 nullable
* follow(F1) += follow(F)            from F -> G F1
* follow(G) += first(F1) + follow(F) from F -> G F1 with F1 nullable

So let's try that with our grammar
```
   firsts     follow
S   v (       ACCEPT
E   v (       $ ] )
E1  +         $ ] )
T   v (       +  $ ] )
T1  *         +  $ ] )
F   v (       * + $ ] )
F1  . [       * + $ ] )
G   v (       . [ * + $ ] )
```
So we can now create the parsing table whose rows are the non-terminals and columns are the terminals
by iterating through the rules X -> Y...Z and put the rule into the X row and t column 
* if t is in first(Y...Z) or
* if (Y...Z) is nullable and t is in follow(X)

We only need to worry about the non-terminals that have more than one rules E1,T1,F1,G
as there is no choice for the others S,E,T,F

```
E1 -> + T E1      if t = +          i.e. t in first (+ T E1)
E1 -> epsilon     if t in $ ] )     i.e. t in follow E1
T1 -> * F T1      if t = *          i.e. t in first (* F T1)
T1 -> epsilon     if t in + $ ] )   i.e. t in follow T1
F1 -> . v F1      if t = '.'        etc.
F1 -> [E] F1      if t = '['
F1 -> epsilon     if t in * + $ ] )
G  -> v           if t = v
G  -> (E)         if t = (
```
and this gives us our LL(1) parsing table for G1 and there are no conflicts so the grammar is LL(1)
and this table can be used to parse the language of expressions.

## Next up
The next thing we will do is show how to write javacc programs to parse LL(1) grammars, including this one.


