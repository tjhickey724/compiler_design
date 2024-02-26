package syntaxtree;

public class ArrayLength extends Exp{

    Exp e1;
    public ArrayLength(Exp e1){
        this.e1=e1;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}