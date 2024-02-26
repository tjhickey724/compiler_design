package syntaxtree;

public class Assign extends Statement{
    public Identifier i;
    public Exp e;
    
    public Assign(Identifier i,Exp e){
        this.i=i; this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}