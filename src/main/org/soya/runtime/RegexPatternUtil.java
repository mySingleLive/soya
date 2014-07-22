package org.soya.runtime;

import soya.lang.RegexPattern;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class RegexPatternUtil {

    private static Map<String, RegexPattern> internedRegexPatterns = new HashMap<String, RegexPattern>();

    public static boolean isCaseInsensitive(int flag) {
        return (flag & RegexPattern.CASE_INSENSITIVE) == RegexPattern.CASE_INSENSITIVE;
    }

    public static boolean isGlobal(int flag) {
        return (flag & RegexPattern.GLOBAL) == RegexPattern.GLOBAL;
    }

    public static boolean isMultiline(int flag) {
        return (flag & RegexPattern.MULTILINE) == RegexPattern.MULTILINE;
    }


    public static String getRegexPatternCode(String regex, int flag) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('/');
        buffer.append(regex);
        buffer.append('/');
        if (isCaseInsensitive(flag)) {
            buffer.append('i');
        }
        if (isGlobal(flag)) {
            buffer.append('g');
        }
        if (isMultiline(flag)) {
            buffer.append('m');
        }
        return buffer.toString();
    }


    public static String getRegexPatternCode(RegexPattern regexPattern) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('/');
        buffer.append(regexPattern.toString());
        buffer.append('/');
        if (regexPattern.isCaseInsensitive()) {
            buffer.append('i');
        }
        if (regexPattern.isGlobal()) {
            buffer.append('g');
        }
        if (regexPattern.isMultiline()) {
            buffer.append('m');
        }
        return buffer.toString();
    }

    public static RegexPattern internRegexPattern(RegexPattern regexPattern) {
        String code = getRegexPatternCode(regexPattern);
        if (internedRegexPatterns.containsKey(code)) {
            return internedRegexPatterns.get(code);
        }
        internedRegexPatterns.put(code, regexPattern);
        return internedRegexPatterns.get(code);
    }

    public static RegexPattern internRegexPattern(String regex, int flag) {
        String code = getRegexPatternCode(regex, flag);
        if (internedRegexPatterns.containsKey(code)) {
            return internedRegexPatterns.get(code);
        }
        internedRegexPatterns.put(code, new RegexPattern(regex, flag));
        return internedRegexPatterns.get(code);
    }

}
