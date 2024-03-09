package syntaxtree;

public class MethodDecl {
    public Type t;
    public Identifier i;
    public FormalList f;
    public VarDeclList v;
    public StatementList s;
    public Exp e;

    public MethodDecl(Type t, Identifier i, FormalList f,
                       VarDeclList v,StatementList s, Exp e){
        this.t=t;this.i=i;this.f=f;this.v=v;this.s=s;this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}