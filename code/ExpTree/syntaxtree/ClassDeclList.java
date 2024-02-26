package syntaxtree;

public class ClassDeclList {
    public ClassDeclList(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}