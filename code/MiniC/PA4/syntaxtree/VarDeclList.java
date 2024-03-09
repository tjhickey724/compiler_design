package syntaxtree;

public class VarDeclList {
    public VarDecl v;
    public VarDeclList vlist;

    public VarDeclList(VarDecl v, VarDeclList vlist){
        this.v=v;
        this.vlist=vlist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}