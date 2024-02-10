# SLP-ConcreteST
This is the jjtree code that builds the Concrete Syntax trees for a language of straight line programs:
consisting of a sequence of assignment statements ```V = Exp;``` and print statements ```System.out.println(Exp)```

We run this
```
% java -classpath ../javacc.jar jjtree SLP.jjt
% java -classpath ../javacc.jar javacc SLP.jj
% javac SLP.java
% java SLP
a= 3 + 1/(7 + 1/15);
b = 1;
b = (b + 2/b)/2;
^D
```

