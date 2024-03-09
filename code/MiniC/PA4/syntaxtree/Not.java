package syntaxtree;

public class Not extends Exp{
    public Exp e;
    
    public Not(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}