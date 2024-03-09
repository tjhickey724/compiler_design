package syntaxtree;

public class False extends Exp{

    public False(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}