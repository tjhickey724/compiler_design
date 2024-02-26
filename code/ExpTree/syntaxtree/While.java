package syntaxtree;

public class While extends Statement{
    Exp e;
    Statement s;
    public While(Exp e,Statement s){
        this.e=e; this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}