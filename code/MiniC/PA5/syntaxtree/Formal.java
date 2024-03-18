package syntaxtree;

public class Formal {
    public Type t;
    public Identifier i;
    
    public Formal( Type t, Identifier i){
        this.t=t; this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}