/* Generated By:JJTree: Do not edit this line. ASTPrintln.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTPrintln extends SimpleNode {
  public ASTPrintln(int id) {
    super(id);
  }

  public ASTPrintln(SLP p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SLPVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b78e6c3dcba512fdea935d37b502f454 (do not edit this line) */
