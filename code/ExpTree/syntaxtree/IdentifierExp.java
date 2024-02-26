package syntaxtree;

public class IdentifierExp extends Exp{
    String s;
    public IdentifierExp(String s){
        this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}