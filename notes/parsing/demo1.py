      

class parser():
    def __init__(self,tokens):
        self.pos=0
        self.tokens=tokens
        self.tree=[]
    
    def __str__(self):
        return (self.pos,self.tokens[:self.pos],self.tokens[self.pos:])
    
    def error(self):
        raise Exception("parsing error at pos "+str(self.pos)+" "+str(self.tokens[self.pos:]))
    
    def getToken(self,):
        return self.tokens[self.pos]
    
    def advance(self):
        self.pos += 1
    
    def eat(self,token):
        if self.getToken()==token:
            self.advance()
        else:
            self.error()

    def S(self):
        token = self.getToken()
        if token=='if':
            self.eat('if')
            e1=  self.E()
            self.eat('then')
            s1 = self.S()
            self.eat('else')
            s2 = self.S()
            return ('if',e1,s1,s2)
        elif token=='begin':
            self.eat('begin')
            s = self.S()
            rest = self.L()
            return ('seq',s,rest)
        elif token=='print':
            self.eat('print')
            e = self.E()
            return ('print',e)
        else:
            self.error()
    
    def L(self):
        token = self.getToken()
        if token=='end':
            self.eat('end')
            return ('end',)
        elif token==';':
            self.eat(';')
            s1 = self.S()
            rest = self.L()
            return ('seq',s1,rest)
        else:
            self.error()
    
    def E(self):
        token = self.getToken()
        if token=='num':
            self.eat('num')
            return ('num',)
        else:
            self.error()



# define a sample input program 
p1 = '''
if num then 
   begin 
      print num ; 
      if num then
          print num
      else
          print num ;
      print num 
   end 
else 
   if num then 
      print num 
   else print num
'''.split()

# parse the program
print(p1)
test = parser(p1)
print(test.__str__())
p = test.S()

# pretty print the output
def draw_tree(indent,t):
    ''' pretty print the tree t'''
    if type(t)==tuple:
        for i in range(len(t)):
            draw_tree(indent+1,t[i])
    else:
        print(' '*indent*4+t)
draw_tree(0,t)
