package syntaxtree;

public class FormalList {
    public Formal f;
    public FormalList flist;

    public FormalList(Formal f, FormalList flist){
        this.f=f;
        this.flist=flist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}