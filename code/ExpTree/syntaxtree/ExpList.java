package syntaxtree;

public class ExpList {
    ExpList a;
    Exp b;
    public ExpList(ExpList a, Exp b){
        this.a=a; this.b=b;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}