package syntaxtree;

public class VarDecl {
    Type t;
    Identifier i;
    public VarDecl( Type t, Identifier i){
        this.t=t; this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}