package syntaxtree;

public class IntArrayType extends Type {
 
    public IntArrayType(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}