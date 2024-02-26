package syntaxtree;

public class Identifier{
    String s;
    public Identifier(String s){
        this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}