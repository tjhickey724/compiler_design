/* Generated By:JavaCC: Do not edit this line. SLPDefaultVisitor.java Version 7.0.13 */
public class SLPDefaultVisitor implements SLPVisitor{
  public Object defaultVisit(SimpleNode node, Object data){
    node.childrenAccept(this, data);
    return data;
  }
  public Object visit(SimpleNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStart node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStatements node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPrintln node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTVarAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExp node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExp4 node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExp9 node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExp11 node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExp16 node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNumber node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTVariable node, Object data){
    return defaultVisit(node, data);
  }
}
/* JavaCC - OriginalChecksum=3c886df3a0349b539da3002d3dd8b28d (do not edit this line) */