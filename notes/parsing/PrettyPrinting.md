# Pretty Printing with javacc

Once we have a parser for a programming language, it is fairly easy to create a pretty printer (and a cross compiler).
Here are some notes on how to pretty print mini-java. I won't show the whole code here, just snippets.

First is the main program for the parser.
The idea is that when we recognize a
```
/*
 * This is MiniJavaPP.jj file.
 * It will build a tree we can traverse using the Visitor pattern!
 */

options {
  IGNORE_CASE=false;
}

PARSER_BEGIN(MiniJavaPP)
public class MiniJavaPP {

  /** Main entry point. */
  public static void main(String args[]) {
    System.out.println("Reading from standard input...");
    MiniJavaPP t = new MiniJavaPP(System.in);
    try {
      t.Start(0);
      System.out.println("Thank you.");
    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
PARSER_END(MiniJavaPP)

/* Program Syntax */

void Start(int indent):
{}
{
  MainClass(indent)
  ClassDecls(indent)
  <EOF>

}

void MainClass(int indent) :
{Token s,t;}
{
  "class" 
  t=<ID> 
  <LCURLY>  
      {System.out.println("class "+t.image+"{");}
  "public" "static" "void" <MAIN> 
      {System.out.print("    public static void main"); }
  <LPAREN> "String" <LBRACKET> <RBRACKET> 
  s=<ID> 
  <RPAREN>
      {System.out.println("(String[] "+s.image + "){");  }
  <LCURLY> 
  Statement(indent+1) 
  <RCURLY>
  <RCURLY>
      {System.out.println("\n  }\n}\n"); }
}

```


Here is part of the code for Statement:
```

void Statement(int indent) :
{Token t;}
{ 
  <LCURLY> 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("{");}
  Statements(indent+1) 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("}");}
  <RCURLY> 

  |
      {System.out.format("%1$"+4*indent+"s", "");}
  <IF> 
  <LPAREN> 
      {System.out.print("if (");}
  Exp()
  <RPAREN> 
      {System.out.println(")");}
  Statement(indent+1) 
  <ELSE> 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("else ");}
  Statement(indent+1)
      {System.out.println();}

  |
// et cetera
}
```
  
