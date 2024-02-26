package syntaxtree;

public class NewObject extends Exp{
    Identifier i;
    public NewObject(Identifier i){
        this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}