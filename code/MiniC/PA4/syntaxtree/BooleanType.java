package syntaxtree;

public class BooleanType extends Type{
 
    public BooleanType(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}
