# Type Inference

We can perform a simple kind of type inference using the Data Flow Analysis approach by keeping a symbol table
where the values are 
* $\bot$ undefined
* boolean
* int
* int[]
* $\top$ overdefined 

where $\bot \lt {\rm boolean}, {\rm int}, {\rm int[]} \lt \top$ is a partially ordered set.

We initialize all variables in the three address code to be $\bot$ and then update the values with the natural rules,

Let's try it with a simple python program where we assume that all variables can only be booleans, integers, or integer arrays...
```
def p(a,b,c):
    if (b<c):
      a[b] = c-b
    else:
      a[b] = 0
    return a[b]
```
Let's compile this to 3 address code and then determine the types of the variables ...

We could also work from the top down, and keep track of the set of types a variable could take on in the program.
Each instruction would be used to restrict those sets.










