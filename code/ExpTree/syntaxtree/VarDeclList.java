package syntaxtree;

public class VarDeclList {
    public VarDeclList(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}