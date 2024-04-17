# Constant Folding

The idea behind constant folding is to determine whether there are any variables that have a constant value throughout the flow graph,
and if so to replace them with that value. This is very common in coding, since it is good practice to give names to important constants
in a program. In Java we often represent them as "final static" variables but not all languages have that construct and Java variables can
represent constants even if they aren't declared to be "final static".

## Example
``` java
boolean debugging = false;
int maxval = 20;
int minval = 10;
int scale = 100;
int x = minval;
while (x<maxval){
    int z = scale*(maxval-x)/(maxval-minval))
    System.out.println(z);
    x = 2*x;
}
```
With your basic programmer knowledge, which of these variables are not constant, and why?

This program translates to 3 address code as follows where I'm using labels for every basic block
```
BB0:
debugging = 0
maxvasl=20
minval=10
scale=100
x = minval

BB1:
if-false x<maxval goto BB3:

BB2:
t1 = maxval - x
t2 = maxval-minval
t3 = t1*scale
z = t3/t2
print(z)
x = 2*x
goto L1:

BB3:
end
```
and the flow graph is as follows with one loop ```BB1->BB2-> BB1```
```
BB0 -> BB1
BB1 -> BB2
BB1 -> BB3
BB2 -> BB1
```
To do constant propogation we create a symbol table where the initial value for all variables is "UNDEFINED"

We then process each instruction and update the symbol table as follows:
* if a literal value is assigned to an undefined variable, we record that in the symbol table
* if a literal vaslue is assigned to an variable which has a value, then
  * do nothing if the values agree
  * assign the value as NotConstant if they disagree
* if the instruction is a binary operation ```A = B OP C``` then
  * if B or C is undefined, this is an error
  * if B and C are constants then evaluate to get a number d and treat this as an assignment A = d
  * if B or C is NotContant, then assign NotConstant to A

We then traverse the flow graph  iteratively until there is no change.

### NOTE on Graph Traveral...
The best way to traverse the graph is using a multiple pass where in each pass we perform a depth first left-to-right traveral but not following edges that lead to a node that has already been visited. The paths we traverse decompose the graph into a spanning tree with the untravelled edges corresponding to loops.
For constant folding we could process the instructions in any random order and it wouldn't effect the outcome, but could slow down the convergence.

Why does this algorithm always terminate? (Hint: the values always go up from Undefined, to a constant value, and possibly to NotConstant).

### Example.
For the program above, we will determine the constant state of all variables at the beginning and ending of each basic block.
From the flow graph, 
```
BB0 -> BB1
BB1 -> BB2
BB1 -> BB3
BB2 -> BB1
```
we will generate a set of equations
```
IN[BB1] = OUT[BB0] join OUT[BB2]
IN[BB2] = OUT[BB1]
IN[BB3] = OUT[BB1]
OUT[BB0] = F(IN[BB0])
OUT[BB1] = F(IN[BB1])
OUT[BB2] = F(IN[BB2])
OUT[BB3] = F(IN[BB3])
```
where the function F gives the constant status after the block in terms of the constant status before the block.

Our initial state is that all variables are undefined. Let's use "U" for undefined and "N" for not_constant
```
s0 = (debugging = U, maxvasl=U, minval=U, scale=U, x=U, t1=U, t2=U, t3=U, z=U)
IN[BB0]=OUT[BB0] = s0
IN[BB1]=OUT[BB1] = s0
IN[BB2]=OUT[BB2] = s0
IN[BB3]=OUT[BB3] = s0
```
So initially  IN[B]=OUT[B] = s0 for all basic blocks B. As we iterate the values will get
defined and possibly multiply defined, and we stop when the there are no changes.

A the end of block BB0, we see that some variables now have values:
```
OUT[BB0] = F(IN[BB0}) = F(s0) = s1
where s1 = (debugging = 0, maxvasl=20, minval=10, scale=100, x=10, t1=U, t2=U, t3=U, z=U)
```

There are two blocks that have links to the beginning of BB1, those are BB0 and BB2.
We have computed OUT[BB0] =s1, and OUT[BB2]=s0 our initial value, so
```
IN[BB1] = OUT[BB0] join OUT[BB2] = s1 join s0 = s1
```
where the join operator takes the least upper bound of the values for each variable as defined above.

At the end of BB1, the state is the same as the start as there are no definitions in BB1, so
```
OUT[BB1] = s1
```
BB2 only has on block linking to it, BB1, so 
```
IN[BB2] = OUT[BB1] = s1
```
and we can now calcuate OUT[BB2]  by looking at the assignmennts in it
```
OUT[BB2] = s2
s2 = (debugging = 0, maxvasl=20, minval=10, scale=100, x=10,
     t1=maxval-x = 10,
     t2=maxval-minval = 10,
     t3=t1*scale = 1000,
     z=t3/t2 = 100
     x = 2*x = 20)
 = (debugging = 0, maxvasl=20, minval=10, scale=100, x=20, t1=10, t2=10, t3=1000, z=100)
```
So everything has a value, but we know that the next time we go through the loop x,t1,t3,and z will
get different values and so they will be multiply defined and hence have value $N$

Now lets use this to calculate IN[BB1]
```
IN[BB1] = OUT[BB0] join OUT[BB2] = s1 join s2 = s3
where s3 =
 (debugging = 0, maxval=20, minval=10, scale=100, x=10, t1=U, t2=U, t3=U, z=U)
join
(debugging = 0, maxval=20, minval=10, scale=100, x=20, t1=10, t2=10, t3=1000, z=100)
=  (debugging = 0, maxval=20, minval=10, scale=100, x=N, t1=10, t2=10, t3=1000, z=100)
```

So now IN[BB2] = s2 and 
```
OUT[BB2] = F(IN[BB2]) = s3
where s3 = ????
```

Doing one more iteration we will reach our fixed point....
```
s = (debugging = 0, maxvasl=20, minval=10, scale=100, x=N, t1=B, t2=10, t3=N, z=N)

### Optimization pass
Once we know which variables are constant, we can optimize the 3 address code, by 
* replacing all expressions with only constant variables with their constant value 
* evaluating any constant conditional tests, and replacing them with uncondiational jumps
* removing all blocks that are no longer reachable
value

For the example above, what are the final values for the symbol table
| var | value |
| --- | --- |
|debugging |0|
|maxval|20|
|minval|10|
|scale|100|
|x|N|
|z|N|
|t1|N|
|t2|10|
|t3|N|

```
BB0:
debugging = 0
maxvasl=20
minval=10
scale=100
x = minval

BB1:
if-false x<maxval goto BB3:

BB2:
t1 = maxval - x
t2 = maxval-minval
t3 = t1*scale
z = t3/t2
print(z)
x = 2*x
goto L1:

BB3:
end
```

and now we can optimize it by removing constant variables from the program
and replacing them in expressions with their constant values
```
BB0:
debugging = 0
maxvasl=20
minval=10
scale=100
x = minval

BB1:
if-false x<maxval goto BB3:

BB2:
t1 = maxval - x
t2 = maxval-minval
t3 = t1*scale
z = t3/t2
print(z)
x = 2*x
goto L1:

BB3:
end
```




