package syntaxtree;

public class ClassDecl {
    Identifier i;
    VarDeclList v;
    MethodDeclList m;
    public ClassDecl( Identifier i,VarDeclList v,MethodDeclList m){
        this.i=i; this.v=v;this.m=m;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}