package syntaxtree;

public class ClassDeclList {
    public ClassDecl c;
    public ClassDeclList clist;

    public ClassDeclList(ClassDecl c,ClassDeclList clist){
        this.c=c;
        this.clist=clist;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}