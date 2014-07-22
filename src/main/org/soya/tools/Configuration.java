package org.soya.tools;

import java.io.File;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * @author: Jun Gong
 */
public class Configuration {

//    private String jvmVersion = getJvmVersion();

    private String extension;

    private PrintWriter out;

    private File sourceFile;

    private String inlineSource;

    private Vector<String> classPath;

    private boolean help;

    private boolean version;

    private boolean debug;

    private String sourceEncoding;

    private int errorThreshold;

    private String[] args;

    public Configuration() {
        setExtension("soya");
        setOut(null);
        setSourceFile(null);
        setClassPath(new Vector<String>());
        setHelp(false);
        setVersion(false);
        setDebug(false);

        String encoding = null;
        try {
            encoding = System.getProperty("file.encoding", "US-ASCII");
        } catch (Exception e) {
        }
        try {
            encoding = System.getProperty("soya.source.encoding", encoding);
        } catch (Exception e) {
        }
        setSourceEncoding(encoding);

        setErrorThreshold(10);
    }

    public String getJvmVersion() {
        String major = System.getProperty("java.class.version").split("\\.")[0];
        if (Integer.parseInt(major) < 49) {
            return "1.4";
        }
        return "1.5";
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public String getInlineSource() {
        return inlineSource;
    }

    public void setInlineSource(String inlineSource) {
        this.inlineSource = inlineSource;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Vector<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(Vector<String> classPath) {
        this.classPath = classPath;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public int getErrorThreshold() {
        return errorThreshold;
    }

    public void setErrorThreshold(int errorThreshold) {
        this.errorThreshold = errorThreshold;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
