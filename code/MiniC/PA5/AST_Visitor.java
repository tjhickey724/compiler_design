import syntaxtree.*;

/* 
 * this class uses the Visitor pattern 
 * to pretty print the miniJava program abstract syntax tree
 * It works for all of miniJava, not just the miniC subset
*/

 public class AST_Visitor implements Visitor
 {
   private int indent = 0;
 
   private String indentString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4*indent; ++i) {
        sb.append(' ');
        }
        return sb.toString();
   }

     /*
  getClassName(c) returns name of the Java class c without the package prefix
  */
  public static String getClassName(Object c){
     String s = c.getClass().getName();
     return s.substring(s.lastIndexOf('.')+1);
   }

   public Object visit(Program node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.m.accept(this,data);
        if (node.c!=null){
            node.c.accept(this,data);
        }
        --indent;
        return data;
   }

   public Object visit(MainClass node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.i.accept(this,data);
        node.s.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(ClassDecl node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.i.accept(this,data);
        node.v.accept(this,data);
        node.m.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(VarDecl node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.t.accept(this,data);
        node.i.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(MethodDecl node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.t.accept(this,data);
        node.i.accept(this,data);
        if (node.f!=null){
            node.f.accept(this,data);
        }
        if (node.v!=null){
            node.v.accept(this,data);
        }
        if (node.s!=null){
            node.s.accept(this,data);
        }
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Formal node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.t.accept(this,data);
        node.i.accept(this,data);
        --indent;
        return data;
   }

   public Object visit(IntArrayType node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(IntegerType node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(BooleanType node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(IdentifierType node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        String s = node.s;
        System.out.println(" "+s);
        --indent;
        return data;
   }
   public Object visit(Block node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        if (node.slist!=null){
            node.slist.accept(this,data);
        }
        --indent;
        return data;
   }
   public Object visit(If node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        node.s1.accept(this,data);
        node.s2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(While node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        node.s.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Print node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Assign node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.i.accept(this,data);
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(ArrayAssign node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.i.accept(this,data);
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }

   public Object visit(And node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(LessThan node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Plus node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Minus node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Times node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(ArrayLookup node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e1.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(ArrayLength node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Call node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        if (node.e1!=null){
             node.e1.accept(this,data);
        }
        node.i.accept(this,data);
        node.e2.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(IntegerLiteral node, Object data){
        System.out.print(indentString() + getClassName(node));
        ++indent;
        int i = node.i;
        System.out.println(" "+i);
        --indent;
        return data;
   }
   public Object visit(True node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(False node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(IdentifierExp node, Object data){
        System.out.print(indentString() + getClassName(node));
        ++indent;
        String s = node.s;
        System.out.println(" "+s);
        --indent;
        return data;
   }
   public Object visit(This node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        --indent;
        return data;
   }
   public Object visit(NewArray node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(NewObject node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.i.accept(this,data);
        --indent;
        return data;
   }

   public Object visit(Not node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(Identifier node, Object data){
        System.out.print(indentString() + getClassName(node));
        ++indent;
        String s = node.s;
        System.out.println(" "+s);
        --indent;
        return data;
   }
   public Object visit(ExpGroup node, Object data){
     System.out.println(indentString() + getClassName(node));
     ++indent;
     if (node.e!=null){
         node.e.accept(this,data);
     }
     --indent;
     return data;
   }
   public Object visit(ClassDeclList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.c.accept(this,data);
        if (node.clist!=null){
            node.clist.accept(this,data);
        }
        --indent;
        return data;
   }
   public Object visit(ExpList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        if (node.elist!=null){
            node.elist.accept(this,data);
        }
        --indent;
        return data;
   }
   public Object visit(FormalList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.f.accept(this,data);
        if (node.flist!=null){
            node.flist.accept(this,data);
        }
        --indent;
        return data;
   }
   public Object visit(MethodDeclList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.m.accept(this,data);
        if (node.mlist!=null){
            node.mlist.accept(this,data);
        }
        --indent;
        return data;
   }
   public Object visit(StatementList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        
        if (node.slist!=null){
            node.slist.accept(this,data);
        }
        node.s.accept(this,data);
        --indent;
        return data;
   }
   public Object visit(VarDeclList node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.v.accept(this,data);
        if (node.vlist!=null){
            node.vlist.accept(this,data);
        }
        
        --indent;
        return data;
   }
}

 

 