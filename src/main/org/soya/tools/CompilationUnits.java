package org.soya.tools;

import org.soya.ast.SoyaCST;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class CompilationUnits {

    private Configuration configuration;

    private List<SourceCode> sources;

    private SoyaCST ast;

    private HashSet depends;

    private ErrorList errorList;

    public CompilationUnits(Configuration configuration) {
        this.configuration = configuration;
        this.sources = new LinkedList<SourceCode>();
        this.errorList = new ErrorList(configuration);
    }

    public void addSource(File file) {
        try {
            SourceCode source = new SourceCode(file, configuration);
            source.setErrorList(getErrorList());
            sources.add(source);
        } catch (IOException e) {
            errorList.addException(e);
        }
    }

    public void addSource(String script) {
        SourceCode source = new SourceCode("<SCRIPT>", script, configuration);
        source.setErrorList(getErrorList());
        sources.add(source);
    }


    public void comple() throws ErrorListException {
        for (int i = 0; i < sources.size(); i++) {
            SourceCode source = sources.get(i);
            source.parse();
        }
        errorList.interruptIfHasErrors();
    }

    public ErrorList getErrorList() {
        return errorList;
    }

    public void setErrorList(ErrorList errorList) {
        this.errorList = errorList;
    }

    public List<SourceCode> getSources() {
        return sources;
    }
}
