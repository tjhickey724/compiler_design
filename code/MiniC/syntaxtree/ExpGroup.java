package syntaxtree;


public class ExpGroup extends Exp{
    // this corresponds to a parenthesized expression
    // we only need it for pretty printing....
    public Exp e;
    
    public ExpGroup(Exp e){
        this.e=e; 
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}