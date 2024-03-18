package syntaxtree;

public class ArrayLength extends Exp{

    public Exp e;
    public ArrayLength(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}