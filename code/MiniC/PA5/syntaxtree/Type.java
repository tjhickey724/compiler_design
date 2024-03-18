package syntaxtree;

public abstract class Type {
    public abstract Object accept(Visitor visitor, Object data);
    
}
