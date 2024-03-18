package syntaxtree;

public class Block extends Statement{
    public StatementList slist;
    
    public Block(StatementList slist){
        this.slist=slist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}