# The Early Parser for general CFGs
The best way to understand how LR parsing works is to first look at the Early Parser.
This is a O(n^3) algorithm for parsing strings in any Context Free Grammar. 

The main idea is to design a non-deterministic program to generate a leftmost derivation of a string
and then to use the same idea we had for converting and NFA to a DFA to create a deterministic program.
The difference is that the non-deterministic program will go through a set of states and need to refer to previous
states, so it will use a "memory" which DFAs do not.

Lets look at the following grammar for the inherently ambiguous language { a^n b^n c^m union a^n b^m c^m}
```
1  P -> S $
2  S -> AB C
3  S -> A BC
4  AB -> a AB b
5  AB -> a b
6  A -> a A
7  A -> a
8  BC -> b BC c
9  BC -> b c
10 C -> c C
11 C -> c
```
and let try to get a derivation for abc. There are two derivations
```
1  P          -> rule 1  P -> S $         P -> . S $
2  S $        -> rule 2  S -> AB C        P -> . S $  S -> . AB C
3  AB C $     -> rule 4  AB -> a b        P -> . S $  S -> . AB C   AB -> .a b
4 |a b C $    -> scan a and b,            P -> . S $  S -> . AB C   AB -> a b.  
5  a b|C $    -> recognize an AB          P -> . S $  S -> AB . C   
5  a b|C $    -> rule 10 C -> c           P -> . S $  S -> AB . C   C -> . c 
6  a b|c  $   -> scan c                   P -> . S $  S -> AB . C   C -> c .
6  a b c | $  -> recognize a C            P -> . S $  S -> AB C . 
7  a b c | $  -> recognize an S           P -> S . $
8  a b c $ |  -> scan $                   P -> S $ .
9  a b c $ |  -> recognize P, done!       
```
or
```
1  P          -> rule 1  P -> S $
2  S $        -> rule 3  S -> A BC
3  A BC $     -> rule 6  A -> a N
4  a A BC $   -> rule 7  A -> epsilon
5  a BC $     -> rule 8  BC -> b BC c
6  a b BC c $ -> rule 9  BC -> epsilon  
7  a b c $
```
So we had two choices for the second rule which makes this non-deterministic.

The Early model is to try to run all of the possible parses at once.
So for each character in the input, starting from position 0, we keep track of all of the rules we could have used so far in the derivation
and we keep track of "where" in the rule we are. For example at step 4 in both derivations, we have derived a string starting with "a"
but in the first derivation the next non-terminal is AB, where as in the second derivation the next nonterminal is A.
```
character state  LR states
            0    P -> . S $   S -> .AB C  S -> .A BC  AB -> .a AB b  AB -> .ab  A -> .a A  A -> .a 
a
            1    AB -> a . AB b/0    AB -> a . b/0    A -> a . A/0   A -> a ./0      (find all LR items starting with an 'a', note we may have recognized an A, from state 0
                 AB -> . a AB b/1    AB -> . a b/1     (we can now look for another AB  using the rules AB -> a . AB b /0)
                 S -> A . BC  (this is because we had A -> a/0 and in state 0 we were trying to parse an A with S -> .A BC, which we have parsed!
                 BC -> . b BC c/1    BC -> .b c /1
b
            2    AB -> a b./0     BC -> b . BC c/1    BC -> b . c /1    
                 BC -> . b BC c/2   BC -> .b c/2
                 S -> AB . C /0  (since we have recognized an AB with AB -> a b./0 and that comes from state 0 where we were looking for an AB in S -> .AB C)
                 C -> . c C/2    C -> .c /2
c
            3    BC -> b c./1    C-> c. C/2   C -> c./2
                 S -> AB C ./0   (as we found C->c./2, and in state 2 we had S -> AB . C/0, so we found that C and can advance it)
                 S -> A BC ./0   (as we have found BC -> b c./1, and in state 1 we were looking for S -> A . BC/0, which we found)
                 P -> S.$        (as we have found an S that we were looking for in state 0, as P -> .S $, so we advance the dot)
$
            4    P -> S $ .  and we have recognized the start symbol P so the parse is over!
```

## Early's algorithm
We start with the start symbol ```P -> . S$``` with the end of file character attached, this is state0
and we want to parse  string v = v0 v1 v2 .... vm
Then we apply the following rules to process state[n]
* Prediction: for every rule $(A \rightarrow \alpha . B \beta,k)$ in state[n], add the rules $(B\rightarrow . \gamma,n)$ to state[n] for each $B\rightarrow \gamma$ in the grammar
* Reduction: for every rule $(A \rightarrow \alpha .,k)$ in state[n], go to state[k] and find all rules of the form $(C\rightarrow \beta . A \gamma,j)$ and add
  the rules $(C\rightarrow \beta A . \gamma,j)$ to state[n]

Keep iterating these two operations until there is no more change of state[n]

* Shift: look at the next character vn and create a state[n+1] by finding all rules from state[n] of the form $(A\rightarrow \alpha . vn \beta,k)$ and
  adding $(A\rightarrow \alpha  vn . \beta,k)$ to state[n+1], then go back to the prediction and reduction steps to complete state[n+1]

When the end of the string is reached, if we have the production $P\rightarrow S \$.$ in the last state, then the string is in the language, otherwise it is not!
           



