package syntaxtree;

public class StatementList {
    public StatementList(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}