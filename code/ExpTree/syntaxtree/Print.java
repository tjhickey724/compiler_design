package syntaxtree;

public class Print extends Statement{
    Exp e;
    public Print(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}