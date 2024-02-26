package syntaxtree;

public class NewArray extends Exp{
    Exp e;
    public NewArray(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}