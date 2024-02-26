package syntaxtree;

public class IdentifierType extends Type{
    public String s;
    
    public IdentifierType(String s){
        this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}