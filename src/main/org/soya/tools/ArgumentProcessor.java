package org.soya.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author: Jun Gong
 */
public class ArgumentProcessor {
    private Configuration configuration = new Configuration();
    private List<String> args;
    private String unrecognizedOption = null;

    public ArgumentProcessor(List<String> args) throws UnreconizedOptionException {
        this.args = args;
        processArguments();
    }

    public ArgumentProcessor(String[] args) throws UnreconizedOptionException {
        this.args = new ArrayList<String>(args.length);
        for (int i = 0; i < args.length; i++) {
            this.args.add(args[i]);
        }
        if (args.length == 0) {
            this.args.add("-h");
        }
        processArguments();
    }

    public void processArguments() throws UnreconizedOptionException {
        Iterator<String> iterator = args.iterator();
        List<String> arguments = new ArrayList();
        while (iterator.hasNext()) {
            String opt = iterator.next();
            if (opt.charAt(0) == '-') {
                if (opt.equals("-v") ||  opt.equals("-version")) {
                    configuration.setVersion(true);
                    return;
                }
                else if (opt.equals("-h") || opt.equals("-help")) {
                    configuration.setHelp(true);
                    return;
                }
                else if (opt.equals("-debug")) {
                    configuration.setDebug(true);
                }
                else if (opt.equals("-cp") || opt.equals("-classpatch")) {
                    if (iterator.hasNext()) {
                        String cps = iterator.next();
                        String[] cpstrs = cps.split(";");
                        Vector<String> cpsVector = new Vector<String>(cps.length());
                        for (int i = 0; i < cpstrs.length; i++) {
                            cpsVector.add(cpstrs[i]);
                        }
                        configuration.setClassPath(cpsVector);
                    }
                }
                else if (opt.equals("-e")) {
                    if (iterator.hasNext()) {
                        String code = iterator.next();
                        configuration.setInlineSource(code);
                    }
                    return;
                }
                else {
                    throw new UnreconizedOptionException(opt);
                }
            }
            else if (configuration.getSourceFile() == null) {
                configuration.setSourceFile(new File(opt));
            }
            else {
                arguments.add(opt);
            }
        }
        configuration.setArgs(new String[arguments.size()]);
        for (int i = 0; i < arguments.size(); i++) {
            configuration.getArgs()[i] = arguments.get(i);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getUnrecognizedOption() {
        return unrecognizedOption;
    }
}
