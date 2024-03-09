package syntaxtree;

public class IntegerType extends Type {
 
    public IntegerType(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}