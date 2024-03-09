package syntaxtree;

public class StatementList extends Statement {
    public Statement s;
    public StatementList slist;

    public StatementList(Statement s, StatementList slist){
        this.s=s;
        this.slist=slist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}