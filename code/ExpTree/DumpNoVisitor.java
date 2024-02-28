import syntaxtree.*;

public class DumpNoVisitor {
    
    public static void visit(Object node){
        if (node.getClass()==IntegerLiteral.class){
            IntegerLiteral x = (IntegerLiteral)node;
            System.out.print(x.i);

        }else if (node.getClass()==Times.class){
            Times x = (Times) node;
            System.out.print("times(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else if (node.getClass()==Plus.class){
            Plus x = (Plus) node;
            System.out.print("plus(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else if (node.getClass()==Minus.class){
            Minus x = (Minus) node;
            System.out.print("minus(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else {
            System.out.println("unknown class: "+node.getClass());
        }
    }
    
}
