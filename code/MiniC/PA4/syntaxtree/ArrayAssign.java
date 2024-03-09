package syntaxtree;

public class ArrayAssign extends Statement{
    public Identifier i;
    public Exp e1;
    public Exp e2;
    
    public ArrayAssign(Identifier i,Exp e1,Exp e2){
        this.i=i; this.e1=e1; this.e2=e2;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}