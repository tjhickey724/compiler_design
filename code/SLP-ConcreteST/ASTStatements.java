/* Generated By:JJTree: Do not edit this line. ASTStatements.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTStatements extends SimpleNode {
  public ASTStatements(int id) {
    super(id);
  }

  public ASTStatements(SLP p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SLPVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fc14351a37744f6dd0619c85d9e58917 (do not edit this line) */
