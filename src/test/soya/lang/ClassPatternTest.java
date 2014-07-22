package soya.lang;

import org.soya.runtime.VarScope;
import soya.util.SoyaBaseTestCase;

/**
 * @author: Gongjun
 */
public class ClassPatternTest extends SoyaBaseTestCase {

    public class PatternVarScope extends VarScope {
        public int __var_x;
    }

    public void testPatternVarValue() {
        PatternVarScope varScope = new PatternVarScope();
        ClassPattern pattern = new ClassPattern(String.class, "x", varScope);
        pattern.setValue(10);
        assertEquals(varScope.__var_x, pattern.getValue());
        assertEquals(10, pattern.getValue());
    }

    public void testMatch() throws Throwable {
        int i = 1;
        String s = "s";

        ClassPattern intPattern = new ClassPattern(int.class);
        ClassPattern stringPattern = new ClassPattern(String.class);

        assertTrue(intPattern.isMatch(i));
        assertFalse(intPattern.isMatch(s));

        assertFalse(stringPattern.isMatch(i));
        assertTrue(stringPattern.isMatch(s));
    }
}
