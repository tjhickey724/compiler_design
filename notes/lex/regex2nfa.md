# Regular Expressions to NFAs

Below is a program that converts regular expressions to NFAs with a liberal use of epsilon transitions.

``` python
def star(R):
    return ('star',R)
def bar(R1,R2):
    return ('bar',R1,R2)
def dot(R1,R2):
    return ('dot',R1,R2)

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
