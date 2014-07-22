package org.soya.tools;

import org.soya.ast.CompilationUnit;
import soya.lang.SoyaClassLoader;
import org.soya.runtime.SoyaShell;

import java.io.*;

/**
 * @author: Jun Gong
 */
public class Interpreter {

    private Configuration configuration;

    private SoyaShell shell = SoyaShell.getSharedShell();

    public Interpreter(Configuration configuration) {
        this.configuration = configuration;
    }

    public void interpret() throws Exception {
        File file = configuration.getSourceFile();
        String script = configuration.getInlineSource();
        if (file != null) {
            interpret(file);
        }
        else if (script != null) {
            interpret(script);
        }
    }

    public void interpret(String script) throws Exception {
        interpret("<script>", new StringReader(script));
    }

    public void interpret(File file) throws Exception {
        interpret(file.getPath(), new FileReader(file));
    }

    public void interpret(String name, Reader reader) throws Exception {
        interpret(new SourceCode(name, reader, configuration));
    }

    public void interpret(SourceCode sourceCode) throws Exception {
        SoyaClassLoader classLoader = new SoyaClassLoader(configuration);
        sourceCode.setClassLoader(classLoader);
        sourceCode.parse();
        interpret(sourceCode.getAST());
    }

    public void interpret(CompilationUnit unit) throws Exception {
        shell.eval(unit);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
