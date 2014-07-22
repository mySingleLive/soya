package org.soya.antlr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Jun Gong
 */
public class SourceLineReader extends BufferedReader {
    private final List lines = new ArrayList();
    private StringBuffer currentLine;

    public SourceLineReader(Reader in) {
        super(in);
        currentLine = new StringBuffer();
        lines.add(currentLine);
    }


    public String getSnippet(int startLine, int startColumn, int endLine, int endColumn) {
        if (startLine == endLine && startColumn == endColumn) {
            return null;
        }
        if (lines.size() == 1 && currentLine.length() == 0) {
            return null;
        }

        if (startLine < 1) {
            startLine = 1;
        }
        if (endLine < 1) {
            endLine = 1;
        }
        if (startColumn < 1) {
            startColumn = 1;
        }
        if (endColumn < 1) {
            endColumn = 1;
        }
        if (startLine > lines.size()) {
            startLine = lines.size();
        }
        if (endLine > lines.size()) {
            endLine = lines.size();
        }

        StringBuffer snippet = new StringBuffer();
        for (int i = startLine - 1; i < endLine;i++) {
            String line = lines.get(i).toString();
            if (startLine == endLine) {
                if (startColumn > line.length()) { startColumn = line.length();}
                if (startColumn < 1) { startColumn = 1;}
                if (endColumn > line.length()) { endColumn = line.length() + 1;}
                if (endColumn < 1) { endColumn = 1;}

                line = line.substring(startColumn - 1, endColumn - 1);
            }
            else {
                if (i == startLine - 1) {
                    if (startColumn - 1 < line.length()) {
                        line = line.substring(startColumn - 1);
                    }
                }
                if (i == endLine - 1) {
                    if (endColumn - 1 < line.length()) {
                        line = line.substring(0,endColumn - 1);
                    }
                }
            }
            snippet.append(line);
        }
        return snippet.toString().trim();
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c != -1) {
            currentLine.append((char) c);
        }
        if (c == '\n') {
            currentLine = new StringBuffer();
            lines.add(currentLine);
        }
        return c;
    }
}
