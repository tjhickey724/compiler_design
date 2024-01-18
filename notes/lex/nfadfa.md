# Finite Automata aka Finite State Machines
Another way to define regular languages is by using a directed graph whose edges are labelled with characters.  The graph $G$ consists of a set $N$ of nodes include a start node $N_0$. A subset $F\subseteq N$ of the nodes are marked as final nodes. Each of the edges in the graph is labelled with a character from some alphabet $\Sigma$ or with the empty string $\epsilon$

Each path through the graph starting at the start state $N_0$ and ending in a final state defines a sequence of characters (the characters on the edges of the path), and the set of all such strings is called the language defined by that automata.  

The Finite Automata is called a Deterministic Finite Automata (DFA) if for each node, all of the edges leaving that node have distinct labels; otherwise it is a Nondeterministic Finite Automata (NFA). The NFAs are most convenient for describing regular languages, but testing if a string is accepted by an NFA is challenging. 

We can represent an NFA as a set of triples:
* $(s_i,c_i,t_i) \in S\times\Sigma\times S$ which represents the edge from node $s_i$ to node $t_i$ labelled with symbol $c_i$

Equivalently we could view it as a partial function $\delta$ from $S\times\Sigma$ to $S$ which explains which state to change to for a given symbol.

We can see if a deterministic finite automata accepts a string by using the DFA to change state for each symbol and checking to see if the last state is a final state.

### Exercise: 
* Draw a DFA for the RE '0*.(1.0*.1.0*)*' and trace through the state changes for 0001101110001000

## Converting a Regular Expression to an NFA
We can define this algorithm to convert a Regular Expression R to an NFA as follows.
* construct the parse tree for R
  * this is pretty easy for humans to do, for small examples, but we won't explain how to do this programmatically until we discuss parsing!
* for each node in the parse tree constuct an NFA with a single start state and a single final state as follows:
  * for a leaf labelled with a symbol $s$ we have s simple NFA  start -> final where the edge is labelled with s
  * for a dot node R1 . R2
    * add an epsilon edge between the final node of R1 and the start node of R2
    * OR just identify the final state of R1 with the start state of R2
  * for a bar node R1 | R2
    * create a new start and final node and have epsilon edges from the new start node to the starts of R1 and R2
    and expilon edges from the final nodes of R1 and R2 to the new final node.
    * OR just identify the start states of R1 and R2 and the final state of R1 and R2
  * for a star node R1*
    * create new start and final node with epsilon edge from the new start to the start and to the final,
   and and epsilon edge from the final of R1 to the start of R1

The only place we need to introduce epsilon edges is for the star nodes.

### Exercise
Convert the following regular expression $R_1$ to an NFA $N_1$ using the algorithm above:
* R1 = ```(a.(b|c))*```
*  Remove the epsolon edges from $N_1$ to get a new NFA $N_2$
*  Do the same with R2 = ```(a.b|c)*``` (which is the same as ```((a.b)|c)*```
*  Now with ```(a.b*|c)*



## Recognizing strings using an DFA
This is a standard state machine where the node represents the current state and the edge determines
which state to move into next.

For a DFA D it is relatively easy to see if a string $\omega$ is accepted by D with the following algorithm.
First let $S$ be the set of states of the DFA and $\Sigma$ the alphabet for the DFA and assume one of the states is an "Error" state $E$.

Let $\delta:S\times \Sigma \rightarrow S$ be the function defined by
* $\delta(s_1,\sigma) = s_2$ if there is an edge from $s_1$ to $s_2$ labelled with $\sigma$
* $\delta(s_1,\sigma) = E$, if there is no edge from $s_1$ labelled with $\sigma$

The following algorithm will determine if a string $\omega$ of length $n$ is accepted by the DFA.
* let $s_0$ be the start state
* define $s_{i+1} = \delta(s_i,\omega_i)$ for each $i$ from 0 to $n-1$ if $s_i\ne E$, otherwise $s_{i+1}=E$
If $s_n$ is a final state, then the string is accepted, otherwise it is not accepted.

Note that we can have the final states labelled with token types, so a DFA could not only accept a string, it could classify it!

Here is a Python program for determining if a DFA recognizes a string of characters
``` python
# we represent a dfa by a dictionary giving the start state, final states, and a set of edges of the form (head,tail,char)
dfa1 = {'start':0,'final':{0},'edges':{(0,1,'a'),(1,0,'b'),(1,0,'c')}}

def edge(dfa,state,c):
    ''' return the next state, or 'error' if there is no next state'''
    next_states = [e[1] for e in dfa['edges'] if e[0]==state and e[2]==c]
    if len(next_states)==1:
        return next_states[0]
    else:
        return 'error'

def accepts(dfa,chars):
    ''' return True if the dfa accepts the string of chars '''
    state = dfa['start']
    for i in range(len(chars)):
        c = chars[i]
        state1 = edge(dfa,state,c)
        print('traverse edge',state,c,state1)
        if state1=='error':
            return False
        else:
            state=state1
    return state in dfa['final']
    

```


## Recognizing strings using an NFA
In this case, we keep track of the set of all possible states the NFA could be in. This is a standard
construction that works with any non-deterministic algorithm. To do this we keep track of all of the possible states we might be in.

Let's define the algorithm more precisely, as in the text. You can convert this into a Python program fairly easily, but we'll use pseudo code.

We will assume the NFA is represented as a set $E$ of labelled edges $(s,t,c)\in S\times\Sigma^*$ where $S$ is the set of states and $\Sigma^*$ is the alphabet $\Sigma$ with the epsilon character $\epsilon$ added, where
* $(s,t,c)$ represents an edge from state $s$ to state $t$ with edge labelled $c$

Here are two NFAs
``` python
nfa1 = {'start':0,'final':{0,2},'edges':{(0,1,'a'),(1,0,'b'),(1,0,'c'),(1,2,'b'),(2,0,'c')}}
nfa2 = {'start':0,'final':{0,2},
        'edges':{(0,1,'epsilon'),(1,2,'epsilon'),(1,3,'epsilon'),(2,3,'epsilon'),
                 (1,3,'a'),(2,2,'a'),(3,3,'b'),(2,3,'c'),(3,2,'epsilon')}}
```

The first step is to define the epsilon closure of a set $T$ of states. This is the set of states you can get to by following only edges labelled with $\epsilon$.  

``` python
def epsilon_edge(NFA,S):
    ''' returns all states reachable from a state in s by an edge labelled epsilon '''
    return {e[1] for e in NFA['edges'] if e[0] in S and e[2]=='epsilon'}

def closure(NFA,S):
    ''' returns set of states reachable from a state in S following epsilon edges'''
    T=S
    next_states = epsilon_edge(NFA,S)
    print('epsilon edges',T,'epsilon',T.union(next_states))
    while not next_states.issubset(T):
        T = T.union(next_states)
        next_states = epsilon_edge(NFA,T)
        print('epsilon edges',T,'epsilon',T.union(next_states))
    return T
```

Once we have the closure we can easily write the NFA recognizer:
``` python
def dfa_edge(nfa,s,c):
    ''' returns closure of all states reachable from a state in s by an edge labelled c '''
    s1 = {e[1] for e in nfa['edges'] if e[0] in s and e[2]==c}
    s2 = closure(nfa,s1)
    return s2

def is_final(nfa,state):
    ''' return True if the nfa state is a final state '''
    return len(state.intersection(nfa['final']))>0

def nfa_accepts(nfa,chars):
    ''' returns True if the nfa accepts the string of chars '''
    state = closure(nfa,{nfa['start']})
    for i in range(len(chars)):
        c = chars[i]
        state1=state
        state = dfa_edge(nfa,state,c)
        print('dfa_edge',state1,c,state)
    return is_final(nfa,state)
```

## Exercise: Try this out!

## Converting an NFA to a DFA
The states of the DFA are the sets of states that the NFA could be in at that point in time.

We can define the next state function on the power set by 
* $\delta(U,\sigma) = \\{\delta(u,\sigma) : \forall u \in U\\}$
* the start state if $\\s_o\\}$ and the final states are those subsets that contain a final state.
We only care about those states which are reachable from the start state so in general we won't have $2^n$ states in our DFA,
but in the worst case, that is a possibility. So use a breadth-first or depth-first search to find those states (and give them
state names as integers not sets!) See the Textbook for details...


### Exercise: Write the NFA to DFA converter


## Finding the maximal match for a DFA
We usually want to find the longest prefix of a string that the DFA accepts.
To do this we just keep track of the last final state encountered. If we reach an error state,
then we revert back to the last final state.


How would we find the longest possible match for a DFA?
* run the algorithm above, but keep track of each $i$ for which $s_i$ is a final state,
* when an Error state is reached or the end of the string, return the last final state, and restart the DFA from position $i$.

### Exercise: Modify the DFA converter to return the longest match
Keep track of the last final state encountered and return that state and the recognized string, when a error state arises.

### Exercise: Write a function tokenizer(dfa,chars) to convert a string chars into a list of tokens
The function should repeatedly 
* find the longest prefix p of chars accepted by the dfa,
* add that to the list along with the accepting state (p,s)
* if no prefix is accepted, then skip the next character c and generate an error (c,'error')
* repeat until the end of the list

For example, with the DFA recognizing strings ending in 00 or 11, we would get
* nfa = {'start':0,final:{2,4},edges:{(0,1,'0'),(1,2,'0'),(0,3,'1'),(3,4,'1'),(2,2,'0'),(4,4,'1'),(2,3,'1'),(4,1,'0'),(1,3,'1'),(3,1,'0')}}
* tokenizer(nfa,"01000111010") -> [('01000111',4),('0','error'),('1','error'),('0','error')]



## Exercise: lexical_analyzer¶
Write a lexical_analyzer which accepts a list of token definitions of the form
``` python
(token_name,  NFA)
```
and generates a DFA for recognizing those tokens which could be used with the "tokenizer" function above.

Ideally we would use a Regular Expression instead of an NFA, but we need to read the next chapter to learn how to
parse a Regular Expression into a tree that can be converted into an NFA. We'll do this next week.


# Jupyter Notebook
[Here](NFA%2BDFA.ipynb) is a link to a jupyter notebook containing the code from this lesson
