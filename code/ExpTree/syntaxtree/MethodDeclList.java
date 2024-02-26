package syntaxtree;

public class MethodDeclList {
    public MethodDeclList(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}