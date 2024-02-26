package syntaxtree;

public class IdentifierType extends Type{
    String x;
    public IdentifierType(String x){
        this.x=x;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}