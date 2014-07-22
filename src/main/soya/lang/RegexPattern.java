package soya.lang;

import org.soya.runtime.MetaClassUtil;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author: Jun Gong
 */
public class RegexPattern extends ObjectPattern implements java.io.Serializable {

    public static final int CASE_INSENSITIVE = 0x1;

    public static final int GLOBAL = 0x1 << 1;

    public static final int MULTILINE = 0x1 << 2;

    public static final int SINGLELINE = 0x1 << 3;

    public static final int UNICODE_CASE = 0x1 << 4;


    private Pattern pattern;
    private int flag;
    private Matcher globalMatcher = null;
    private String lastMatchedString = null;

    public RegexPattern(String pattern) {
        this(Pattern.compile(pattern));
    }

    public RegexPattern(String pattern, int flag) {
        super(MetaClassUtil.createMetaClass(RegexPattern.class), pattern);
        this.flag = flag;
        this.pattern = Pattern.compile(pattern, generateJavaPatternFlag());
    }

    public RegexPattern(Pattern pattern) {
        super(MetaClassUtil.createMetaClass(RegexPattern.class), pattern);
        this.pattern = pattern;
    }


    public Pattern getPattern() {
        return pattern;
    }


    public boolean isCaseInsensitive() {
        return (flag & CASE_INSENSITIVE) == CASE_INSENSITIVE;
    }

    public boolean isGlobal() {
        return (flag & GLOBAL) == GLOBAL;
    }

    public boolean isMultiline() {
        return (flag & MULTILINE) == MULTILINE;
    }

    private int generateJavaPatternFlag() {
        int f = 0;
        if (isCaseInsensitive()) {
            f |= Pattern.CASE_INSENSITIVE;
        }
        if (isMultiline()) {
            f |= Pattern.MULTILINE;
        }
        return f;
    }

    public Matcher matcher(CharSequence input) {
        return getPattern().matcher(input);
    }

    public boolean test(String str) {
        Matcher matcher = null;
        if (isGlobal()) {
            if (globalMatcher == null || !str.equals(lastMatchedString)) {
                globalMatcher = pattern.matcher(str);
            }
            matcher = globalMatcher;
            lastMatchedString = str;
        }
        if (matcher == null) {
            matcher = pattern.matcher(str);
        }
        boolean ret = matcher.find();
        if (isGlobal() && !ret) {
            globalMatcher.reset(str);
        }
        return ret;
    }

    public boolean matches(String str) {
        Matcher matcher = this.matcher(str);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  RegexPattern) {
            obj = ((RegexPattern) obj).getPattern();
            return pattern.toString().equals(obj.toString());
        }
        else if (obj instanceof ObjectPattern) {
            obj = ((ObjectPattern) obj).getObject();
            return this.equals(obj);
        }
        else if (obj instanceof Pattern) {
            return pattern.toString().equals(obj.toString());
        }
        return false;
    }

    @Override
    public boolean isCase(Object obj) throws Throwable {
        if (obj instanceof RegexPattern) {
            return this.equals(obj);
        }
        if (obj instanceof CharSequence) {
            return matches(obj.toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return pattern.toString();
    }
}
