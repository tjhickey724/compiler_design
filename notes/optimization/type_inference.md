# Type Inference

We can perform a simple kind of type inference using the Data Flow Analysis approach by keeping a symbol table
where the values are 
* $\bot$ undefined
* boolean
* int
* int[]
* $\top$ overdefined
```
where $\bot$ is the smallest element and $\top$ is the largest.

