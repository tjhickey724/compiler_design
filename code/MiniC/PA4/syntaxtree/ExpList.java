package syntaxtree;

public class ExpList {
    public ExpList elist;
    public Exp e;
    
    public ExpList(ExpList elist, Exp e){
        this.e=e; this.elist=elist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}