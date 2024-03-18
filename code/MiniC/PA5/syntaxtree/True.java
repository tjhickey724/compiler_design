package syntaxtree;

public class True extends Exp{

    public True(){

    }


    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}