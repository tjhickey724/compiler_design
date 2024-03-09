package syntaxtree;

public class IntegerLiteral extends Exp{
    public int i;
    
    public IntegerLiteral(int i){
        this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}