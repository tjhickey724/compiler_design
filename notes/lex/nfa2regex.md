# Converting a NFA to a Regular Expression

In this note we sketch an algorithm for converting an NFA to a regular expression.
The key idea is to transform it by steps into a Generalized NFA (GNFA)
where the edges are labelled with regular expressions.  We can then show how
to eliminate duplicate edges (between the same two states) and eliminate
interion nodes (i.e. not start or final states).

## Step 1. Normalize to a single start and single final state
Create a single start state S0 and a single final state S1
and add epsilon links from S0 to the original start state(s)
and from the original final states to S1. If there is only one
start state and one final state you can skip this step.

## Step 2. Replace all edges with the same source S and same destination D
with a single edge labelled by the disjunction of all their edge labels.

For example
```
s1->s2  with label A
s1->s2  with label B
```
are replaced with
```
s1 -> s2 with label A|B
```
Note that this applies to self loops too, where s1 = s2

## Step 3. Eliminate non-start and non-final nodes one at a time
The idea here is to eliminate a node by adding many new edges between the other nodes
One might have to go back to step 2 if this creates duplicate edges.

The algorithm is as follows. Let s be some internal state (not start or final)
* Let u1,,,uj be the states with edges into s with labels A_i (which can be RegExs)
* Let B be the self-loop label on s (possibly epsilon)
* Let w1,...,wk be the states with edges from s into wj with labels C_j
* Remove the node s and all of the edges into and out of it, and replace them with edges as follows:
```
ui -> wj  labelled with Ai . B* . Cj
```
Clearly this accepts the same strings as any path through s must go from one of the ui to s and then to a vj

# Repeat steps 2 and 3
Do this until there is all nodes except start and final have been eliminated.
At which point the GNFA will consist of a single edge from start to final labelled with a RegEx R
which is equivalent tot he original NFA

We can prove this by induction on the size of the GNFA 
and each combination of steps 2 and 3 reduces the number of nodes by 1 until there are just 2 left.
The base case is a GNFA with a single edge labeled by a single RegEx.
