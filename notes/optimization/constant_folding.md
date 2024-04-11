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
    in z = scale*(maxval-x)/(maxval-minval))
    System.out.println(z);
    x = 2*x;
}
```
this translates to 3 address code as follows where I'm using labels for every basic block
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
and the flow graph is
```
BB0 -> BB1
BB1 -> BB2
BB1 -> BB3
BB2 -> BB1
```
To do constant propogation we create a symbol table where the initial value for all variables in "undefined"

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

### Optimization pass
Once we know which variables are constant, we can optimize the 3 address code, by 
* replacing all expressions with only constant variables with their constant value 
* evaluating any constant conditional tests, and replacing them with uncondiational jumps
* removing all blocks that are no longer reachable
value

For the example above, what are the final values for the symbol table
| var | value |
| --- | --- |
|debugging ||
|maxvasl||
|minval||
|scale||
|x||
|n||
|z||
|t1||
|t2||
|t3||




