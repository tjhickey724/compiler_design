package syntaxtree;

public class MethodDeclList {
    public MethodDecl m;
    public MethodDeclList mlist;

    public MethodDeclList(MethodDecl m, MethodDeclList mlist){
        this.m=m;
        this.mlist=mlist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}