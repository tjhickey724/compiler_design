package syntaxtree;

public class If extends Statement{
    public Exp e;
    public Statement s1;
    public Statement s2;
    
    public If(Exp e,Statement s1,Statement s2){
        this.e=e; this.s1=s1; this.s2=s2;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}