package org.soya.tools;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;
import antlr.collections.AST;
import org.soya.antlr.SoyaASTProcessor;
import org.soya.antlr.SoyaTokenSource;
import org.soya.antlr.SourceLineReader;
import org.soya.antlr.SyntaxException;
import org.soya.antlr.parser.SoyaLexer;
import org.soya.antlr.parser.SoyaParser;
import org.soya.ast.CompilationUnit;
import org.soya.ast.SoyaCST;
import org.soya.ast.TreeNode;
import soya.lang.SoyaClassLoader;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author: Jun Gong
 */
public class SourceCode {

    private Configuration configuration;

    private String name;

    private Reader sourceReader;

    SourceLineReader sourceLineReader;

    private SoyaCST cst;

    private CompilationUnit ast;

    private ErrorList errorList;

    private SoyaClassLoader classLoader;

    public SourceCode(String name, Reader reader, Configuration configuration) {
        this.configuration = configuration;
        this.name = name;
        this.sourceReader = reader;
        this.errorList = new ErrorList(configuration);
    }

    public SourceCode(File file, Configuration configuration) throws IOException {
        this(file.getPath(),
                new InputStreamReader(
                        new BufferedInputStream(new FileInputStream(file)),
                        Charset.forName(configuration.getSourceEncoding())),
                configuration);
    }

    public SourceCode(String name, String source, Configuration configuration) {
        this(name, new StringReader(source), configuration);
    }

    public void parse() throws ErrorListException {
        Reader reader = getSourceReader();
        sourceLineReader = new SourceLineReader(reader);
        SoyaLexer lexer = new SoyaLexer(sourceLineReader);
        SoyaTokenSource tokenSource = new SoyaTokenSource(lexer.plumb());
        tokenSource.setLexer(lexer);
        SoyaParser parser = new SoyaParser(tokenSource);
        lexer.setTokenSource(tokenSource);
        parser.setLexer(lexer);
        parser.getASTFactory().setASTNodeClass(SoyaCST.class);
        tokenSource.setParser(parser);
        try {
            parser.compilationUnit();
        }
        catch (TokenStreamRecognitionException tsre) {
            RecognitionException e = tsre.recog;
            SyntaxException err = new SyntaxException(getName(), e.getMessage(), e.getLine(), e.getColumn());
            errorList.addException(err);
        }
        catch (RecognitionException e) {
            SyntaxException err = new SyntaxException(getName(), e.getMessage(), e.getLine(), e.getColumn());
            errorList.addException(err);
        }
        catch (TokenStreamException e) {
            errorList.addException(e);
        }

        errorList.interruptIfHasErrors();

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AST cst = parser.getAST();
        setCST((SoyaCST) cst);
        SoyaASTProcessor processor = new SoyaASTProcessor(cst, this, classLoader);
        CompilationUnit ast = null;
        try {
            ast = processor.processASTFromCST();
        } catch (SyntaxException e) {
            errorList.addException(e);
        }

        errorList.interruptIfHasErrors();
        setAST(ast);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getName() {
        return name;
    }

    public String getClassNameFromSourceFile() {
        File sourceFile = configuration.getSourceFile();
        if (sourceFile == null) {
            return null;
        }
        return sourceFile.getName();
    }

    public Reader getSourceReader() {
        return sourceReader;
    }

    public SourceLineReader getSourceLineReader() {
        return sourceLineReader;
    }

    public SoyaCST getCST() {
        return cst;
    }

    public void setCST(SoyaCST cst) {
        this.cst = cst;
    }

    public CompilationUnit getAST() {
        return ast;
    }

    public void setAST(CompilationUnit ast) {
        this.ast = ast;
    }

    public ErrorList getErrorList() {
        return errorList;
    }

    public void setErrorList(ErrorList errorList) {
        this.errorList = errorList;
    }

    public void addError(Exception err) {
        errorList.addException(err);
    }

    public SoyaClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(SoyaClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getSnippet(TreeNode node) {
        return getSourceLineReader().getSnippet(node.getLine(), node.getColumn(), node.getLastLine(), node.getLastColumn());
    }

}
