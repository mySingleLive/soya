package org.soya.tools;

import java.io.File;

/**
 * @author: Jun Gong
 */
public class Compiler {

    private Configuration configuration;

    public Compiler(Configuration configuration) {
        this.configuration = configuration;
    }

    public void compile() throws ErrorListException {
        File file = configuration.getSourceFile();
        if (file != null) {
            compile(file);
        }
        else  {
            String script = configuration.getInlineSource();
            if (script != null) {
                compile(script);
            }
        }
    }


    public void compile(File file) throws ErrorListException {
        CompilationUnits compilationUnits = new CompilationUnits(configuration);
        compilationUnits.addSource(file);
        compilationUnits.comple();
    }

    public void compile(String script) throws ErrorListException {
        CompilationUnits compilationUnits = new CompilationUnits(configuration);
        compilationUnits.addSource(script);
        compilationUnits.comple();
    }

}
