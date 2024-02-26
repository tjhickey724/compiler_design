package syntaxtree;

public class This extends Exp{

    public This(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}