import syntaxtree.*; 
import java.util.HashMap;

public class SymbolTable { 

    public HashMap<String,MethodDecl> methods = new HashMap<String, MethodDecl>();
    public HashMap<String,VarDecl> variables = new HashMap<String, VarDecl>() ;
    public HashMap<String,Formal> formals = new HashMap<String, Formal>() ;
    public HashMap<String,String> typeName = new HashMap<String, String>();

    public SymbolTable() {
    }

    public String toString(){
        String result = "Methods: \n";
        for (String key : methods.keySet()) {
            result += key + ": "+ methods.get(key).toString() + ", \n";
        }
        result += "\n";
        result += "Variables: \n";
        for (String key : variables.keySet()) {
            result += key + ": " + variables.get(key).toString() + ", \n";
        }
        result += "\n";
        result += "TypeNames: \n";
        for (String key : typeName.keySet()) {
            result += key + ": "+ typeName.get(key) + ",\n ";
        }
        return result;
    }

 
}
