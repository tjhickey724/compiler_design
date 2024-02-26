package syntaxtree;

public class Program {
    MainClass m;
    ClassDeclList c;
    public Program(MainClass m, ClassDeclList c){
        this.m=m; this.c=c;
    }

    public Object accept(Visitor visitor, Object data) {

        return
        visitor.visit(this, data);
      }
    
}
