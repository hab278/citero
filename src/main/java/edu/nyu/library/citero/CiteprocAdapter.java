package edu.nyu.library.citero;

import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.tools.shell.Global;

public class CiteprocAdapter {
    
    private Context context;
    private Global global;

    private CiteprocAdapter(String data, String styleDef){
        ContextFactory factory = new ContextFactory();
        context = factory.enterContext();
        global = new Global(context);
        setVar("data",data);
        setVar("style","\""+styleDef+"\"");
        String location = "src/main/java/edu/nyu/library/citero/vendor/csl/";
        readJS(location,"xmle4x.js");
        readJS(location,"citeproc.js");
        readJS(location,"citeproc-interface.js");
        
    }
    
    public static CiteprocAdapter dataAndStyle(String data, String styleDef){
        return new CiteprocAdapter(data,styleDef);
    }
    
    public String result(){
        Object res = context.evaluateString(global, "get_formatted_bib();", "<cmd>", 0, null);
        return res.toString();
    }
    
    private void setVar(String varname, String value){
        String statement = "var " + varname + " = " + value + ";";
        context.evaluateString(global, statement, "<cmd>", 0, null);
    }
    
    private void readJS(String location, String fileName){
        FileReader file;
        try {
            file = new FileReader(location+fileName);
            context.evaluateReader(global, file, fileName, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
