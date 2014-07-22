package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author: Jun Gong
 */
public class SoyaString extends EvalObject implements Comparable, CharSequence, Serializable, Pattern {

    private String value;

    public SoyaString(String value) {
        super(MetaClassUtil.String);
        this.value = value;
    }

    public int hashCode() {
        int hashCode = toString().hashCode();
        return hashCode;
    }

    public int compareTo(Object o) {
        if (o == null) {
            return 0;
        }
        int ret = toString().compareTo(o.toString());
        return ret;
    }

    public int compareToIgnoreCase(Object o) {
        return toString().compareToIgnoreCase(o.toString());
    }

    public boolean equals(Object o) {
		if (!(o instanceof String || o instanceof SoyaString)) {
			return false;
		}
        return toString().equals(o.toString());
    }

    public Object get(int index1, int index2) throws Throwable {
        StringBuffer buffer = new StringBuffer();
        if (index2 > index1) {
            index2 = Math.min(index2, length() - 1);
            for (int i = index1; i <= index2; i++) {
                buffer.append(value.charAt(i));
            }
        }
        else {
            buffer.append(value.charAt(index1));
        }
        return buffer.toString();
    }


    public String get(int index) {
        char c = charAt(index);
        return c + "";
    }

    public Object get(RegexPattern regexPattern) {
        return find(regexPattern);
    }


    public Object get(int index, Pattern pattern) throws Throwable {
        if (pattern instanceof RegexPattern) {
            List ret = new SoyaList();
            int len = length();
            for (int i = index; i < len; i++) {
                Object o = get(i);
                if (PatternUtil.isMatch(o, pattern)) {
                    ret.add(o);
                }
            }
            return ret;
        }
        else {
            StringBuffer buffer = new StringBuffer();
            int len = length();
            for (int i = index; i < len; i++) {
                Object o = get(i);
                if (PatternUtil.isMatch(o, pattern)) {
                    buffer.append(o);
                }
            }
            return buffer.toString();
        }
    }

    public Object get(Pattern pattern, int index) throws Throwable {
        if (pattern instanceof RegexPattern) {
            List ret = new SoyaList();
            int len = length();
            index = Math.min(index + 1, len);
            for (int i = 0; i < index; i++) {
                Object o = get(i);
                if (PatternUtil.isMatch(o, pattern)) {
                    ret.add(o);
                }
            }
            return ret;
        }
        else {
            StringBuffer buffer = new StringBuffer();
            int len = length();
            index = Math.min(index + 1, len);
            for (int i = 0; i < index; i++) {
                Object o = get(i);
                if (PatternUtil.isMatch(o, pattern)) {
                    buffer.append(o);
                }
            }
            return buffer.toString();
        }
    }


    public int length() {
        return toString().length();
    }

    public char charAt(int i) {
        return toString().charAt(i);
    }

    public char[] toCharArray() {
        return toString().toCharArray();
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        toString().getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public String substring(int beginIndex) {
        return toString().substring(beginIndex);
    }

    public String substring(int begineIndex, int endIndex) {
        return toString().substring(begineIndex, endIndex);
    }

    public String substring(Range range) {
        Object from = InvokeUtil.transformToJavaObject(Integer.class, range.getFrom());
        Object to = InvokeUtil.transformToJavaObject(Integer.class, range.getTo());
        int ifrom = 0;
        int ito = 0;

        if (from != null) {
            if (!(from instanceof Integer)) {
                return "";
            }
            ifrom = ((Integer) from).intValue();
            if (!range.isIncludeFrom()) {
                ifrom += 1;
            }
        }
        if (to != null) {
            if (!(to instanceof Integer)) {
                return "";
            }
            ito = ((Integer) to).intValue();
            if (!range.isIncludeTo()) {
                ito -= 1;
            }
        }

        if (from != null && to == null) {
            return substring(ifrom);
        }
        else if (from == null && to != null) {
            return substring(0, ito);
        }
        return substring(ifrom, ito);
    }

    public CharSequence subSequence(int i, int i2) {
        return toString().subSequence(i, i2);
    }

    public String concat(Object o) {
        return toString().concat(o.toString());
    }

    public boolean contains(Object o) {
        return toString().contains(o.toString());
    }

    public boolean contentEquals(Object o) {
        return toString().contentEquals(o.toString());
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }

    public byte[] getBytes(Charset charset) {
        return toString().getBytes(charset);
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        return toString().getBytes(charsetName);
    }

/*
    public void getBytes(int srcBegin, int srcEnd, byte dst[], int dstBegin) {
        toString().getBytes(srcBegin, srcEnd, dst, dstBegin);
    }
*/

    public String getValue() {
		return value;
	}

    public int codePointAt(int index) {
        return toString().codePointAt(index);
    }

    public int codePointBefore(int index) {
        return toString().codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return toString().codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return toString().offsetByCodePoints(index, codePointOffset);
    }

    public String toUpperCase() {
        return toString().toUpperCase();
    }

    public String toUpperCase(Locale locale) {
        return toString().toUpperCase(locale);
    }

    public String toLowerCase() {
        return toString().toLowerCase();
    }

    public String toLowerCase(Locale locale) {
        return toString().toLowerCase(locale);
    }

    public int indexOf(int ch) {
        return toString().indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return toString().indexOf(ch, fromIndex);
    }

    public int indexOf(String str) {
        return toString().indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return toString().indexOf(str, fromIndex);
    }

    public int indexOf(RegexPattern regexPattern) {
        Matcher matcher = regexPattern.matcher(toString());
        if (matcher.find()) {
            return matcher.start();
        }
        return -1;
    }

    public int indexOf(RegexPattern regexPattern, int fromIndex) {
        Matcher matcher = regexPattern.matcher(toString());
        if (matcher.find(fromIndex)) {
            return matcher.start();
        }
        return -1;
    }

    public String[] split(String s) {
        return toString().split(s);
    }

    public String[] split(RegexPattern regex) {
        return toString().split(regex.toString());
    }

    public String intern() {
        return toString().intern();
    }

    public boolean endWith(String suffix) {
        return toString().endsWith(suffix);
    }

    public int lastIndexOf(int ch) {
        return toString().lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return toString().lastIndexOf(ch, fromIndex);
    }

    public int lastIndexOf(CharSequence o) {
        return toString().lastIndexOf(o.toString());
    }

    public int lastIndexOf(CharSequence o, int fromIndex) {
        return toString().lastIndexOf(o.toString(), fromIndex);
    }

    public boolean matches(String regex) {
        return toString().matches(regex);
    }

    public boolean matches(Pattern pattern) throws Throwable {
        return PatternUtil.isMatch(toString(), pattern);
    }

    public Object find(RegexPattern regexPattern) {
        Matcher matcher = regexPattern.matcher(toString());
        if (regexPattern.isGlobal()) {
            List results = new SoyaList();
            while (matcher.find()) {
                results.add(matcher.group());
            }
            return results;
        }
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String replace(char oldChar, char newChar) {
        return toString().replace(oldChar, newChar);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return toString().replace(target, replacement);
    }

    public String replace(RegexPattern target, CharSequence replacement) {
        return value.replaceAll(target.toString(), replacement.toString());
    }

	public String toString() {
        if (value == null) {
            return "";
        }
        return value;
    }

    @Override
    public boolean isEmpty() {
        return toString().isEmpty();
    }

	@Override
	public boolean isMatch(Object obj) {
		return equals(obj);
	}

    public String multi(int n) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            buffer.append(value);
        }
        return buffer.toString();
    }

    public String[] div(String s) {
        return split(s);
    }

    public String[] div(RegexPattern regexPattern) {
        return split(regexPattern);
    }

    public boolean lessThan(Object o) {
        return compareTo(o) == -1;
    }

    public boolean greaterThan(Object o) {
        return compareTo(o) == 1;
    }

    public boolean lessEquals(Object o) {
        int i = compareTo(o);
        return i == -1 || i == 0;
    }

    public boolean greaterEquals(Object o) {
        int i = compareTo(o);
        return i == 1 || i == 0;
    }

}