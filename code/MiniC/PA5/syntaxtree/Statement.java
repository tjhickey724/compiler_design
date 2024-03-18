package syntaxtree;

public abstract class Statement {
    public abstract Object accept(Visitor visitor, Object data);
}
