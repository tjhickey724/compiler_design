package syntaxtree;

public class MainClass {
    public Identifier i;
    public Statement s;
    
    public MainClass(Identifier i,Statement s){
        this.i=i; this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}
