import syntaxtree.*;

/* 
 * this class uses the Visitor pattern 
 * to convert a miniC program into a Pascal program
 * we can run the Pascal program using this link:
 * https://onecompiler.com/pascal
 * Here is an article about Pascal syntax and semantics:
 * https://en.wikipedia.org/wiki/Pascal_(programming_language)
 * The idea is to have each node return its string representation!
 * 
 * Here is a sample Pascal program
PROGRAM Test;
VAR
   radius: REAL;

FUNCTION CircleArea(r : REAL): REAL;
BEGIN
    CircleArea := 3.1415 * r * r;
END;

BEGIN
    WRITE('Area of circle with radius 2.0: ');
    WRITELN(CircleArea(2.0):6:1);
    WRITE('Area of circle with radius 5.0: ');
    WRITELN(CircleArea(5.0):6:1);
    WRITE('Enter your own radius: ');
    READLN(radius);
    WRITE('Area of circle with radius ', radius:3:1,': ');
    WRITELN(CircleArea(radius));   { ugly - formatting missing for real }
    radius := 5.0;
    radius := CircleArea(radius);
    WRITELN(radius);               { can you guess the output ? }
END.
 
*/

 public class Pascal_Visitor implements Visitor
 {
 
   private String indentString(int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4*indent; ++i) {
        sb.append(' ');
        }
        return sb.toString();
   }

   public Object visit(Program node, Object data){
     return null;
   }

   public Object visit(MainClass node, Object data){
     return null;
   }
   public Object visit(ClassDecl node, Object data){
     return null;
   }
   public Object visit(VarDecl node, Object data){
        int indent = (int) data; 
        String t = (String) node.t.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        return indentString(indent)+"VAR"+ " "+i +":"+t+";\n";
   }
   public Object visit(MethodDecl node, Object data){
        // 
        int indent = (int) data; 
        String type = (String) node.t.accept(this,indent);
        String id = (String) node.i.accept(this,indent);
        String formalList = "";
        if (node.f!=null){
            formalList = (String) node.f.accept(this,indent);
        }
        String varList = "";
        if (node.v!=null){
            varList = (String) node.v.accept(this,indent+1);
        }
        String statementList = "";
        if (node.s!=null){
            statementList = (String) node.s.accept(this,indent+1);
        }
        String expr = (String) node.e.accept(this,indent);
        return 
        indentString(indent)+ "FUNCTION"+
        " "+id+"("+formalList+") :"+ type +";\n"+
        indentString(indent)+
        varList +
        "BEGIN\n"+
        statementList+
        indentString(indent+1)+id +":="+expr +";\n"+
        indentString(indent)+
        "\n"+indentString(indent)+"END;\n";
   }
   public Object visit(Formal node, Object data){
        // 
        int indent = (int) data; 
        String type = (String) node.t.accept(this,indent);
        String id = (String) node.i.accept(this,indent);
        return id+":"+type;
   }

   public Object visit(IntArrayType node, Object data){
     return null;
   }
   public Object visit(IntegerType node, Object data){
        // 
        int indent = (int) data; 
        return "INTEGER";
   }
   public Object visit(BooleanType node, Object data){
        // 
        int indent = (int) data; 
        return "BOOLEAN";
   }
   public Object visit(IdentifierType node, Object data){
     return null;
   }
   public Object visit(Block node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        if (node.slist!=null){
            result = (String) node.slist.accept(this,indent+1);
        }
        
        return indentString(indent)+"BEGIN\n"+
               result+
               indentString(indent)+"END\n";
   }
   public Object visit(If node, Object data){
        // 
        int indent = (int) data; 
        String expr = (String)node.e.accept(this,indent+1);
        String s1 = (String) node.s1.accept(this,indent+1);
        String s2 = (String) node.s2.accept(this,indent+1);
        return indentString(indent)+
        "IF ("+expr+") THEN\n"+
        s1+
        indentString(indent)+"ELSE\n"+
        s2 + ";";
   }
   public Object visit(While node, Object data){
     return null;
   }
   public Object visit(Print node, Object data){
        // 
        int indent = (int) data; 
        String e = (String) node.e.accept(this,indent);
        //--indent;
        return indentString(indent) + "writeln("+e+");\n";
   }
   public Object visit(Assign node, Object data){
        // 
        int indent = (int) data; 
        String i = (String) node.i.accept(this,indent);
        String e = (String) node.e.accept(this,indent);
        //--indent;
        return indentString(indent)+i+" := "+e+";\n";
   }

   
   public Object visit(ArrayAssign node, Object data){ 
     Identifier i = node.i;
     Exp e1 = node.e1;
     Exp e2 = node.e2;
     String id = (String) i.accept(this,0);
     String exp1 = (String) e1.accept(this,0);
     String exp2 = (String) e2.accept(this,0);
     data = id+"["+exp1+"] = "+exp2+";\n";
     return data; 
   } 


   public Object visit(And node, Object data){
     return null;
   }

   public Object visit(LessThan node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+"<"+e2;
   }
   public Object visit(Plus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" + "+e2;
   }
   public Object visit(Minus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" - "+e2;
   }
   public Object visit(Times node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" * "+e2;
   }
   public Object visit(ArrayLookup node, Object data){
     return null;
   }
   public Object visit(ArrayLength node, Object data){
     return null;
   }
   public Object visit(Call node, Object data){
        // 
        int indent = (int) data; 
        //String e1 = (String) node.e1.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);

        return i+"("+e2+")";
   }
   public Object visit(IntegerLiteral node, Object data){
        int indent = (int) data; 
        int i = node.i;
        return " "+i+" ";
   }
   public Object visit(True node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "true";
   }
   public Object visit(False node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "false";
   }
   public Object visit(IdentifierExp node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return " "+s+" ";
   }
   public Object visit(This node, Object data){
     return null;
   }
   public Object visit(NewArray node, Object data){
     return null;
   }
   public Object visit(NewObject node, Object data){
     return null;
   }

   public Object visit(Not node, Object data){
     return null;
   }
   public Object visit(Identifier node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return s;
   }

   public Object visit(ExpGroup node, Object data){
     String e="";
     if (node.e!=null){
         e = (String) node.e.accept(this,data);
     }
     return "("+e+")";
   }

   public Object visit(ClassDeclList node, Object data){
     return null;
   }
   public Object visit(ExpList node, Object data){
        // 
        int indent = (int) data; 
        String e = (String) node.e.accept(this,indent);
        if (node.elist!=null){
            String e1 = (String) node.elist.accept(this,indent);
            e = e1 + ","+e;
        }

        return e;
   }
   public Object visit(FormalList node, Object data){
        // 
        int indent = (int) data; 
        String f = (String) node.f.accept(this,indent);
        if (node.flist!=null){
            String f1 = (String) node.flist.accept(this,indent);
            f = f1 +","+f;
        }
        //--indent;
        return f;
   }
   public Object visit(MethodDeclList node, Object data){
        // 
        int indent = (int) data; 
        String m = (String) node.m.accept(this,indent);
        if (node.mlist!=null){
            String m2 = (String) node.mlist.accept(this,indent);
            m = m2 +"\n"+m; 
        }
        return m;
   }
   public Object visit(StatementList node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        
        if (node.slist!=null){
            String s = (String) node.slist.accept(this,indent);
            result = result+s;
        }
        String t = (String) node.s.accept(this,indent);
        //--indent;
        result = result+t;
        return result;
   }
   public Object visit(VarDeclList node, Object data){
        // 
        int indent = (int) data; 
        String v = (String) node.v.accept(this,indent);
        
        if (node.vlist!=null){
            String vlist = (String) node.vlist.accept(this,indent);
            v = vlist + v;
        }
        
        return v;
   }
}

 

 