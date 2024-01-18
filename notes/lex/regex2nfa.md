# Regular Expressions to NFAs

Below is a program that converts regular expressions to NFAs with a liberal use of epsilon transitions.
This algorithm is a key part of the process of building a lexical scanner as we typically define
tokens by regular expressions. After we convert it to an NFA, the next step is to convert the NFA to a DFA.

First we define some functions to generate parse trees from regular expression operators:
``` python
def star(R):
    return ('star',R)
def bar(R1,R2):
    return ('bar',R1,R2)
def dot(R1,R2):
    return ('dot',R1,R2)
```
e.g.
``` python
star(dot('a',bar('b','c))) ==>
('star',('dot','a',('bar', 'b','c')))
```

Now we can use this to convert a RegEx parse tree into an NFA recursively:
``` python
def convert_RE_to_NFA(RE,start):
    ''' 
        The idea is to generate an NFA from a regular expression
        and to give it a starting number for the states and keep track of the highest state generated
    '''
    if type(RE)==str:
        return {'start':start,'final':start+1,'last':start+1,'edges':((start,start+1,RE),)}
    elif RE[0]=='dot':
        R1 = convert_RE_to_NFA(RE[1],start+2)
        R2 = convert_RE_to_NFA(RE[2],R1['last']+1)
        #print('dot r1',RE,'\n',R1)
        #print('dot r2',RE,'\n',R2)
        return {'start':start,'final':start+1,'last':R2['last'],
            'edges':((start,R1['start'],'epsilon'),)
                     +R1['edges'] 
                    + ((R1['final'],R2['start'],'epsilon'),)
                     +R2['edges']
                     + ((R2['final'],start+1,'epsilon'),)}

    elif RE[0]=='bar':
        R1 = convert_RE_to_NFA(RE[1],start+2)
        R2 = convert_RE_to_NFA(RE[2],R1['last']+1)
        #print('bar r1',RE,'\n',R1)
        #print('bar r2',RE,'\n',R2)
        return {'start':start,'final':start+1,'last':R2['last'],
            'edges':(     (start,R1['start'],'epsilon'),) 
                        + R1['edges']
                        + ((R1['final'],start+1,'epsilon'),(start,R2['start'],'epsilon'),) 
                        + R2['edges']+
                          ((R2['final'],start+1,'epsilon'),)  }
    elif RE[0]=='star':
        R1 = convert_RE_to_NFA(RE[1],start+2)
        #print('star r1',RE,'\n',R1)
        return {'start':start,'final':start+1,'last':R1['last'],
            'edges':((start,R1['start'],'epsilon'),
                     (R1['start'],start+1,'epsilon'))  
                    + R1['edges'] 
                    + ((R1['final'],R1['start'],'epsilon'),)}
```

## Exercise: Extend RegEx language
* add some new operators such as
  * plus(R) which is one or more occurrences of R
  * repeat(R,n) which is exactly n occurrences of R
  * option(R) which is zero or one occurences of R

modify the '''convert_RE_to_NFA''' function to handle these extended operators



