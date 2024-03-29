# Three Address Code
A very common Intermediate representation for programs is called three address code.
This converts an abstract syntax tree into a sequence of labels and instructions
where each instruction has one of the following forms:
```
X = Y op Z  op is +,-,*,/,%,//,&&,||, ...
X = uop Z   uop is -,!,...
if X op Y jump L1 else jump L2  op is <,<=,==,>,>=,!=
jump L
X[Y] = Z
X = Y[Z]
X = int[Y]   # create an array of size Y
```
We can convert arithmetic expressions into 3-address code 
by introducing temporary variables for each non-leaf node in the Abstract Syntax Tree. 
We will use T1,T2,... to represent temporaries.

For example, 
```
C = (A-B)*(A+B)
```
could be translated into 3 address code as
```
T1 = A-B
T2 = A+B
C = T1*T2
```
Likewise, we can convert control structures like if, while, switch, into three address code with labels,
where we use L1, L2, L3, ... for labels.

For example,
```
  if (x%2==0){
    x = x/2;
  } else {
    x = 3*x+1;
  }
```
could be converted as follows:
```
t1 = x % 2
if t == 0 jump L4 else jump L5
L4:
x = x / 2
jump L6
L5:
t2 = 3 * x
x = t2 + 1
L6:
```

Try converting some simple program code to three address code:
