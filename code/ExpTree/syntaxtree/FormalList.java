package syntaxtree;

public class FormalList {
    public FormalList(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}