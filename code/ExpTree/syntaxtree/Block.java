package syntaxtree;

public class Block extends Statement{
    StatementList s;
    public Block(StatementList s){
        this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}