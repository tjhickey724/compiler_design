package syntaxtree;

public abstract class Exp {
    public abstract Object accept(Visitor visitor, Object data);
}
