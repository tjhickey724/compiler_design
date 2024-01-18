# Building a Lexical Analyzer

In this note we explain how to use what we've seen before to build a lexical analyzer.

We assume the goal is to convert a string into a sequence of tokens T1 T2 ... Tk
where each token has a token type T and a regular expression R, e.g.
* IF = 'i' . 'f'
* LT = '<'
* ....
* ID = ('a' | 'b' | ... | 'z' | 'A' | .... | 'Z' | '_' | '0' | ... | '9')+
* NUM = ('1' | '2' | ... | '9') . ('0' | '1' | ... | '9')*
* SPACE = ' ' | '\t' | '\n' | '\r' 
* ERROR = | of all characters

We convert the disjunction of all of these RegExes into an NFA where the final states are labelled with
the token they represent.

Then we convert that NFA into a DFA whose states are sets of NFA states. If a DFA state contains one or more
NFA final states, then it becomes a final state with the first tokentype to appear in the specification,
so order matters.

We then optinize that DFA by generating a next state array whose rows are the state and whos columns are the 256 one byte characters
and whose values are the state we transition into upon seeing that character. This DFA can be efficiently run in linear time to find
the longest match, but it may be quadratic time if for example no subtring matches any token!



## Exercise: lexical_analyzer¶
Write a lexical_analyzer which accepts a list of token definitions of the form
``` python
(token_name,  NFA)
```
and generates a DFA for recognizing those tokens which could be used with the "tokenizer" function above.

Ideally we would use a Regular Expression instead of an NFA, but we need to read the next chapter to learn how to
parse a Regular Expression into a tree that can be converted into an NFA. We'll do this next week.

We call this DFA repeatedly on the string, finding (and removing) the longest match (which could be a one byte error).
We return a list of these matches Mi with their token types Ti:
* [(M1 T1),(M2 T2),....,(Mn, Tn)]

If there are no errors then we can proceed to the parsing phase!
