# Three Address Code
A very common Intermediate representation for programs is called three address code.
This converts an abstract syntax tree into a sequence of labels and instructions
where each instruction has one of the following forms:
```
# arithmetic expressions
X = Y op Z  op is +,-,*,/,%,//,&&,||, ...
X = uop Z   uop is -,!,...

# conditional and unconditional jumps
if X op Y goto(L)  op is <,<=,==,>,>=,!=
jump L

# array operations
X[Y] = Z
X = Y[Z]
X = int[Y]   # create an array of size Y

# 2d arrays
X[A,B] = X[A][B] = X'[N*A+B]  if X in int[N][M] where X' in int[N*M]
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
  if t != 0 goto(L1)
  x = x / 2
  jump L2
L1:
  t2 = 3 * x
  x = t2 + 1
L2:
```

Try converting some simple program code to three address code, e.g.
```
for(int i=1; i<41; i++){
  a[i] = i*i+i+41;  // use
}
```

Also, try this one:
for (int i=0; i<10; i++){
  for (int j=0; j<10; j++){
     a[i][j] = i*j;  // a[i][j] represented as b[10*i+j] for a 1-d array b
  }
}

