import syntaxtree.*;

public class DumpVisitor implements Visitor{
    
  public Object visit(Program node, Object data){
    System.out.println("Program");
    return(data);}
  public Object visit(MainClass node, Object data){return(data);}
  public Object visit(ClassDecl node, Object data){return(data);}
  public Object visit(VarDecl node, Object data){return(data);}
  public Object visit(MethodDecl node, Object data){return(data);}
  public Object visit(Formal node, Object data){return(data);}
  public Object visit(Type node, Object data){return(data);}
  public Object visit(IntArrayType node, Object data){return(data);}
  public Object visit(IntegerType node, Object data){return(data);}
  public Object visit(BooleanType node, Object data){return(data);}
  public Object visit(IdentifierType node, Object data){return(data);}
  public Object visit(Block node, Object data){return(data);}
  public Object visit(If node, Object data){return(data);}
  public Object visit(While node, Object data){return(data);}
  public Object visit(Print node, Object data){return(data);}
  public Object visit(Assign node, Object data){return(data);}
  public Object visit(ArrayAssign node, Object data){return(data);}
  //public Object visit(Exp node, Object data){return(data);}
  public Object visit(And node, Object data){return(data);}
  public Object visit(LessThan node, Object data){return(data);}
  public Object visit(Plus node, Object data){
    System.out.print("plus(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}
  public Object visit(Minus node, Object data){
    System.out.print("minus(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}
  public Object visit(Times node, Object data){
    System.out.print("times(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}
  public Object visit(ArrayLookup node, Object data){return(data);}
  public Object visit(ArrayLength node, Object data){return(data);}
  public Object visit(Call node, Object data){return(data);}
  public Object visit(IntegerLiteral node, Object data){
    System.out.print(node.i);
    return(data);}
  public Object visit(True node, Object data){return(data);}
  public Object visit(False node, Object data){return(data);}
  public Object visit(IdentifierExp node, Object data){return(data);}
  public Object visit(This node, Object data){return(data);}
  public Object visit(NewArray node, Object data){return(data);}
  public Object visit(NewObject node, Object data){return(data);}
  public Object visit(Not node, Object data){return(data);}
  public Object visit(Identifier node, Object data){return(data);}
  public Object visit(ClassDeclList node, Object data){return(data);}
  public Object visit(ExpList node, Object data){return(data);}
  public Object visit(FormalList node, Object data){return(data);}
  public Object visit(MethodDeclList node, Object data){return(data);}
  public Object visit(StatementList node, Object data){return(data);}
  public Object visit(VarDeclList node, Object data){return(data);}
}

